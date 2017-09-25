package com.plightpad.controllers;

import com.plightpad.data.ResultPaths;
import com.richpath.RichPath;
import com.richpath.RichPathView;
import com.richpathanimator.AnimationListener;
import com.richpathanimator.RichPathAnimator;

import java.util.List;

import butterknife.Optional;

/**
 * Created by Szczypiorek on 16.09.2017.
 */

public class ResultVectorAnimatorController {

    private static final float PATH_BEGINNING = 0.0f;
    private static final float LINE_PATH_END = 1.0f;
    private static final int BASIC_ANIM_DURATION = 200;

    private List<RichPathView> resultVectors;
    private List<ResultPaths> resultVectorsPaths;

    public ResultVectorAnimatorController(ResultVectorController resultVectorController) {
        this.resultVectors = resultVectorController.getResultVectors();
        this.resultVectorsPaths = resultVectorController.getResultVectorsPaths();
    }

    public void animateAceFromZeroResult() {
        acceleratePathAndSetVisible(resultVectorsPaths.get(0).getInlineCrossed(), LINE_PATH_END);
        animateHorizontalLines();
    }

    private void animateHorizontalLines() {
        for (ResultPaths resultVectorsPath : resultVectorsPaths) {
            if (resultVectorsPaths.indexOf(resultVectorsPath) > 0) {
                acceleratePathAndSetVisible(resultVectorsPath.getInlineHorizontal(), LINE_PATH_END);
            }
        }
    }

    void animate(int actualResult, int destinationResult) {
        if (actualResult == 1) {
            decelerateResults(1, 7);
        } else if (actualResult > destinationResult) {
            decelerateResults(destinationResult, actualResult);
            acceleratePathAndSetVisible(resultVectorsPaths.get(0).getInlineCrossed(), LINE_PATH_END);
        }
        determineWhatAndAccelerate(actualResult, destinationResult);
    }

    private void determineWhatAndAccelerate(int actualResult, int destinationResult) {
        if (actualResult == 0) {
            acceleratePathAndSetVisible(resultVectorsPaths.get(0).getInlineCrossed(), LINE_PATH_END);
        }
        if (destinationResult == 1) {
            if (actualResult == 0) {
                animateAceFromZeroResult();
            } else {
                animateHorizontalLines();
            }
        } else if (destinationResult == 2) {
            acceleratePathAndSetVisible(resultVectorsPaths.get(1).getInlineCrossed(), LINE_PATH_END);
        } else {
            accelerateTriangles(destinationResult);
            acceleratePathAndSetVisible(resultVectorsPaths.get(1).getInlineCrossed(), LINE_PATH_END);
        }
    }

    private void accelerateTriangles(int destination) {
        for (int i = 2; i < destination; i++) {
            acceleratePathAndSetVisible(resultVectorsPaths.get(i).getInlineCrossed(), LINE_PATH_END);
            acceleratePathAndSetVisible(resultVectorsPaths.get(i).getFilledDownTriangle(), LINE_PATH_END);
        }
    }

    private void decelerateResults(int fromResult, int toResult) {
        for (int i = fromResult; i < toResult; i++) {
            deceleratePathIfVisible(resultVectorsPaths.get(i).getInlineCrossed());
            deceleratePathIfVisible(resultVectorsPaths.get(i).getFilledDownTriangle());
            deceleratePathIfVisible(resultVectorsPaths.get(i).getInlineHorizontal());
        }
    }

    private void deceleratePathIfVisible(RichPath richPath) {
        if (richPath.getTrimPathEnd() == LINE_PATH_END) {
            RichPathAnimator.animate(richPath)
                    .duration(BASIC_ANIM_DURATION)
                    .trimPathEnd(richPath.getTrimPathEnd(), PATH_BEGINNING)
                    .animationListener(new AnimationListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onStop() {
                            richPath.setTrimPathEnd(PATH_BEGINNING);
                        }
                    })
                    .start();
        }
    }

    private void acceleratePathAndSetVisible(RichPath richPath, float pathEnd) {
        if (richPath.getTrimPathEnd() == PATH_BEGINNING) {
            RichPathAnimator.animate(richPath)
                    .duration(BASIC_ANIM_DURATION)
                    .trimPathEnd(PATH_BEGINNING, pathEnd)
                    .animationListener(new AnimationListener() {
                        @Override
                        public void onStart() {
                            richPath.setTrimPathEnd(pathEnd);
                        }

                        @Override
                        public void onStop() {
                        }
                    })
                    .start();
        }
    }
}
