package com.example.devoir1.dal;

import com.example.devoir1.bll.model.Task;

import java.util.Date;
import java.util.List;

public interface ITaskDAO {

    public List<Task> findAllTask();

    public void addNewTask(int id, String title, String description, String category, Date date, int priority);

    public void updateTask(int id, String title, String description, String category, Date date, int priority);

    public void updateIsDoneTask(int id,int isDone);

    public void deleteTask(int id);

    public List<Task> findTaskWithKeyword(String keyword);




}
