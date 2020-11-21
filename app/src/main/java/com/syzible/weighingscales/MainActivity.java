package com.syzible.weighingscales;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.syzible.scales.ScaleSliderAdapter;
import com.syzible.scales.ScaleSliderLayoutManager;
import com.syzible.scales.Screen;
import com.syzible.weighingscales.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ScaleSliderLayoutManager.MovementListener {

    private final WeightRepo repo = new WeightRepo();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    @Override
    protected void onStart() {
        super.onStart();

        int padding = Screen.getScreenWidth(this) / 2;
        binding.weightRecyclerView.setPadding(padding, 0, padding, 0);

        ScaleSliderAdapter adapter = new ScaleSliderAdapter();
        adapter.setData(repo.getWeightLabels());

        ScaleSliderLayoutManager layoutManager = new ScaleSliderLayoutManager(this, this);
        binding.weightRecyclerView.setLayoutManager(layoutManager);
        binding.weightRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.weightRecyclerView.setAdapter(adapter);

        binding.weightRecyclerView.scrollToPosition(repo.getStartWeight() - 1);
        setWeightLabel(repo.getStartWeight());
    }

    @Override
    public void onItemSelected(int selectedIndex) {

    }

    @Override
    public void onItemChanged(int selectedIndex) {
        setWeightLabel(selectedIndex + 1);
    }

    private void setWeightLabel(int weight) {
        binding.weight.setText(String.valueOf(weight));
    }
}