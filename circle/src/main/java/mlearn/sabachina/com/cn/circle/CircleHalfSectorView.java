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
import android.view.View;

/**
 * Created by zhc on 2017/9/13 0013.
 */

public class CircleHalfSectorView extends View {
    private final float maxProgress = 100f;
    private int outsideColor;
    private int insideColor;
    private float progress;
    private float circleStrokeWidth;
    private float truthProgress;

    public CircleHalfSectorView(Context context) {
        this(context, null);
    }

    public CircleHalfSectorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CircleHalfSectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleHalfSectorView);
        outsideColor = typedArray.getColor(R.styleable.CircleHalfSectorView_progress, Color.BLACK);
        insideColor = typedArray.getColor(R.styleable.CircleHalfSectorView_outside_color, Color.RED);
        progress = typedArray.getFloat(R.styleable.CircleHalfSectorView_progress, 0f);
        if (progress > 1f) {
            progress = 1f;
        }
        truthProgress = progress * 360;
        circleStrokeWidth = typedArray.getFloat(R.styleable.CircleHalfSectorView_circle_width, 1f);
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
        paint.setColor(outsideColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        float width = getWidth();
        float height = getHeight();
        float radius = Math.min(width, height) / 2 - circleStrokeWidth / 2;
        canvas.drawCircle(width / 2, height / 2, radius, paint);
    }

    public void startAnimation(final float progress) {
        post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, progress * 360);
                valueAnimator.setDuration(1000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float animatedValue = (float) valueAnimator.getAnimatedValue();
                        truthProgress = animatedValue;
                        invalidate();
                    }
                });
                valueAnimator.start();
            }
        });
    }
}
