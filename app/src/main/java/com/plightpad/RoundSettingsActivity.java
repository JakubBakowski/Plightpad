package com.plightpad;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.plightpad.adapters.PersonListViewAdapter;
import com.plightpad.adapters.RoundNameChoosAdapter;
import com.plightpad.items.PersonItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class RoundSettingsActivity extends AppCompatActivity {
    @BindView(R.id.list_view_players)
    ListView playerList;
    private RoundNameChoosAdapter playersAdapter;
    @BindView(R.id.start_round_btn)
    FancyButton startButton;
    private int numberOfPlayers=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_settings);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        numberOfPlayers = intent.getIntExtra("number_picker_value", 0);
        ArrayList<String> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add("");
        }
        playersAdapter = new RoundNameChoosAdapter(this, players);
        playerList.setAdapter(playersAdapter);
        listeners();
    }

    private void listeners() {
        startButton.setOnClickListener(s -> {
            Intent intent = new Intent(this, RoundActivity.class);
            intent.putStringArrayListExtra("PLAYERS_LISTS", playersAdapter.getPlayers());
            intent.putExtra("number_picker_value", numberOfPlayers);
            startActivity(intent);
        });
    }
}
