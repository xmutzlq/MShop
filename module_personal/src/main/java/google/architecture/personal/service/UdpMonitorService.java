package google.architecture.personal.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpMonitorService extends Service {

    private boolean isRunning = false;
    private final static int PORT = 9099;

    @Override
    public void onCreate() {
        super.onCreate();
        isRunning = true;
        startTimeThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void startTimeThread(){
        System.out.println("========szq=========:startTimeThread");
        new Thread(new Runnable() {
            @Override
            public void run() {

                    try (DatagramSocket socket = new DatagramSocket(PORT)) {
                        DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                        socket.receive(request);
                        String address = request.getAddress().getHostAddress();
                        System.out.println("========szq=========:address:" + address);
//                        String jsonStr =  new String(request.getData(),request.getLength());
                        String jsonStr = new String(request.getData(),"utf-8");
                        jsonStr = jsonStr.substring(0,jsonStr.lastIndexOf("}")+1);
                        Gson gson = new Gson();
                        UAVresult result = gson.fromJson(jsonStr, UAVresult.class);

                        System.out.println("========szq=========:jsonStr:" + jsonStr);
                        while (isRunning) {

                            //Thread.sleep(3000);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
