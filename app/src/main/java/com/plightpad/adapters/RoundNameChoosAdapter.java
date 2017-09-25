package com.plightpad.adapters;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.plightpad.R;
import com.plightpad.items.PersonItem;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import lombok.Getter;

/**
 * Created by mabak on 09.08.2017.
 */
@Getter
public class RoundNameChoosAdapter extends ArrayAdapter<String> {
    ArrayList<String> players = new ArrayList<>();

    Context context;

    public RoundNameChoosAdapter(Context context, ArrayList<String> players) {
        super(context, 0, players);
        this.context = context;
        this.players = players;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        String personItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.choose_round_names_item, parent, false);
        }
        // Lookup view for data population

        TextInputLayout textInputLayout = (TextInputLayout) convertView.findViewById(R.id.name_input_round_settings);
        if(position == 0 ){
            textInputLayout.getEditText().setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            textInputLayout.getEditText().setEnabled(false);
            textInputLayout.setHintEnabled(false);
        }
        // Populate the data into the template view using the data object
        textInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                players.set(position, s.toString());
            }

            public void afterTextChanged(Editable s) {

            }
        });

        // Return the completed view to render on screen
        return convertView;

    }
    public String getPlayerNameByPosition(int position){
        return players.get(position);
    }

}