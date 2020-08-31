package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.todolist.Database.DatabaseHelper;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView listViewCV;
    EditText edtTaskName;
    Button btnAddCV;
    DatabaseHelper databaseHelper;

    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewCV = findViewById(R.id.lv);
        edtTaskName = findViewById(R.id.edtTaskName);
        btnAddCV = findViewById(R.id.addCV);

        databaseHelper = new DatabaseHelper(this);


        btnAddCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = String.valueOf(edtTaskName.getText());
                databaseHelper.InsertTask(task);
                loadTask();
            }
        });

        loadTask();
    }

    public void loadTask() {
        ArrayList<String> taskList = databaseHelper.getTask();
        if (mAdapter == null){
            mAdapter = new ArrayAdapter<String>(this, R.layout.item_cv, R.id.tvTaskName, taskList);
            listViewCV.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }
    }
    public void deleteTask(View view){
        View parent = (View) view.getParent();
        TextView textView = findViewById(R.id.tvTaskName);
        String task = String.valueOf(textView.getText());
        databaseHelper.DeleteTask(task);
        loadTask();
        Toast.makeText(this, "Xóa " + task, Toast.LENGTH_SHORT).show();
    }


}