package com.example.devoir1.bll.util;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.devoir1.R;
import com.example.devoir1.bll.model.Task;

public class TaskView extends RecyclerView.ViewHolder {
    private TextView title;
    private CheckBox checkBox;

    public TaskView(View view) {
        super(view);
        this.title = view.findViewById(R.id.task_title);
        this.checkBox = view.findViewById(R.id.task_checkbox);
    }
    public void setItem(Task task){
        this.title.setText(task.getTitle());

        if(task.isDone()){
            this.checkBox.setChecked(true);
        }
        else{
            this.checkBox.setChecked(false);
        }

    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

}
