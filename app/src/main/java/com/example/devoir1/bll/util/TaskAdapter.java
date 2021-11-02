package com.example.devoir1.bll.util;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devoir1.R;
import com.example.devoir1.bll.model.Task;
import com.example.devoir1.fel.MainActivity;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskView> {


    private final List<Task> tasks;
    int clickedPosition = RecyclerView.NO_POSITION;
    int getClickedPosition() { return this.clickedPosition;}

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    private void setClickedPosition(int position){
        notifyItemChanged(clickedPosition);
        clickedPosition = position;
        notifyItemChanged(clickedPosition);
    }

    @NonNull
    @Override
    public TaskView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View itemView = li.inflate(R.layout.task_layout,parent,false);
        return new TaskView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskView holder, final int position) {
        holder.setItem(tasks.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClickedPosition(position);
                MainActivity.getInstance().goToDetails(tasks.get(position));
            }
        });

        holder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MainActivity.getInstance().dao.updateIsDoneTask(tasks.get(position).getId(),1);
                } else{
                    MainActivity.getInstance().dao.updateIsDoneTask(tasks.get(position).getId(),0);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }
}
