package com.myapplication.view.activity;

/*
@Aravind
*/

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myapplication.R;
import com.myapplication.utils.DatabasClient;
import com.myapplication.utils.Task;
import com.myapplication.view.adapter.TaskAdapter;

import java.util.List;

public class ViewDetails extends AppCompatActivity {


    private FloatingActionButton buttonAddTask;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_dbmain);

        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask = findViewById(R.id.floating_button_add);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewDetails.this, AddTaskActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getTask();
    }

    void getTask(){


        class GetTask extends AsyncTask<Void,Void, List<Task>>{

            @Override
            protected List<Task> doInBackground(Void... voids) {

                List<Task> taskdetails=DatabasClient.getmInstance(getApplicationContext()).getdatabase().taskdao().getAll();
                return taskdetails;
            }

            @Override
            protected void onPostExecute(List<Task> taskList) {
                super.onPostExecute(taskList);

                if (taskList.size() == 0) {
                    Toast.makeText(getApplicationContext(),"no data",Toast.LENGTH_SHORT).show();
                } else {
                    TaskAdapter adapter = new TaskAdapter(getApplicationContext(), taskList);
                    recyclerView.setAdapter(adapter);
                }
            }
        }

        GetTask task=new GetTask();
        task.execute();

    }
}
