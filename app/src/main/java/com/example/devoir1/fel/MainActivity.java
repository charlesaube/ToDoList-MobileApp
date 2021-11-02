package com.example.devoir1.fel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.devoir1.R;
import com.example.devoir1.bll.model.Task;
import com.example.devoir1.bll.service.ApplicationController;
import com.example.devoir1.dal.ITaskDAO;
import com.example.devoir1.bll.util.TaskAdapter;
import com.example.devoir1.dal.TaskSQLiteDAO;

public class MainActivity extends AppCompatActivity {

    public ITaskDAO dao;

    private static MainActivity instance;
    private String searchQuery;
    private RecyclerView recyclerView;
    private TextView noTaskFound;
    private ImageView notFoundImage;
    private LinearLayoutManager llm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        this.dao = new TaskSQLiteDAO();

        notFoundImage = findViewById(R.id.notFoundImage);
        noTaskFound = findViewById(R.id.no_task_found);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);

        if(dao.findAllTask().isEmpty()){
            noTaskFound.setVisibility(View.VISIBLE);
            notFoundImage.setVisibility(View.VISIBLE);
        }else {
            noTaskFound.setVisibility(View.INVISIBLE);
            notFoundImage.setVisibility(View.INVISIBLE);
        }
        recyclerView.setAdapter(new TaskAdapter(dao.findAllTask()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu_option, menu);

        MenuItem ourSearchItem = menu.findItem(R.id.search);

        SearchView sv = (SearchView) ourSearchItem.getActionView();

        sv.setQueryHint(ApplicationController.getAppContext().getString(R.string.search));


        sv.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                if(dao.findAllTask().isEmpty()){
                    noTaskFound.setVisibility(View.VISIBLE);
                    notFoundImage.setVisibility(View.VISIBLE);
                }else {
                    noTaskFound.setVisibility(View.INVISIBLE);
                    notFoundImage.setVisibility(View.INVISIBLE);
                }

                recyclerView.setAdapter(new TaskAdapter(dao.findAllTask()));
                recyclerView.setHasFixedSize(true);
                return true;
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(dao.findTaskWithKeyword(searchQuery).isEmpty()){
                    noTaskFound.setVisibility(View.VISIBLE);
                    notFoundImage.setVisibility(View.VISIBLE);
                }else {
                    noTaskFound.setVisibility(View.INVISIBLE);
                    notFoundImage.setVisibility(View.INVISIBLE);
                }
                recyclerView.setAdapter(new TaskAdapter(dao.findTaskWithKeyword(searchQuery)));
                recyclerView.setHasFixedSize(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                return true;
            }
    });
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case R.id.add_item:
                Intent intent = new Intent(MainActivity.this,AddTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.show_item:
                if(dao.findAllTask().isEmpty()){
                    noTaskFound.setVisibility(View.VISIBLE);
                    notFoundImage.setVisibility(View.VISIBLE);
                }else {
                    noTaskFound.setVisibility(View.INVISIBLE);
                    notFoundImage.setVisibility(View.INVISIBLE);
                }
                recyclerView.setAdapter(new TaskAdapter(dao.findAllTask()));
                recyclerView.setHasFixedSize(true);
                break;
        }
        return true;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    public void addNewTask(View v){
        Intent intent = new Intent(MainActivity.this,AddTaskActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToDetails(Task task){
        Intent intent = new Intent(MainActivity.this,TaskDetailsActivity.class);
        intent.putExtra("ID",String.valueOf(task.getId()));
        intent.putExtra("TITLE",task.getTitle());
        intent.putExtra("DESC",task.getDescription());
        intent.putExtra("CATEGORY",task.getCategory());
        intent.putExtra("DATE",task.getDate().toLocaleString());
        intent.putExtra("PRIORITY",String.valueOf(task.getPriority()));

        startActivity(intent);


    }
}
