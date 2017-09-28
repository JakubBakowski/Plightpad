package com.plightpad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.plightpad.R;
import com.plightpad.boxdomain.CourseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mabak on 09.08.2017.
 */

public class ResultListAdapter extends ArrayAdapter<CourseResult> {
    List<CourseResult> personItemList = new ArrayList<>();
    Context context;

    public ResultListAdapter(Context context, List<CourseResult> personItems) {
        super(context, 0, personItems);
        this.context = context;
        this.personItemList = personItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        CourseResult courseResult = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.result_list_item, parent, false);
        }

        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.name_person_result);
        TextView result =(TextView)convertView.findViewById(R.id.person_result);

        // Populate the data into the template view using the data object
          name.setText(courseResult.getName());
          result.setText(String.valueOf(courseResult.getWholeResult()));
//        result.setText(personItem.getValue());
        // Return the completed view to render on screen
        return convertView;

    }


}