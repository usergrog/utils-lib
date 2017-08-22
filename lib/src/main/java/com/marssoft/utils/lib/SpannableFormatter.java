package com.marssoft.utils.lib;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StyleSpan;
import android.view.View;

/**
 * Created by Alexey on 27.06.2015.
 */
public class SpannableFormatter {
    /**
     * Returns a CharSequence that concatenates the specified array of CharSequence
     * objects and then applies a list of zero or more tags to the entire range.
     *
     * @param content an array of character sequences to apply a style to
     * @param tags    the styled span objects to apply to the content
     *                such as android.text.style.StyleSpan
     */
    private static CharSequence apply(CharSequence[] content, Object... tags) {
        SpannableStringBuilder text = new SpannableStringBuilder();
        openTags(text, tags);
        for (CharSequence item : content) {
            text.append(item);
        }
        closeTags(text, tags);
        return text;
    }

    /**
     * Iterates over an array of tags and applies them to the beginning of the specified
     * Spannable object so that future text appended to the text will have the styling
     * applied to it. Do not call this method directly.
     */
    private static void openTags(Spannable text, Object[] tags) {
        for (Object tag : tags) {
            text.setSpan(tag, 0, 0, Spannable.SPAN_MARK_MARK);
        }
    }

    /**
     * "Closes" the specified tags on a Spannable by updating the spans to be
     * endpoint-exclusive so that future text appended to the end will not take
     * on the same styling. Do not call this method directly.
     */
    private static void closeTags(Spannable text, Object[] tags) {
        int len = text.length();
        for (Object tag : tags) {
            if (len > 0) {
                text.setSpan(tag, 0, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                text.removeSpan(tag);
            }
        }
    }

    /**
     * Returns a CharSequence that applies boldface to the concatenation
     * of the specified CharSequence objects.
     */
    public static CharSequence bold(CharSequence... content) {
        return apply(content, new StyleSpan(Typeface.BOLD));
    }

    /**
     * Returns a CharSequence that applies normalface to the concatenation
     * of the specified CharSequence objects.
     */
    public static CharSequence normal(CharSequence... content) {
        return apply(content, new StyleSpan(Typeface.NORMAL));
    }

    /**
     * Returns a CharSequence that applies italics to the concatenation
     * of the specified CharSequence objects.
     */
    public static CharSequence italic(CharSequence... content) {
        return apply(content, new StyleSpan(Typeface.ITALIC));
    }

    /**
     * Returns a CharSequence that applies a foreground color to the
     * concatenation of the specified CharSequence objects.
     */
    public static CharSequence color(int color, CharSequence... content) {
        return apply(content, new ForegroundColorSpan(color));
    }

    /**
     * Returns a CharSequence that adds image to the
     * concatenation of the specified CharSequence objects.
     */
    public static CharSequence image(Drawable drawable, CharSequence... content) {
        return apply(content, new ImageSpan(drawable));
    }

    /**
     * Returns a CharSequence that applies a clickable span to the
     * concatenation of the specified CharSequence objects.
     */
    public static CharSequence clickable(final View.OnClickListener callback, CharSequence... content) {
        return apply(content, new ClickableSpan(){
            @Override
            public void onClick(View widget) {
                callback.onClick(widget);
            }
        });
    }

    /**
     * Returns a CharSequence that applies a size to the
     * concatenation of the specified CharSequence objects.
     */
    public static CharSequence size(int size, CharSequence... content) {
        return apply(content, new AbsoluteSizeSpan(size, true));
    }


    /**
     * Returns a CharSequence that applies a scaleX to the
     * concatenation of the specified CharSequence objects.
     */
    public static CharSequence scaleX(float proportion, CharSequence... content) {
        return apply(content, new ScaleXSpan(proportion));
    }

    /**
     * Returns a CharSequence that applies a typeface to the
     * concatenation of the specified CharSequence objects.
     */
    public static CharSequence typeface(Typeface typeface, CharSequence... content) {
        return apply(content, new CustomTypefaceSpan(typeface));
    }

    public static CharSequence replacement(int textColor, int backgroundColor,
                                           final CharSequence... content) {
        return apply(content, new RoundedBackground(textColor, backgroundColor));
    }


    private static class RoundedBackground extends ReplacementSpan {

        private static final int padding = (int) (5 * 2/*density*/);

        private int mTextColor;

        private int mBackgroundColor;

        private RoundedBackground(int textColor, int backgroundColor) {
            mTextColor = textColor;
            mBackgroundColor = backgroundColor;
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end,
                           Paint.FontMetricsInt fm) {
            return getTextWidth(paint, text, start, end);
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top,
                         int y,
                         int bottom, Paint paint) {

            // first height is higher on about 4 pixels, I don't know why. Alexey
            if (top == 0) {
                top = 4;
            }
//            int textSize = (int)((bottom - top - padding * 4) * FishkaApp.density);
            int textSize = (int) paint.getTextSize();
            //paint.setTextSize(textSize);
            float textWidth = getTextWidth(paint, text, start, end);
            RectF rect = new RectF(x, top, x + textWidth + padding * 2, top + textSize + padding);
            paint.setColor(mBackgroundColor);
            canvas.drawRoundRect(rect, 10, 10, paint);
            paint.setColor(mTextColor);
            canvas.drawText(text, start, end, x + padding, top + textSize, paint);
        }

        private int getTextWidth(Paint paint, CharSequence text, int start, int end) {
            return Math.round(paint.measureText(text, start, end));
        }

    }

}

