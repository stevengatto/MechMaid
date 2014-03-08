package com.overthink.mechmaid.progress;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.overthink.mechmaid.R;

/**
 * Default progress indicator shown in {@link com.overthink.mechmaid.progress.ProgressableContentFrame}.
 */
public class DefaultProgressView extends LinearLayout {

    /**
     * Constructs a default progress view within the given context
     *
     * @param context Context to which progressable content frame will be added
     */
    public DefaultProgressView(Context context) {
        super(context);

        if (!isInEditMode()) {
            // Inflate this content frame from XML
            ((Activity) context).getLayoutInflater().inflate(R.layout.default_progress_view, this, true);
        }
    }

    /**
     * Constructs a new default progress view from XML
     *
     * @param context Context to which view will be added
     * @param attrs Attribute set
     */
    public DefaultProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);

        if (!isInEditMode()) {
            // Inflate this content frame from XML
            ((Activity) context).getLayoutInflater().inflate(R.layout.default_progress_view, this, true);

            TypedArray attrValues =
                    context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressableContentFrame, defStyle, 0);
        }
    }
}
