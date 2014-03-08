package com.overthink.mechmaid.progress;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.overthink.mechmaid.R;

/**
 * Holds a content layout and a loading layout and allows the user to toggle between the two to indicate
 * progress. Frames default to showing the progress indicator, and must be set to show content once the content becomes
 * available.
 */
public class ProgressableContentFrame extends FrameLayout {

    public static final int DEFAULT_CONTAINER_ID = -1;

    private int contentContainerId;
    private int progressContainerId;
    private String progressText;

    /**
     * Constructs a progressable content frame within the given context
     *
     * @param context Context to which progressable content frame will be added
     */
    public ProgressableContentFrame(Context context) {
        this(context, DEFAULT_CONTAINER_ID, R.id.default_progress_container);
    }

    /**
     * Constructs a progressable content frame with the given content and progress containers
     *
     * @param context Context to which progressable content frame will be added
     * @param contentContainerId Resource ID of the content container to be added
     * @param progressContainerId Resource ID of the progress container to be added
     */
    public ProgressableContentFrame(Context context, int contentContainerId, int progressContainerId) {
        super(context);

        if(!isInEditMode()) {
            // Inflate this content frame from XML
            ((Activity) context).getLayoutInflater().inflate(R.layout.progressable_content_frame, this, true);

            // Save container IDs
            this.contentContainerId = contentContainerId;
            this.progressContainerId = progressContainerId;
        }
    }

    /**
     * See {@link #ProgressableContentFrame(android.content.Context, android.util.AttributeSet, int)}
     *
     * @param context
     * @param attrs
     */
    public ProgressableContentFrame(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructs a new progressable content frame from XML
     *
     * @param context Context to which content frame will be added
     * @param attrs
     */
    public ProgressableContentFrame(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);

        if (!isInEditMode()) {
            // Inflate this content frame from XML
            ((Activity) context).getLayoutInflater().inflate(R.layout.progressable_content_frame, this, true);

            // Get the dereferenced attributes from XML
            TypedArray attrValues =
                    context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressableContentFrame, defStyle, 0);

            // Save atrributes
            contentContainerId =
                    attrValues.getResourceId(R.styleable.ProgressableContentFrame_content_container_id, DEFAULT_CONTAINER_ID);
            progressContainerId = attrValues.getResourceId(R.styleable.ProgressableContentFrame_progress_container_id,
                    R.id.default_progress_container);

            if (attrValues.hasValue(R.styleable.ProgressableContentFrame_defualt_progress_text)) {
                setProgressText(attrValues.getString(R.styleable.ProgressableContentFrame_defualt_progress_text));
            }
        }
    }

    public int getContentContainerId() {
        return contentContainerId;
    }

    public void setContentContainerId(int contentContainerId) {
        this.contentContainerId = contentContainerId;
    }

    public int getProgressContainerId() {
        return progressContainerId;
    }

    public void setProgressContainerId(int progressContainerId) {
        this.progressContainerId = progressContainerId;
    }

    /**
     * Shows the progress indicator in this frame
     */
    public void showProgress() {
        findViewById(contentContainerId).setVisibility(GONE);
        findViewById(progressContainerId).setVisibility(VISIBLE);
    }

    /**
     * Shows the content in this frame
     */
    public void showContent() {
        findViewById(contentContainerId).setVisibility(VISIBLE);
        findViewById(progressContainerId).setVisibility(GONE);
    }

    /**
     * Set default containers if necessary
     */
    private void addDefaultContainersIfNeeded(Context context) {
        // Add default containers if not provided
        if (progressContainerId == DEFAULT_CONTAINER_ID) {
            // Add the default progress view to the layout
            View defaultProgressView = new DefaultProgressView(context);
            addView(defaultProgressView);
            // Set the progress container's ID to that of the default progress view
            progressContainerId = defaultProgressView.getId();
        }
        // TODO: Add a default content container if none is provided by user?
    }

    public String getProgressText() {
        return progressText;
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;

        // Update the view with the given text
        TextView txtProgress = (TextView) findViewById(R.id.txt_default_progress);
        txtProgress.setText(progressText);
    }
}
