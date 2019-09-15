package com.skocur.pkpseatfinder;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;

public class CompartmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LifecycleOwner lifecycleOwner;

    private SparseArray<String> mMap = new SparseArray<>();

    public CompartmentAdapter(LifecycleOwner owner) {
        lifecycleOwner = owner;

        mMap.append(0, "1-3");
        mMap.append(1, "4-6");
        mMap.append(2, "7-9");
        mMap.append(3, "10-12");
        mMap.append(4, "13-15");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_compartment, parent, false);
        return new CompartmentVH(v, lifecycleOwner);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CompartmentVH) {
            CompartmentVH compartmentVH = (CompartmentVH) holder;
            compartmentVH.setName(mMap.get(position));
            try {
                compartmentVH.setEmptySeatsFor("" + position);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMap.size();
    }
}
