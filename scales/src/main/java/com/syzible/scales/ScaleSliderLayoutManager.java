package com.syzible.scales;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ScaleSliderLayoutManager extends LinearLayoutManager {
    private RecyclerView recyclerView;
    private final MovementListener movementListener;

    public ScaleSliderLayoutManager(Context context, MovementListener movementListener) {
        super(context);
        setOrientation(HORIZONTAL);
        this.movementListener = movementListener;
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        this.recyclerView = view;
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
        int position = calculatePosition();
        movementListener.onItemChanged(position);
        return scrolled;
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            int position = calculatePosition();
            movementListener.onItemSelected(position);
        }
    }

    private int calculatePosition() {
        float recyclerViewCentreX = getRecyclerViewCentreX();
        float minDistance = recyclerView.getWidth();
        int position = -1;

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View child = recyclerView.getChildAt(i);
            float childCentreX = getDecoratedLeft(child) + (float) (getDecoratedRight(child) - getDecoratedLeft(child)) / 2;
            float newDistance = Math.abs(childCentreX - recyclerViewCentreX);
            if (newDistance < minDistance) {
                minDistance = newDistance;
                position = recyclerView.getChildLayoutPosition(child);
            }
        }

        return position;
    }

    private int getRecyclerViewCentreX() {
        return (int) (((float) (recyclerView.getRight() - recyclerView.getLeft()) / 2) + (float) recyclerView.getLeft());
    }

    public interface MovementListener {
        void onItemSelected(int selectedIndex);

        void onItemChanged(int selectedIndex);
    }
}
