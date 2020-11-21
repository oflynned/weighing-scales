package com.syzible.scales;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ScaleSliderAdapter extends RecyclerView.Adapter<ScaleSliderAdapter.ViewHolder> {

    private final List<String> data = new ArrayList<>();

    private int colour = Color.parseColor("#FF6200EE");

    private int countBetweenMarkers = 10;
    private int longSpokeHeight = 32;
    private int shortSpokeHeight = 16;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_scale_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.spoke.getLayoutParams();

        holder.spoke.setBackgroundColor(colour);

        if (shouldDrawLongSpoke(position)) {
            // first position, last position, divisions of every nth unit
            holder.value.setText(data.get(position));
            holder.value.setVisibility(View.VISIBLE);
            params.height = Screen.dpToPx(holder.itemView.getContext(), longSpokeHeight);
        } else {
            // otherwise draw a space to give it a standard width wrt to standard text size
            holder.value.setText(" ");
            holder.value.setVisibility(View.INVISIBLE);
            params.height = Screen.dpToPx(holder.itemView.getContext(), shortSpokeHeight);
        }

        holder.spoke.setLayoutParams(params);
    }

    private boolean shouldDrawLongSpoke(int index) {
        if (index == 0) {
            return true;
        }

        if (index == data.size() - 1) {
            return true;
        }

        // since we always start at 1, we have to account for this when using the *index*
        // in order to get the spoke position
        return (index + 1) % countBetweenMarkers == 0;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<String> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setSpokeColour(String hex) {
        this.colour = Color.parseColor(hex);
    }

    public void setSpokeColour(int colour) {
        this.colour = colour;
    }

    public void setCountBetweenMarkers(int countBetweenMarkers) {
        this.countBetweenMarkers = countBetweenMarkers;
    }

    public void setLongSpokeHeight(int longSpokeHeight) {
        this.longSpokeHeight = longSpokeHeight;
    }

    public void setShortSpokeHeight(int shortSpokeHeight) {
        this.shortSpokeHeight = shortSpokeHeight;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView value;
        View spoke;

        ViewHolder(View v) {
            super(v);
            value = v.findViewById(R.id.unit_value);
            spoke = v.findViewById(R.id.unit_spoke);
        }
    }
}
