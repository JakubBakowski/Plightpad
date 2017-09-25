package com.plightpad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.plightpad.R;
import com.plightpad.items.PersonItem;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by mabak on 09.08.2017.
 */

public class PersonListViewAdapter extends ArrayAdapter<PersonItem> {
    List<PersonItem> personItemList = new ArrayList<>();
    Context context;
    PersonItem personItem;
    public PersonListViewAdapter(Context context, List<PersonItem> personItems) {
        super(context, 0, personItems);
        this.context = context;
        this.personItemList = personItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        PersonItem personItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.person_list_item, parent, false);
        }

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.name_person);
        NumberPicker numberPicker = (NumberPicker) convertView.findViewById(R.id.person_number_picker);

        // Populate the data into the template view using the data object
        tvName.setText(personItem.getName());
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                personItem.setValue(numberPicker.getValue());
            }
        });
        // Return the completed view to render on screen
        return convertView;

    }
   public int getNumberByPosition(int position){
       return getItem(position).getValue();
   }
   public String getName(int position){
       return getItem(position).getName();
   }
}