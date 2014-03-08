package com.overthink.mechmaid.progress;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Manages all of the progressable content frames for an activity or fragment. A progressable content frame is a
 * viewgroup containing content to show as well as a loading indicator. This manager will find all of the progressable
 * content frames in your view tree and register them so you can toggle their loading/content state as needed.
 * <p>
 * To set up progressable content frames, instantiate a ProgressableContentFrameManager, passing it your
 * view tree. The manager will traverse the view tree and register all ProgressableContentFrame views so
 * that you can call {@link #showContent(int)} and {@link #showLoading(int)} passing in the id of the
 * ProgressableContentFrame to toggle between showing the content of that frame or showing the loading
 * indicator.
 * <p>
 * To use a ProgressableContentFrame view in XML, place two containers inside, one consisting of your content
 * and the other your progress indicator. Use the ProgressableContentFrame's "content_container_id" and
 * "progress_container_id" attributes to specify the loading and content container by id. If you do not specify a
 * progress container, the {@link com.overthink.mechmaid.progress.DefaultProgressView} will be displayed. See
 * {@link com.overthink.mechmaid.progress.ProgressableContentFrame} for more on customizing the default progress view.
 * <p>
 *
 */
public class ProgressableContentFrameManager {

    private static final String TAG = ProgressableContentFrameManager.class.getName();

    // A map from resource IDs to ProgressableContentFrames
    private final SparseArray<ProgressableContentFrame> progressableContentFrames = new SparseArray<ProgressableContentFrame>();

    /**
     * Default constructor
     */
    public ProgressableContentFrameManager() {
        super();
    }

    /**
     * Creates a progressable content frame manager and registers the progressable content frames in the layout
     *
     * @param fragmentViewTree the root of the fragment view tree
     */
    public ProgressableContentFrameManager(ViewGroup fragmentViewTree) {
        super();
    }

    /**
     * Recursively traverse viewtree to find all ProgressableContentFrame views and add them to a
     * map by their android:id attribute
     *
     * @param viewTree the viewtree to search for ProgressableContentFrames
     */
    public void registerProgressableContentFramesIn(ViewGroup viewTree) {
        // If the root of the view tree is a progressable content frame, add it to the set
        if (viewTree instanceof ProgressableContentFrame) {
            progressableContentFrames.put(viewTree.getId(), (ProgressableContentFrame) viewTree);
        }

        // Iterate through children of this view group
        for (int i = 0; i < viewTree.getChildCount(); i++) {
            View child = viewTree.getChildAt(i);
            // If we find a ProgressableContentFrame, add it to the set
            if (child instanceof ProgressableContentFrame) {
                progressableContentFrames.put(child.getId(), (ProgressableContentFrame) child);
                // Continue searching within this content frame
                registerProgressableContentFramesIn((ViewGroup) child);
            }
            // If we find a ViewGroup, recursively call this method on it
            else if (child instanceof ViewGroup) {
                registerProgressableContentFramesIn((ViewGroup) child);
            }
        }
    }

    /**
     * Hide the content of the progressable content frame and show its progress indicator
     *
     * @param progressableContentFrameId the XML id of the ProgressableContentFrame to set to loading
     */
    public void showLoading(int progressableContentFrameId) {
        // Look up ProgressableContentFrame in map by its id
        ProgressableContentFrame progressableContentFrame = progressableContentFrames.get(progressableContentFrameId);

        // Show the progress container
        progressableContentFrame.findViewById(progressableContentFrame.getProgressContainerId())
                .setVisibility(View.VISIBLE);

        // Hide the content
        progressableContentFrame.findViewById(progressableContentFrame.getContentContainerId())
                .setVisibility(View.INVISIBLE);
    }

    /**
     * Show the content of the progressable content frame and hide its loading indicator
     *
     * @param progressableContentFrameId the XML id of the ProgressableContentFrame to set to show content
     */
    public void showContent(int progressableContentFrameId) {
        // Look up ProgressableContentFrame in map by key
        ProgressableContentFrame progressableContentFrame = progressableContentFrames.get(progressableContentFrameId);

        // Show the progress container
        progressableContentFrame.findViewById(progressableContentFrame.getContentContainerId()).setVisibility(View.VISIBLE);

        // Hide the content
        progressableContentFrame.findViewById(progressableContentFrame.getProgressContainerId()).setVisibility(View.GONE);
    }
}
