package com.plightpad.tools;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

public class SpruceUtils {

    public static Animator getSpruceAssignedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.setVisibility(View.VISIBLE);
        return new Spruce
                .SpruceBuilder(recyclerView)
                .sortWith(new DefaultSort(/*interObjectDelay=*/50L))
                .animateWith(new Animator[]{DefaultAnimations.shrinkAnimator(recyclerView, /*duration=*/450)})
                .start();
    }

    public static Animator getSpruceAssignedToListView(ListView recyclerView) {
        recyclerView.setVisibility(View.VISIBLE);
        return new Spruce
                .SpruceBuilder(recyclerView)
                .sortWith(new DefaultSort(/*interObjectDelay=*/50L))
                .animateWith(new Animator[]{DefaultAnimations.shrinkAnimator(recyclerView, /*duration=*/450)})
                .start();
    }

}
