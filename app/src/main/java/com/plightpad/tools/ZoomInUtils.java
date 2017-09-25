package com.plightpad.tools;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Szczypiorek on 29.08.2017.
 */

public class ZoomInUtils {

    public static Matrix matrix = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    static final int CLICK = 3;
    static int mode = NONE;
    static float oldScale = 1.0f;

    // Remember some things for zooming
    static PointF last = new PointF();
    static PointF start = new PointF();
    static float minScale = 1f;
    static float maxScale = 4f;
    static float[] m;

    static float redundantXSpace, redundantYSpace;
    static float width, height;
    static float saveScale = 1f;
    static float right, bottom, origWidth, origHeight, bmWidth, bmHeight;

    ScaleGestureDetector mScaleDetector;

    public static void zoomIn(Context context, CircleImageView view) {
        oldScale = saveScale;
        if(saveScale<=maxScale)
        {
            saveScale += .5;
            matrix.setScale(saveScale, saveScale);
            view.setImageMatrix(matrix);
            view.invalidate();

            // Center the image
            // Center the image
            if(bmHeight>bmWidth)
            {
                redundantXSpace = width - (saveScale * bmWidth);
                redundantXSpace /= 2;
            }
            else
            {
                redundantYSpace = height - (saveScale * bmHeight) ;
                redundantYSpace /= 2;
            }

            matrix.postTranslate(redundantXSpace , redundantYSpace );
            view.setImageMatrix(matrix);
            view.invalidate();
        }
    }

    public static void zoomOut(Context context, CircleImageView view) {

        if(saveScale>=minScale)
        {
            saveScale -= .5;
            matrix.setScale(saveScale, saveScale);
            view.setImageMatrix(matrix);
            view.invalidate();

            // Center the image
            if(bmHeight>bmWidth)
            {
                redundantXSpace = width - (saveScale * bmWidth);
                redundantXSpace /= 2;
            }
            else
            {
                redundantYSpace = height - (saveScale * bmHeight) ;
                redundantYSpace /= 2;
            }
            matrix.postTranslate(redundantXSpace , redundantYSpace );
            view.setImageMatrix(matrix);
            view.invalidate();
        }
    }

}
