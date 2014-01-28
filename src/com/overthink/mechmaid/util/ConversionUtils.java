package com.overthink.mechmaid.util;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Static class for converting graphical units without supplying context. When providing dimensions outside of XML,
 * the Android framework expects the value in pixels. Of course, Android developers are taught to think in terms of
 * density-independent pixels for user interface dimensions, except text, which uses scaled pixels. These helper methods
 * will allow you to continue to think in terms of the familiar units of measure.
 */
public class ConversionUtils {

    /**
     * Converts value from density-independent pixels to pixels
     */
    public static int dpToPx(int dp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    /*
     * ConversionUtils value from pixels to density-independent pixels
     */
    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * Converts value from scaled pixels to pixels
     */
    public static int spToPx(int sp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                Resources.getSystem().getDisplayMetrics());
    }
}