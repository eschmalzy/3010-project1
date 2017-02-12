package com.example.craft.budget;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by craft on 2/6/2017.
 */
public class Budget {
    private String mBudget;

    public void setBudget(String budget) { mBudget = budget;}

    public String getBudget() {return mBudget;}

    public Budget(String budget) {
        mBudget = budget;
    }
}
