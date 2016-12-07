package com.silencedut.knowweather.common.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.utils.Check;
import com.silencedut.knowweather.utils.UIUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilenceDut on 16/10/18.
 */

public class LevelView extends View {

    private Paint mGradientPaint;
    private Paint mTextPaint;
    private List<LevelColorPair> mLevelColors;
    private int mWidth;
    private int mPaintStroke;

    private RectF mRectLeft;
    private RectF mRectRight;
    private int mLevelCount;
    private int mTextSize;
    private int mPaddingTop;

    private int mCurrentValue;


    public LevelView(Context context) {
        super(context);
    }

    public LevelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LevelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LevelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        mWidth = getMeasuredWidth();
        mGradientPaint = new Paint();
        mGradientPaint.setAntiAlias(true);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mTextSize = UIUtil.dipToPx(getContext(), 10.0f);
        mTextPaint.setTextSize(mTextSize);

        mPaintStroke = UIUtil.dipToPx(getContext(), 4.0f);
        mPaddingTop = mPaintStroke;

        mRectLeft = new RectF(0, mPaddingTop - mPaintStroke / 2, mPaintStroke, mPaintStroke + mPaddingTop - mPaintStroke / 2);
        mRectRight = new RectF(mWidth - mPaintStroke, mPaddingTop - mPaintStroke / 2, mWidth, mPaintStroke + mPaddingTop - mPaintStroke / 2);

        mGradientPaint.setStyle(Paint.Style.FILL);
    }

    public void setColorLever(int[] colorIds, int[] levels) {

        if (colorIds.length != levels.length) {
            return;
        }
        mLevelColors = new ArrayList<>();

        for (int index = 0; index < colorIds.length; index++) {
            mLevelColors.add(new LevelColorPair(levels[index], UIUtil.getColor(getContext(), colorIds[index])));
        }
        mLevelCount = mLevelColors.size();
        invalidate();
    }

    public void setColorLever(List<LevelColorPair> colorLevelInfo) {
        if (colorLevelInfo == null) {
            return;
        }
        mLevelColors = new ArrayList<>();
        mLevelColors.clear();
        mLevelColors.addAll(colorLevelInfo);
        mLevelCount = mLevelColors.size();
        invalidate();
    }

    public void setCurrentValue(int value) {
        this.mCurrentValue = value;
        invalidate();
    }

    public int getSectionColor() {
        int sectionColor = UIUtil.getColor(getContext(), R.color.colorAccent);
        for (LevelColorPair levelColorPair : mLevelColors) {
            if (levelColorPair.levelNumber > mCurrentValue) {
                sectionColor = levelColorPair.color;
                break;
            }
        }
        return sectionColor;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (Check.isEmpty(mLevelColors)) {
            return;
        }

        mGradientPaint.setColor(mLevelColors.get(0).color);
        canvas.drawArc(mRectLeft, 90, 180, true, mGradientPaint);

        float widthIndex = mPaintStroke / 2;
        mGradientPaint.setStrokeWidth(mPaintStroke);
        float perGap = (mWidth - mPaintStroke) * 1.0f / mLevelColors.get(mLevelCount - 1).levelNumber;
        int lastNumber = 0;
        String text = "0";
        canvas.drawText(text, 0, mPaddingTop + mPaintStroke + mTextSize, mTextPaint);

        float cursorCenter = widthIndex + mCurrentValue * perGap;

        for (int index = 0; index < mLevelCount; index++) {

            LevelColorPair levelColorPair = mLevelColors.get(index);
            mGradientPaint.setColor(levelColorPair.color);

            float perLength = perGap * (levelColorPair.levelNumber - lastNumber);
            canvas.drawLine(widthIndex, mPaddingTop, widthIndex + perLength, mPaddingTop, mGradientPaint);

            widthIndex += perLength;
            lastNumber = levelColorPair.levelNumber;

            if (index == mLevelCount - 1) {
                text = "+";
            } else {
                text = levelColorPair.levelNumber + "";
            }
            canvas.drawText(text, widthIndex - mTextSize / 2, mPaddingTop + mPaintStroke + mTextSize, mTextPaint);
        }
        canvas.drawArc(mRectRight, 270, 180, true, mGradientPaint);

        mGradientPaint.setColor(UIUtil.getColor(getContext(), R.color.colorAccent));
        canvas.drawCircle(cursorCenter, mPaddingTop, mPaintStroke, mGradientPaint);

    }

    public static class LevelColorPair {
        int levelNumber;
        int color;

        public LevelColorPair(int levelNumber, int color) {
            this.levelNumber = levelNumber;
            this.color = color;
        }
    }
}
