package com.syzible.weighingscales;

import java.util.ArrayList;
import java.util.List;

public class WeightRepo {
    public List<String> getWeightLabels() {
        List<String> labels = new ArrayList<>();

        for (int i = 1; i <= 150; i++) {
            labels.add(String.valueOf(i));
        }

        return labels;
    }

    public int getStartWeight() {
        return 75;
    }
}
