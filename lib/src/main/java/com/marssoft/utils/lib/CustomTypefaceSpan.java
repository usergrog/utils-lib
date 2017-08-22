package com.marssoft.utils.lib;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

/**
 * Created by Alexey on 27.06.2015.
 */
public class CustomTypefaceSpan extends MetricAffectingSpan {

    private final Typeface mTypeface;

    public CustomTypefaceSpan(Typeface typeface) {
        mTypeface = typeface;
    }

/*
    public int getSpanTypeId() {
        return TextUtils.TYPEFACE_SPAN;
    }
*/

    private static void apply(Paint paint, Typeface typeface) {
        int oldStyle;

        Typeface old = paint.getTypeface();
        if (old == null) {
            oldStyle = 0;
        } else {
            oldStyle = old.getStyle();
        }

        int fake = oldStyle & ~typeface.getStyle();

        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(typeface);
    }

    public int describeContents() {
        return 0;
    }

    public Typeface getTypeface() {
        return mTypeface;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        apply(ds, mTypeface);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        apply(paint, mTypeface);
    }
}
