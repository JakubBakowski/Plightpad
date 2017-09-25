package com.plightpad.controllers;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.plightpad.R;
import com.plightpad.data.ResultPaths;
import com.richpath.RichPathView;
import com.richpathanimator.AnimationListener;
import com.richpathanimator.RichPathAnimator;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by Szczypiorek on 16.09.2017.
 */

@Getter
public class ResultVectorController {

    private static final float PATH_BEGINNING = 0.0f;
    private static final float PATH_END = 1.0f;

    private RelativeLayout layout;
    private Context context;
    private List<RichPathView> resultVectors;
    private List<ResultPaths> resultVectorsPaths;
    private int actualResult;
    private ResultVectorAnimatorController resultVectorAnimatorController;

    public ResultVectorController(Context context, RelativeLayout layout) {
        this.layout = layout;
        this.context = context;
        actualResult = 0;
        resultVectors = new ArrayList<>();
        resultVectorsPaths = new ArrayList<>();
        bindAllResultVectors();
        setIconsForResultVectors();
        setupOnVectorClickListeners();
        bindPathsWithResultVectorViews();
        setupInitialStateForVectors();
        resultVectorAnimatorController = new ResultVectorAnimatorController(this);
    }

    private void setIconsForResultVectors() {
        for (RichPathView resultVector : resultVectors) {
            resultVector.setVectorDrawable(R.drawable.ic_one_square_result);
        }
    }

    private void setupOnVectorClickListeners() {
        for (RichPathView resultVector : resultVectors) {
            resultVector.setOnClickListener(s -> setResult(resultVectors.indexOf(resultVector) + 1));
        }
    }

    private void setupInitialStateForVectors() {
        for (ResultPaths resultVectorsPath : resultVectorsPaths) {
            resultVectorsPath.getInlineCrossed().setTrimPathEnd(PATH_BEGINNING);
            resultVectorsPath.getInlineHorizontal().setTrimPathEnd(PATH_BEGINNING);
            resultVectorsPath.getFilledDownTriangle().setTrimPathEnd(PATH_BEGINNING);
        }
    }

    private void bindPathsWithResultVectorViews() {
        for (RichPathView resultVector : resultVectors) {
            resultVectorsPaths.add(new ResultPaths(resultVector));
        }
    }

    private void bindAllResultVectors() {
        resultVectors.add((RichPathView) layout.findViewById(R.id.result_vector1));
        resultVectors.add((RichPathView) layout.findViewById(R.id.result_vector2));
        resultVectors.add((RichPathView) layout.findViewById(R.id.result_vector3));
        resultVectors.add((RichPathView) layout.findViewById(R.id.result_vector4));
        resultVectors.add((RichPathView) layout.findViewById(R.id.result_vector5));
        resultVectors.add((RichPathView) layout.findViewById(R.id.result_vector6));
        resultVectors.add((RichPathView) layout.findViewById(R.id.result_vector7));
    }

    private void setResult(int result) {
        if (actualResult != result) {
            resultVectorAnimatorController.animate(actualResult, result);
            actualResult = result;
        }
    }

    public int getResult() {
        return actualResult;
    }

}
