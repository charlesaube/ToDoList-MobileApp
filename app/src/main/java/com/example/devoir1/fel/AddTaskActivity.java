package com.example.devoir1.fel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.devoir1.R;
import com.example.devoir1.dal.ITaskDAO;

import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity{

    EditText addTitle, addDesc, addCategory, addDate;
    Spinner addPriority;

    Button addButton ,addPickDate;
    int priority, requestCode, year, month, day;
    ITaskDAO dao;
    Date currentDate;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dao = MainActivity.getInstance().dao;

        addTitle = findViewById(R.id.add_task_title);
        addDesc = findViewById(R.id.add_task_desc);
        addCategory = findViewById(R.id.add_task_category);
        addDate = findViewById(R.id.add_task_date);
        addPriority = findViewById(R.id.add_task_priority);
        addButton = findViewById(R.id.add_button);
        addPickDate = findViewById(R.id.add_pickDate);

        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this,R.array.priority_array,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        addPriority.setAdapter(adapter);

        addPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(1);

            }
        });

    }

    public void addNewTask(View view){
        dao.addNewTask(1,addTitle.getText().toString(),addDesc.getText().toString(),addCategory.getText().toString(),currentDate,priority);
        Intent intent1 = new Intent(AddTaskActivity.this,MainActivity.class);
        startActivity(intent1);
        finish();
    }

    public void pickDate(View view){
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        DatePickerDialog dpd =  new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                currentDate = new Date(year-1900, month, day);
                addDate.setText(day + "/" + month + "/" +  year );

            }
        }, day, month, year);

        dpd.show();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;

    }


    @Override
    public void onActivityResult(int resultRequestCode, int resultCode, Intent result){
        super.onActivityResult(resultRequestCode,resultCode,result);

        if(resultRequestCode == requestCode){
            if(resultCode == RESULT_OK){
                year = result.getIntExtra("Year",2020);
                month = result.getIntExtra("Month",1);
                day = result.getIntExtra("Day",1);
                addDate.setText(String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day));
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case R.id.add_item:
                Intent intent = new Intent(AddTaskActivity.this,AddTaskActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.show_item:
                Intent intent1 = new Intent(AddTaskActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
        }

        return true;


    }
}
