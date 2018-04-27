package mlearn.sabachina.com.cn.circle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * 外部圆环加上内部扇形的指示器，用于标识某个事物的进度
 * Created by zhc on 2017/9/13 0013.
 */

public class SectorIndicatorView extends View {
    private static final String TAG = "SectorIndicatorView";
    /**
     * 外圈颜色
     */
    private int strokeColor;
    /**
     * 内部填充色
     */
    private int insideColor;
    /**
     * 进度的百分比
     */
    private float progress;
    /**
     * 外圈弧的宽度
     */
    private float circleStrokeWidth;
    /**
     * 是否需要动画
     * 默认为true
     * 需要手动调用startAnimation方法
     */
    private boolean animationRequire = true;
    /**
     * 动画持续时间
     */
    private int animationDuration;
    /**
     * 当绘制的时候，真正的圆弧角度大小
     */
    private float truthProgress;

    public void setAnimationRequire(boolean animationRequire) {
        this.animationRequire = animationRequire;
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        invalidate();
    }

    public void setInsideColor(int insideColor) {
        this.insideColor = insideColor;
        invalidate();
    }

    public void setProgress(float progress) {
        if (progress >1){
            progress = 1;
            Log.e(TAG, "SectorIndicatorView: can not set progress that value Greater than 1!" );
        }
        this.progress = progress;
        truthProgress = progress * 360;
        invalidate();
    }

    public void setCircleStrokeWidth(float circleStrokeWidth) {
        this.circleStrokeWidth = circleStrokeWidth;
        invalidate();
    }

    public SectorIndicatorView(Context context) {
        this(context, null);
    }

    public SectorIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SectorIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SectorIndicatorView);
        strokeColor = typedArray.getColor(R.styleable.SectorIndicatorView_strokeColor, Color.BLUE);
        insideColor = typedArray.getColor(R.styleable.SectorIndicatorView_inside_color, Color.CYAN);
        progress = typedArray.getFloat(R.styleable.SectorIndicatorView_progress, 0f);
        if (progress > 1) {
            progress = 1f;
            Log.e(TAG, "SectorIndicatorView: can not set progress that value Greater than 1!" );
        }
        truthProgress = progress * 360;
        circleStrokeWidth = typedArray.getFloat(R.styleable.SectorIndicatorView_circle_width, 1f);
        animationRequire = typedArray.getBoolean(R.styleable.SectorIndicatorView_animation_require, true);
        animationDuration = typedArray.getInteger(R.styleable.SectorIndicatorView_animation_duration, 800);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int result = 100;
        int widthSize;
        int heightSize;
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthSpecMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                widthSize = Math.min(result,widthSpecSize);
                break;
            case MeasureSpec.EXACTLY:
                widthSize = widthSpecSize;
                break;
            default:
                widthSize = result;
                break;
        }
        switch (heightSpecMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                heightSize = Math.min(result,heightSpecSize);
                break;
            case MeasureSpec.EXACTLY:
                heightSize = heightSpecSize;
                break;
            default:
                heightSize = result;
                break;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawInsideArc(canvas);
    }

    private void drawInsideArc(final Canvas canvas) {
        final Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(insideColor);
        final RectF rectF = new RectF(circleStrokeWidth, circleStrokeWidth,
                getWidth() - circleStrokeWidth, getHeight() - circleStrokeWidth);
        canvas.drawArc(rectF, 270f, truthProgress, true, paint);
    }

    private void drawCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(circleStrokeWidth);
        paint.setColor(strokeColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        float width = getWidth();
        float height = getHeight();
        float radius = Math.min(width, height) / 2 - circleStrokeWidth / 2;
        canvas.drawCircle(width / 2, height / 2, radius, paint);
    }

    public void startAnimation(float progress) {
        if (progress >1){
            Log.e(TAG, "SectorIndicatorView: can not set progress that value Greater than 1!" );
            progress = 1;
        }
        final float realProgress = progress;
        if (animationRequire) {
            post(new Runnable() {
                @Override
                public void run() {
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, realProgress * 360);
                    valueAnimator.setDuration(animationDuration);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            truthProgress = (float) valueAnimator.getAnimatedValue();
                            invalidate();
                        }
                    });
                    valueAnimator.start();
                }
            });
        }
    }
}
