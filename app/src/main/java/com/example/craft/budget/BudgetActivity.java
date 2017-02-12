package com.example.craft.budget;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class BudgetActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CREATE = 0;
    private final String PREFS_TASKS = "prefs_tasks";
    private final String KEY_TASKS_LIST = "list";

    private Button mCreateButton;
    private ArrayList<Budget> mBudgetList;
    private ArrayAdapter<String> mBudgetAdapter;
    private ArrayList<String> mBudgetListStrings;
    private ListView lvItems;


    @Override
    protected void onStop(){
        super.onStop();
        StringBuilder savedList = new StringBuilder();
        for(Budget b : mBudgetList){
            String s = b.getBudget();
            savedList.append(s);
            savedList.append(",");
        }
        getSharedPreferences(PREFS_TASKS, MODE_PRIVATE).edit().putString(KEY_TASKS_LIST, savedList.toString()).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        String savedList = getSharedPreferences(PREFS_TASKS, MODE_PRIVATE).getString(KEY_TASKS_LIST,null);
        if(savedList != null){
            String[] items = savedList.split(",");
            mBudgetList = new ArrayList<Budget>();
            for(String i : items){
                Budget b = new Budget(i);
                mBudgetList.add(b);
            }
        } else {
            mBudgetList = new ArrayList<Budget>();
        }

        lvItems = (ListView) findViewById(R.id.lvItems);
        mBudgetListStrings = new ArrayList<String>();
        for (Budget b : mBudgetList){
            if(b != null) {
                String s = b.getBudget();
                mBudgetListStrings.add(s);
            }
        }
        mBudgetAdapter = new ArrayAdapter<String>(BudgetActivity.this, android.R.layout.simple_list_item_1, mBudgetListStrings);
        lvItems.setAdapter(mBudgetAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                taskSelected(position);
            }
        });

        mCreateButton = (Button) findViewById(R.id.create_button);
        mCreateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(BudgetActivity.this, CreateActivity.class);
                startActivityForResult(i, REQUEST_CODE_CREATE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_CREATE){
            if(data == null){
                return;
            }
                String budget = CreateActivity.getNewBudget(data);
                Budget b = new Budget(budget);
                mBudgetList.add(b);
                String s = b.getBudget();
                mBudgetListStrings.add(s);
                mBudgetAdapter.notifyDataSetChanged();

        }
    }

    private void taskSelected(final int position){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BudgetActivity.this);
        alertDialogBuilder.setTitle(R.string.alert_title);

        alertDialogBuilder.setMessage(mBudgetList.get(position).getBudget()).setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mBudgetList.remove(position);
                mBudgetListStrings.remove(position);
//                mBudgetAdapter.notifyDataSetChanged();
                lvItems.setAdapter(mBudgetAdapter);

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
           AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }


}

