package mlearn.sabachina.com.cn.circle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by zhc on 2018/3/26 0026.
 */

public class LabelView extends AppCompatTextView {
    private Paint paint;
    private Path path;

    public LabelView(Context context) {
        this(context,null);
    }

    public LabelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public LabelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelView);
        int color = typedArray.getColor(R.styleable.LabelView_background_color,Color.BLUE);
        paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        path = new Path();
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        path.moveTo(0,0);
        path.lineTo(0,height);
        path.lineTo(width/2,height-height/7);
        path.lineTo(width,height);
        path.lineTo(width,0);
        path.close();
        canvas.drawPath(path,paint);
        super.onDraw(canvas);
    }
}
