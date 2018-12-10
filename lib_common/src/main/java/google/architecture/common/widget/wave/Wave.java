package google.architecture.common.widget.wave;

import android.graphics.Path;

/**
 * @author lq.zeng
 * @date 2018/10/25
 */

public class Wave {
    Path path;          //水波路径
    int width;          //画布宽度（2倍波长）
    int wave;           //波幅（振幅）
    float offsetX;        //水波的水平偏移量
    float offsetY;        //水波的竖直偏移量
    float velocity;       //水波移动速度（像素/秒）
    private float scaleX;       //水平拉伸比例
    private float scaleY;       //竖直拉伸比例
//    int startColor;     //开始颜色
//    int closeColor;     //结束颜色
//    float alpha;        //颜色透明度

    /**
     * 通过参数构造一个水波对象
     * @param offsetX   水平偏移量
     * @param offsetY   竖直偏移量
     * @param velocity  移动速度（像素/秒）
     * @param scaleX    水平拉伸量
     * @param scaleY    竖直拉伸量
     * @param w         波长
     * @param h         画布高度
     * @param wave      波幅（波宽度）
     */
//    @SuppressWarnings("PointlessArithmeticExpression")
    Wave(/*Context context, */int offsetX, int offsetY, int velocity, float scaleX, float scaleY, int w, int h, int wave) {
//        super(context);
        this.width = (int) (2 * scaleX * w); //画布宽度（2倍波长）
        this.wave = wave;           //波幅（波宽）
        this.scaleX = scaleX;       //水平拉伸量
        this.scaleY = scaleY;       //竖直拉伸量
        this.offsetX = offsetX;     //水平偏移量
        this.offsetY = offsetY;     //竖直偏移量
        this.velocity = velocity;   //移动速度（像素/秒）
        this.path = buildWavePath(width, h);
    }

//    /*
//     * 根据 波长度、中轴线高度、波幅 绘制水波路径
//     */
//    public Wave(Context context) {
//        this(context, null, 0);
//    }
//
//    public Wave(Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public Wave(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//
//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Wave);
//
////        startColor = ta.getColor(R.styleable.Wave_mwhStartColor, 0);
////        closeColor = ta.getColor(R.styleable.Wave_mwhCloseColor, 0);
////        alpha = ta.getFloat(R.styleable.Wave_mwhColorAlpha, 0f);
//        scaleX = ta.getFloat(R.styleable.Wave_mwScaleX, 1);
//        scaleY = ta.getFloat(R.styleable.Wave_mwScaleY, 1);
//        offsetX = ta.getDimensionPixelOffset(R.styleable.Wave_mwOffsetX, 0);
//        offsetY = ta.getDimensionPixelOffset(R.styleable.Wave_mwOffsetY, 0);
//        velocity = ta.getDimensionPixelOffset(R.styleable.Wave_mwVelocity, Util.dp2px(10));
//        wave = ta.getDimensionPixelOffset(R.styleable.Wave_mwWaveHeight, 0) / 2;
//
//        ta.recycle();
//    }

    public void updateWavePath(int w, int h, int waveHeight) {
        this.wave = (wave > 0) ? wave : waveHeight /2;
        this.width = (int) (2* scaleX * w);  //画布宽度（2倍波长）
        this.path = buildWavePath(width, h);
    }


    private Path buildWavePath(int width, int height) {
        int DP = Util.dp2px(1);//一个dp在当前设备表示的像素量（水波的绘制精度设为一个dp单位）
        if (DP < 1) {
            DP = 1;
        }

        int wave = (int) (scaleY * this.wave);//计算拉伸之后的波幅

        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, height - wave);

        for (int x = DP; x < width; x += DP) {
            path.lineTo(x, height - wave - wave * (float) Math.sin(4.0 * Math.PI * x / width));
        }

        path.lineTo(width, height - wave);
        path.lineTo(width, 0);
        path.close();
        return path;
    }
}
