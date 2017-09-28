package com.plightpad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.plightpad.adapters.PersonListViewAdapter;
import com.plightpad.repository.ResultController;
import com.plightpad.controllers.ResultVectorController;
import com.plightpad.items.PersonItem;
import com.plightpad.boxdomain.CourseResult;
import com.plightpad.tools.AnimationUtils;
import com.plightpad.tools.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class RoundActivity extends AppCompatActivity {

    @BindView(R.id.progress_1)
    RoundCornerProgressBar progress1;

    @BindView(R.id.list_view_persons)
    ListView personList;

    @BindView(R.id.next_line_btn)
    FancyButton nextButton;

    @BindView(R.id.show_results_btn)
    FancyButton showRBtn;

    @BindView(R.id.previous_line_btn)
    FancyButton previousButton;

    @BindView(R.id.whole_layout_round_activity)
    RelativeLayout wholeLayout;

    @BindView(R.id.result_vectors_layout)
    RelativeLayout resultVectorsLayout;

    private ResultVectorController resultVectorController;

    private PersonListViewAdapter personListViewAdapter;
    private int laneNumber = 0;
    List<CourseResult> playersResults = new ArrayList<>();

    private int numberOfPlayers = 0;
    List<PersonItem> personItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);
        ButterKnife.bind(this);
        ArrayList<String> playersTemp = getIntent().getStringArrayListExtra("PLAYERS_LISTS");

        personItems = PersonItem.parseStringList(playersTemp);
        personListViewAdapter = new PersonListViewAdapter(this, personItems);
        personList.setAdapter(personListViewAdapter);
        Intent intent = getIntent();
        numberOfPlayers = intent.getIntExtra("number_picker_value", 0);
        initializeProgressBar();
        listeners();

        for (int i = 0; i < numberOfPlayers; i++) {
            playersResults.add(new CourseResult(personListViewAdapter.getName(i), 0, new ArrayList<>()));
            ResultController.save(playersResults.get(0));
        }
        if (laneNumber == 0) {
            previousButton.setEnabled(false);
        }

        initializeData();
    }

    private void initializeData() {
        resultVectorController = new ResultVectorController(this, resultVectorsLayout);
    }

    private void initializeProgressBar() {
        progress1.setProgressColor(getResources().getColor(R.color.th5));
        progress1.setProgressBackgroundColor(getResources().getColor(R.color.th2));
        progress1.setMax(17);
        progress1.setProgress(laneNumber);
    }

    private void listeners() {
        nextButton.setOnClickListener(s -> {
            AnimationUtils.animateRefresh(this, personList);
            laneNumber++;
            previousButton.setEnabled(true);
            progress1.setProgress(laneNumber);
            fullFillingList();
            if (laneNumber == 17) {
                nextButton.setEnabled(false);
            }
        });
        previousButton.setOnClickListener(s -> {
            AnimationUtils.animateRefresh(this, personList);
            laneNumber--;
            if (laneNumber == 0) {
                previousButton.setEnabled(false);
            } else {
                nextButton.setEnabled(true);
            }
            progress1.setProgress(laneNumber);
            for (int i = 0; i < numberOfPlayers; i++) {
                playersResults.get(i).setWholeResult(playersResults.get(i).getWholeResult() - playersResults.get(i).lastValue());
                playersResults.get(i).removeResult();
            }
        });
        showRBtn.setOnClickListener(s -> {
            AnimationUtils.animateWooble(this, showRBtn);
            DialogUtils.showResultDialog(this, playersResults);
        });
    }

    private void fullFillingList() {

        CourseResult result = new CourseResult();

        for (int i = 0; i < numberOfPlayers; i++) {

            playersResults.get(i).setWholeResult(playersResults.get(i).getWholeResult() + personListViewAdapter.getNumberByPosition(i));
            playersResults.get(i).addResult(personListViewAdapter.getNumberByPosition(i));
        }
        ResultController.save(result);
    }


}
