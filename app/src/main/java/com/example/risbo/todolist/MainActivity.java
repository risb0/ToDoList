package com.example.risbo.todolist;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static android.content.Context.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    private ArrayList todoList;
    private ArrayAdapter<String> adapter;
    private ListView todoListView;
    private String fileName = "filename.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = new ArrayList();
        adapter = new ArrayAdapter<>(this,
                R.layout.todolistcell,
                R.id.the_item_text,
                todoList);

        todoListView = (ListView) findViewById(R.id.todo_list);
        todoListView.setAdapter(adapter);

        todoListView.setOnItemLongClickListener(this);
        todoListView.setOnItemClickListener(this);

        try {
            readSavedList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void addItem(View view) {

        EditText itemText = (EditText) findViewById(R.id.todo_item);
        // store the item to add to the list in a variable
        String todoItem = itemText.getText().toString();

        todoList.add(todoItem);
        adapter.notifyDataSetChanged();

        try {
            saveChanges();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        clearText();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int index, long id) {

        todoList.remove(index);
        adapter.notifyDataSetChanged();

        return false;

    }

    public void saveChanges() throws FileNotFoundException {


        // 1) write output to a file
        PrintStream out = new PrintStream(openFileOutput(fileName, MODE_PRIVATE));


        for (int i = 0; i < todoList.size(); i++) {

            String line = todoList.get(i).toString();
            out.println(line);

        }

        out.close();
    }


    public void readSavedList() throws FileNotFoundException {

        Scanner scan = new Scanner(openFileInput(fileName));


        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            todoList.add(line);

        }

        Log.d("Log", todoList.toString());

    }

    public void clearText() {


        EditText editText = (EditText) findViewById(R.id.todo_item);

        if (isEmpty(editText) == false) {
            editText.setText("");
        }
    }

    public void hideKeyboard(View view) {
         /* hide keyboard */
        Toast.makeText(this, "Keyboard dismissed!", Toast.LENGTH_SHORT).show();

        ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    protected boolean isEmpty (EditText editText){
        return editText.getText().toString().trim().length() == 0;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
        Intent intent = new Intent(this, ProgressionActivity.class);
        startActivity(intent);

    }
}