package com.example.sdaassign32022;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * <p></p>Product list for a clothes store
 * Pictures all licensed from pixabay.com
 * </p>
 *
 *
 * @retrun root
 *
 * @author Nick Maher
 * @version 1.0
 */
public class ProductList extends Fragment {

    private static final String TAG = "RecyclerViewActivity";
    private ArrayList<FlavorAdapter> mFlavor = new ArrayList<>();
    private FlavorViewAdapter.RecyclerViewClickListener listener;

    public ProductList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_product_list, container, false);
        // Create an ArrayList of AndroidFlavor objects
        mFlavor.add(new FlavorAdapter("T-shirts", "1", R.drawable.tshirts));
        mFlavor.add(new FlavorAdapter("Shirts", "2", R.drawable.shirts));
        mFlavor.add(new FlavorAdapter("Jeans", "3", R.drawable.jeans));
        mFlavor.add(new FlavorAdapter("Shorts", "4", R.drawable.shorts));
        mFlavor.add(new FlavorAdapter("Jackets", "5", R.drawable.jackets));
        mFlavor.add(new FlavorAdapter("Shoes", "6", R.drawable.shoes));
        mFlavor.add(new FlavorAdapter("Socks", "7", R.drawable.socks));

        //start it with the view
        Log.d(TAG, "Starting recycler view");
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_view);
        FlavorViewAdapter recyclerViewAdapter = new FlavorViewAdapter(getContext(), mFlavor);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set the click listener
        setOnClickListener();


        return root;
    }

    /**
     * Listen for user input and respond with Toast
     *
     */
    private void setOnClickListener() {
        listener = new FlavorViewAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                String versionName = mFlavor.get(position).getVersionName();
                Toast.makeText(getContext(), "Clicked: " + versionName, Toast.LENGTH_SHORT).show();
            }
        };
    }
}
