package kr.pe.sheep_transform.lpu237_adr;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DualThumbSeekBar extends View {

    private int thumbRadius = 30;
    private int trackHeight = 10;
    private Paint trackPaint, thumbPaint, rangePaint;
    private RectF trackRect;

    private float thumbLeftX, thumbRightX;
    private float minX, maxX;
    private boolean isLeftThumbSelected = false, isRightThumbSelected = false;

    private int minValue = 0, maxValue = 15;
    private int selectedMinValue = 0, selectedMaxValue = 15;

    private OnRangeChangeListener onRangeChangeListener;

    public DualThumbSeekBar(Context context) {
        super(context);
        init();
    }

    public DualThumbSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DualThumbSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public int getSelectedMin(){
        return selectedMinValue;
    }
    public int getSelectedMax(){
        return selectedMaxValue;
    }
    public void setMaxMinRangeAndSelectedRange( int nMax, int nMin, int nSelMin, int n_selMax ){
        minValue = nMin;
        maxValue = nMax;
        selectedMinValue = nSelMin;
        selectedMaxValue = n_selMax;
        setThumbPositions();
    }
    private void init() {
        trackPaint = new Paint();
        trackPaint.setColor(Color.GRAY);
        trackPaint.setStyle(Paint.Style.FILL);

        thumbPaint = new Paint();
        thumbPaint.setColor(Color.BLUE);
        thumbPaint.setStyle(Paint.Style.FILL);

        rangePaint = new Paint();
        rangePaint.setColor(Color.GREEN);
        rangePaint.setStyle(Paint.Style.FILL);

        trackRect = new RectF();

        // Initialize thumb positions based on selected values
        setThumbPositions();
    }

    private void setThumbPositions() {
        post(() -> {
            float proportionLeft = (selectedMinValue - minValue) / (float) (maxValue - minValue);
            float proportionRight = (selectedMaxValue - minValue) / (float) (maxValue - minValue);

            thumbLeftX = minX + proportionLeft * (maxX - minX);
            thumbRightX = minX + proportionRight * (maxX - minX);

            invalidate();
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        minX = getPaddingLeft() + thumbRadius;
        maxX = getWidth() - getPaddingRight() - thumbRadius;

        trackRect.set(minX, h / 2 - trackHeight / 2, maxX, h / 2 + trackHeight / 2);

        setThumbPositions();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw track
        canvas.drawRect(trackRect, trackPaint);

        // Draw range
        canvas.drawRect(thumbLeftX, trackRect.top, thumbRightX, trackRect.bottom, rangePaint);

        // Draw thumbs
        canvas.drawCircle(thumbLeftX, trackRect.centerY(), thumbRadius, thumbPaint);
        canvas.drawCircle(thumbRightX, trackRect.centerY(), thumbRadius, thumbPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = 0;
        float touchY = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();

                if (isTouchingThumb(thumbLeftX, touchX, touchY)) {
                    isLeftThumbSelected = true;
                } else if (isTouchingThumb(thumbRightX, touchX, touchY)) {
                    isRightThumbSelected = true;
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                touchX = event.getX();

                if (isLeftThumbSelected) {
                    thumbLeftX = Math.max(minX, Math.min(touchX, thumbRightX));
                    selectedMinValue = calculateSelectedValue(thumbLeftX);
                } else if (isRightThumbSelected) {
                    thumbRightX = Math.max(thumbLeftX, Math.min(touchX, maxX));
                    selectedMaxValue = calculateSelectedValue(thumbRightX);
                }

                if (onRangeChangeListener != null) {
                    onRangeChangeListener.onRangeChanged(selectedMinValue, selectedMaxValue);
                }

                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isLeftThumbSelected = false;
                isRightThumbSelected = false;
                return true;
        }
        return super.onTouchEvent(event);
    }

    private boolean isTouchingThumb(float thumbX, float touchX, float touchY) {
        return Math.abs(touchX - thumbX) <= thumbRadius;
    }

    private int calculateSelectedValue(float thumbX) {
        float proportion = (thumbX - minX) / (maxX - minX);
        return Math.round(proportion * (maxValue - minValue)) + minValue;
    }

    public void setOnRangeChangeListener(OnRangeChangeListener onRangeChangeListener) {
        this.onRangeChangeListener = onRangeChangeListener;
    }

    public interface OnRangeChangeListener {
        void onRangeChanged(int minValue, int maxValue);
    }
}

