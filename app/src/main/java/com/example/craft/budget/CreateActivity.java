package com.example.craft.budget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by craft on 2/6/2017.
 */
public class CreateActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CREATE = 0;

    public static String getNewBudget(Intent i){
        return i.getStringExtra(EXTRA_CREATED_ITEM);
    }

    private static final String EXTRA_CREATED_ITEM =
            "com.example.craft.budget.created_item";

    private EditText mNewBudget;
    private Button mDoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        mNewBudget = (EditText) findViewById(R.id.budgetDescription);

        mDoneButton =(Button) findViewById(R.id.doneButton);
        mDoneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String budgetDescription = mNewBudget.getText().toString();

                if (!budgetDescription.isEmpty()){
                    Intent result = new Intent();
                    result.putExtra(EXTRA_CREATED_ITEM, budgetDescription);
                    setResult(RESULT_OK, result);
                } else {
                    setResult(RESULT_CANCELED);
                }
                finish();
            }

        });
    }




}
