package com.example.risbo.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

public class ProgressionActivity extends AppCompatActivity {
    private static final  int PROGRESS = 0x1;

    private ProgressBar mProgress;
    private int mProgressStatut = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progression);
    }


    public void OKButtonClick(View view) {

        EditText progressionNb = (EditText) findViewById(R.id.progressNb);
        mProgressStatut = Integer.valueOf(progressionNb.getText().toString());

        mProgress = (ProgressBar) findViewById(R.id.the_progress_bar);
        mProgress.setProgress(mProgressStatut);



    }
}
