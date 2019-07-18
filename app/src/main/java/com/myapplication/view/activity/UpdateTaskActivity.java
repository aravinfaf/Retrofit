package com.myapplication.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.myapplication.R;
import com.myapplication.utils.DatabasClient;
import com.myapplication.utils.Task;

public class UpdateTaskActivity extends AppCompatActivity {

    private EditText editTextTask, editTextDesc, editTextFinishBy;
    private CheckBox checkBoxFinished;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        editTextTask = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextFinishBy = findViewById(R.id.editTextFinishBy);
        checkBoxFinished = findViewById(R.id.checkBoxFinished);

        final Task task= (Task) getIntent().getSerializableExtra("task");

        Log.e("Serialize",task.getTask()+task.getDesc());

        loadTask(task);


        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateTask(task);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                deletetask(task);
            }
        });
    }

    private void loadTask(Task task) {

        editTextTask.setText(task.getTask());
        editTextDesc.setText(task.getDesc());
        editTextFinishBy.setText(task.getFinishby());
        checkBoxFinished.setChecked(task.isFinished());
    }

    private void updateTask(final Task task){

        final String sTask = editTextTask.getText().toString().trim();
        final String sDesc = editTextDesc.getText().toString().trim();
        final String sFinishBy = editTextFinishBy.getText().toString().trim();

        if (sTask.isEmpty()) {
            editTextTask.setError("Task required");
            editTextTask.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            editTextDesc.setError("Desc required");
            editTextDesc.requestFocus();
            return;
        }

        if (sFinishBy.isEmpty()) {
            editTextFinishBy.setError("Finish by required");
            editTextFinishBy.requestFocus();
            return;
        }

        class UpdateTask extends AsyncTask<Void,Void,Void>{

            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {

                task.setTask(editTextTask.getText().toString().trim());
                task.setDesc(editTextDesc.getText().toString().trim());
                task.setFinishby(editTextFinishBy.getText().toString().trim());
                task.setFinished(checkBoxFinished.isChecked());

                Log.e("Task",sTask+sDesc+sFinishBy);

                DatabasClient.getmInstance(getApplicationContext()).getdatabase().taskdao().update(task);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, ViewDetails.class));
            }
        }

        UpdateTask updateTask=new UpdateTask();
        updateTask.execute();
    }

    void deletetask(final Task task){

        final String sTask = editTextTask.getText().toString().trim();
        final String sDesc = editTextDesc.getText().toString().trim();
        final String sFinishBy = editTextFinishBy.getText().toString().trim();

        if (sTask.isEmpty()) {
            editTextTask.setError("Task required");
            editTextTask.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            editTextDesc.setError("Desc required");
            editTextDesc.requestFocus();
            return;
        }

        if (sFinishBy.isEmpty()) {
            editTextFinishBy.setError("Finish by required");
            editTextFinishBy.requestFocus();
            return;
        }

        class DeleteTask extends AsyncTask<Void,Void,Void>{

            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {

                task.setTask(sTask);
                task.setDesc(sDesc);
                task.setFinishby(sFinishBy);
                task.setFinished(checkBoxFinished.isChecked());

                Log.e("Task",sTask+sDesc+sFinishBy);

                DatabasClient.getmInstance(getApplicationContext()).getdatabase().taskdao().delete(task);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, ViewDetails.class));
            }
        }

        DeleteTask updateTask=new DeleteTask();
        updateTask.execute();

    }
}
