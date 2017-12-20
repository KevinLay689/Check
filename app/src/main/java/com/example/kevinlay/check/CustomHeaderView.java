package com.example.kevinlay.check;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Prototype, still needs work
 *
 */

public class CustomHeaderView extends View {

    private Paint mPaint;
    private Rect areaRect;
    private RectF bounds;

    private String nameLetter;

    public CustomHeaderView(Context context) {
        super(context);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.aqua_blue));
        mPaint.setTextSize(175);

        areaRect = new Rect(75, 75, 300, 300);

        bounds = new RectF(areaRect);

        nameLetter = "";

    }

    public CustomHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.aqua_blue));
        mPaint.setTextSize(175);

        areaRect = new Rect(75, 75, 300, 300);

        bounds = new RectF(areaRect);

        nameLetter = "";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawItem(canvas, nameLetter );
    }

    public void setLetter(String nameLetter) {
        this.nameLetter = nameLetter;
        invalidate();
    }

    private void drawItem(Canvas canvas, String text) {

        canvas.drawRect(areaRect, mPaint);

        // measure text width
        bounds.right = mPaint.measureText(text, 0, text.length());

        // measure text height
        bounds.bottom = mPaint.descent() - mPaint.ascent();

        bounds.left += (areaRect.width() - bounds.right) / 2.0f;
        bounds.top += (areaRect.height() - bounds.bottom) / 2.0f;

        mPaint.setColor(Color.WHITE);
        canvas.drawText(text, bounds.left, bounds.top - mPaint.ascent(), mPaint);
    }

}
