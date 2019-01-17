package google.architecture.personal;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.king.android.res.config.ARouterPath;
import com.shiyuan.footmodel.FootSurfaceView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

import google.architecture.common.base.BaseActivity;
import google.architecture.common.util.ToastUtils;
import google.architecture.common.viewmodel.xlj.FootScanViewModel;
import google.architecture.coremodel.data.xlj.FootScanData;
import google.architecture.coremodel.datamodel.http.ApiClient;
import google.architecture.coremodel.datamodel.http.ApiConstants;
import google.architecture.personal.databinding.ActivityFootScanBinding;
import google.architecture.personal.service.UAVresult;
import google.architecture.personal.service.UdpMonitorService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Route(path = ARouterPath.FootScanLoginAty)
public class ActivityFootScan extends BaseActivity<ActivityFootScanBinding> {
    private Intent serviceIntent;

    private boolean isRunning = false;
    private final static int PORT = 9099;
    private TextView mTvMsg;

    private FootSurfaceView mFootSurfaceView;//展示3D模型的view

    private FootScanViewModel viewModel;

    private String mAddress;//扫脚仪ip

    private String mPhoneNum;
    private String mAccessToken;

    private int prevCode = 0;

    @Override
    protected boolean isStatusBarTransparent() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_foot_scan;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new FootScanViewModel();
        addRunStatusChangeCallBack(viewModel);
//        viewModel.getUserToken("11111111111");

        mTvMsg = findViewById(R.id.msg_tv);

        /*if(!isServiceRunning("google.architecture.personal.service.UdpMonitorService")){
            serviceIntent = new Intent(getApplicationContext(), UdpMonitorService.class);
            startService(serviceIntent);
        }*/

        mFootSurfaceView = findViewById(R.id.foot_surface_view);
        mFootSurfaceView.setisSizeRotate(false);

        if(ApiConstants.isConnectFootScan) {
            isRunning = true;
            startTimeThread();
        }

        binding.statusIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(prevCode == 0 || prevCode == 71){
                    finish();
                }
            }
        });

    }

    @Override
    protected boolean canIdle() {
        return false;
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            UAVresult result = (UAVresult) msg.obj;
            switch(result.getCode()){
                case 0://默认心跳，收到后发送下一次心跳请求
                    if(prevCode == 0){
                        prevCode = 0;
                    }
                    mTvMsg.setText("默认心跳，收到后发送下一次心跳请求");
                    break;
                case 1://TV端服务异常终止
                    prevCode = 1;
                    mTvMsg.setText("TV端服务异常终止");
                    break;
                case 11://Pad端用户登录扫脚仪
                    prevCode = 11;
                    mTvMsg.setText("Pad端用户登录扫脚仪,手机号码:"+result.getMsg());
                    mPhoneNum = result.getMsg();
                    viewModel.getUserToken(mPhoneNum);//通过手机号获取用户token
                    break;
                case 21://预览左脚
                    prevCode = 21;
                    mTvMsg.setText("预览左脚");
                    break;
                case 31://正在扫描左脚
                    prevCode = 31;
                    mTvMsg.setText("正在扫描左脚");
                    break;
                case 41://左脚扫描完成(可以将扫脚仪中的左脚数据下载到TV端展示)
                    prevCode = 41;
                    mTvMsg.setText("左脚扫描完成");
                    downloadModel(result.getMsg());
                    break;
                case 51://预览右脚
                    prevCode = 51;
                    mTvMsg.setText("预览右脚");
                    break;
                case 61://正在扫描右脚
                    prevCode = 61;
                    mTvMsg.setText("正在扫描右脚");
                    break;
                case 71://右脚扫描完成（可以将扫脚仪中的右脚数据下载到TV端展示，到此全部扫脚流程完成）
                    prevCode = 71;
                    mTvMsg.setText("右脚扫描完成");
                    downloadModel(result.getMsg());
                    syncService();
                    break;
            }
            adjustSence(prevCode);
        }
    };


    @Override
    protected void onDataResult(Object o) {
        super.onDataResult(o);
        FootScanData data = (FootScanData)o;
        mAccessToken = data.getUserToken().getAccess_token();
    }

    /**
     * 判断服务是否运行
     */
    private boolean isServiceRunning(final String className) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
//        stopService(serviceIntent);
    }

    private void startTimeThread(){
        System.out.println("========szq=========:startTimeThread");
        new Thread(new Runnable() {
            @Override
            public void run() {

                try (DatagramSocket socket = new DatagramSocket(PORT)) {
                    DatagramPacket request = new DatagramPacket(new byte[1024], 1024);

                    while (isRunning) {
                        socket.receive(request);
                        String address = request.getAddress().getHostAddress();
                        mAddress = address;
                        System.out.println("========szq=========:address:" + address);
//                        String jsonStr =  new String(request.getData(),request.getLength());
                        String jsonStr = new String(request.getData(),"utf-8");
                        jsonStr = jsonStr.substring(0,jsonStr.lastIndexOf("}")+1);
                        jsonStr = jsonStr.replace("'}e':'",",");
                        Gson gson = new Gson();
                        try {
                            UAVresult result = gson.fromJson(jsonStr, UAVresult.class);


                        System.out.println("========szq=========:jsonStr:" + jsonStr);
                        sendHearBeat(result.getTs());

//                        mHandler.sendEmptyMessage(result.getCode());
                        Message msg = new Message();
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                        }catch(Exception e){
                            String error = e.getMessage().toString();
                        }

                        //Thread.sleep(3000);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    private void adjustSence(int code){
        switch (code){
            case 0://进入扫码界面
                binding.statusIv.setImageResource(R.drawable.sc_first_step);
                break;
            case 1://TV端服务异常终止
                break;
            case 11://Pad端用户登录扫脚仪
                binding.statusIv.setImageResource(R.drawable.sc_first_step);
                break;
            case 21://预览左脚
                binding.statusIv.setImageResource(R.drawable.sc_second_step);
                break;
            case 31://正在扫描左脚
                binding.statusIv.setImageResource(R.drawable.sc_third_step);
                break;
            case 41://左脚扫描完成(下载到TV端展示)
                binding.statusIv.setImageResource(R.drawable.sc_fourth_step);
                break;
            case 51://预览右脚
                binding.statusIv.setImageResource(R.drawable.sc_fifth_step);
                break;
            case 61://正在扫描右脚
                binding.statusIv.setImageResource(R.drawable.sc_sixth_step);
                break;
            case 71://右脚扫描完成(下载到TV端展示)
                binding.statusIv.setImageResource(R.drawable.sc_seventh_step);
                break;
        }
    }


    private void sendHearBeat(long ts){
        String url = "http://"+mAddress+"//api/Tv/PostTvInfo?id="+ Build.SERIAL+"&ts="+ts;
        OkHttpClient okHttpClient = ApiClient.getOkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("=========szq=============:fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("=========szq=============:success:"+response.body().string());
            }
        });
    }

    private void downloadModel(String scanId){
        ToastUtils.showLongToast("开始下载模型");
        String url = "http://"+mAddress+"/api/Scan/GetFootDatTri?scan_id="+scanId;
        OkHttpClient okHttpClient = ApiClient.getOkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/text"),"");
        final Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bear"+" "+mAccessToken)
                .post(body)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("=========szq=============:fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("=========szq=============:success:"+response.body().string());
                ToastUtils.showLongToast("下载模型成功，准备展示模型");
                InputStream is = null;//输入流
                ByteArrayOutputStream swapStream = null;
                try {
                    is = response.body().byteStream();//获取输入流
                    long total = response.body().contentLength();//获取文件大小
                    swapStream = new ByteArrayOutputStream();
                    if(is != null){
                        //byte[] dataByte = new byte[(int)total];
                        byte[] buff = new byte[1024];
                        int rc = 0;
                        while ((rc = is.read(buff)) != -1) {
                            swapStream.write(buff, 0, rc);
                        }
                        byte[] dataByte = swapStream.toByteArray();
                        swapStream.flush();
                        show3Dmodel(dataByte);
                        if(swapStream != null){
                            swapStream.close();
                        }
                    }


                }catch (Exception e){

                }finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }

                    try{
                        if(swapStream != null){
                            swapStream.close();
                        }
                    }catch(IOException e){

                    }

                }
            }
        });

    }

    private void syncService(){
        String url = "http://tvapi.3dculab.com:86/UAV2.0.2/api/VahooForTV/Index/GetFoot";
        OkHttpClient okHttpClient = ApiClient.getOkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    private void show3Dmodel(byte[] data){
        mFootSurfaceView.setDrawFrame(data,true);
        mFootSurfaceView.setAutoShowFootShownData();
    }

}
