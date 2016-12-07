package com.silencedut.knowweather.common.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.silencedut.knowweather.R;

/**
 * Created by SilenceDut on 16/10/18.
 */

public class SideLetterBar extends View {
    private static final String[] INITIAL = {"定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int choose = -1;
    private Paint paint = new Paint();
    private OnLetterChangedListener onLetterChangedListener;
    private TextView overlay;

    public SideLetterBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideLetterBar(Context context) {
        super(context);
    }


    public void setOverlay(TextView overlay) {
        this.overlay = overlay;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / INITIAL.length;
        for (int i = 0; i < INITIAL.length; i++) {
            paint.setTextSize(getResources().getDimension(R.dimen.text_size_small));
            paint.setColor(getResources().getColor(R.color.colorAccent));
            paint.setAntiAlias(true);
            float xPos = width / 2 - paint.measureText(INITIAL[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(INITIAL[i], xPos, yPos, paint);
            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnLetterChangedListener listener = onLetterChangedListener;
        final int c = (int) (y / getHeight() * INITIAL.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < INITIAL.length) {
                        choose = c;
                        invalidate();
                        if (overlay != null) {
                            overlay.setVisibility(VISIBLE);
                            String letter = INITIAL[c];
                            if (!(c == 0 || c == 1 || letter.equals("O") || letter.equals("U") || letter.equals("V"))) {
                                listener.onLetterChanged(INITIAL[c]);
                                overlay.setText(INITIAL[c]);
                            }

                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                choose = -1;
                invalidate();
                if (overlay != null) {
                    overlay.setVisibility(GONE);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener) {
        this.onLetterChangedListener = onLetterChangedListener;
    }

    public interface OnLetterChangedListener {
        void onLetterChanged(String letter);
    }

}
