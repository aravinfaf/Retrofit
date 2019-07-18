package com.myapplication.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.myapplication.R;
import com.myapplication.utils.Task;
import com.myapplication.view.activity.UpdateTaskActivity;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    Context context;
    List<Task> taskList;

    public TaskAdapter(Context context, List<Task> taskList){
        this.context=context;
        this.taskList=taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_tasks, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Task task=taskList.get(position);
        Log.e("GetTask",task.getTask());

        holder.textViewTask.setText(""+task.getTask());
        holder.textViewDesc.setText(""+task.getDesc());
        holder.textViewFinishBy.setText(""+task.getFinishby());

        if(task.isFinished()==true){
            holder.textViewStatus.setText("Completed");
        }else{
            holder.textViewStatus.setText("Not Completed");
        }

        holder.ivedit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Task task=taskList.get(position);
                Intent intent = new Intent(context, UpdateTaskActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("task", task);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStatus, textViewTask, textViewDesc, textViewFinishBy;
        ImageView ivedit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewFinishBy = itemView.findViewById(R.id.textViewFinishBy);
            ivedit = itemView.findViewById(R.id.ivedit);

            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Task task=taskList.get(getAdapterPosition());
            Intent intent = new Intent(context, UpdateTaskActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("task", task);
            context.startActivity(intent);
        }
    }
}