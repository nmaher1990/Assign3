package com.example.sdaassign32022;
/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
 * @author Chris Coughlan 2019
 */
public class FlavorViewAdapter extends RecyclerView.Adapter<FlavorViewAdapter.ViewHolder> implements View.OnClickListener{
    private static final String TAG = "RecyclerViewAdapter";
    private Context mNewContext;
    private RecyclerViewClickListener listener;

    //add array for each item\
    private ArrayList<FlavorAdapter> mFlavors;

    FlavorViewAdapter(Context mNewContext, ArrayList<FlavorAdapter> mflavor) {
        this.mNewContext = mNewContext;
        this.mFlavors = mflavor;
        this.listener = listener;
    }

    //declare methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_item, viewGroup, false);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: was called");

        viewHolder.imageText.setText(mFlavors.get(position).getVersionNumber());
        viewHolder.versionText.setText(mFlavors.get(position).getVersionName());
        viewHolder.imageItem.setImageResource(mFlavors.get(position).getImageResourceId());


    }

    @Override
    public int getItemCount() {
        return mFlavors.size();
    }

    @Override
    public void onClick(View v) {

    }


    //viewholder class
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageItem;
        TextView imageText;
        TextView versionText;
        RelativeLayout itemParentLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            //grab the image, the text and the layout id's
            imageItem = itemView.findViewById(R.id.imageItem);
            imageText = itemView.findViewById(R.id.flavorText);
            versionText = itemView.findViewById(R.id.flavorVers);
            itemParentLayout = itemView.findViewById(R.id.listItemLayout);

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    // onClick
    public interface RecyclerViewClickListener{
        void onClick (View v, int position);
    }
}
