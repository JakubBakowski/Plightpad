package com.plightpad.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.plightpad.R;
import com.plightpad.tools.DrawableUtils;
import com.plightpad.tools.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szczypiorek on 17.07.2017.
 */

public class AdminListAdapter extends RecyclerView.Adapter<AdminListAdapter.ViewHolder> {

    private final Activity context;
    private final List<String> web;
    private final List<Bitmap> bitmaps;
    private List<String> editTextValues;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageView mImageView;
        public TextInputLayout mTextInputLayout;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.admin_item_text_view);
            mImageView = (ImageView)v.findViewById(R.id.admin_item_image_view);
            mTextInputLayout = (TextInputLayout)v.findViewById(R.id.admin_item_edit_lane_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdminListAdapter(Activity context,
                            List<String> web, List<Bitmap> bitmaps, final int NUMBER_OF_LANES) {
        this.context = context;
        this.web = web;
        this.bitmaps = bitmaps;
        editTextValues = new ArrayList<>();
        for(int i=0;i<NUMBER_OF_LANES;i++){
            editTextValues.add("");
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdminListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        RelativeLayout rl = (RelativeLayout)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_list_view_item, parent, false);
        ViewHolder vh = new ViewHolder(rl);
        return vh;
    }

    @Override
    public int getItemCount() {
        return web.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(web.get(position));
        Glide.with(context)
                .load(DrawableUtils.bitmapToByte(bitmaps.get(position)))
                .override(Utils.dpToPx(128), Utils.dpToPx(128))
                .into(holder.mImageView);
        holder.mTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editTextValues.set(position, charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public List<String> getEditTextStringsList(){
        return editTextValues;
    }
}


