package com.example.devoir1.fel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.devoir1.R;
import com.example.devoir1.bll.service.ApplicationController;
import com.example.devoir1.dal.ITaskDAO;

import java.util.Calendar;
import java.util.Date;

public class EditTaskActivity extends AppCompatActivity {

     private EditText titleEdit,descEdit,categoryEdit,dateEdit;
     private Spinner priorityEdit;
     private Button btnUpdate, btnDelete,btnPickDate;
     private int requestCode,year, month, day, id,priority;
     ITaskDAO dao;

    Date currentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        dao = MainActivity.getInstance().dao;
        Intent intent = getIntent();

        id = Integer.parseInt(intent.getStringExtra("ID"));
        titleEdit = findViewById(R.id.task_edit_title);
        titleEdit.setText(intent.getStringExtra("TITLE"));

        descEdit = findViewById(R.id.task_edit_desc);
        descEdit.setText(intent.getStringExtra("DESC"));

        categoryEdit = findViewById(R.id.task_edit_category);
        categoryEdit.setText(intent.getStringExtra("CATEGORY"));


        dateEdit = findViewById(R.id.task_edit_date);
        dateEdit.setText(intent.getStringExtra("DATE"));

        priorityEdit = findViewById(R.id.task_edit_priority);


        btnUpdate = findViewById(R.id.btnUpdateTask);
        btnDelete = findViewById(R.id.btnDeleteTask);
        btnPickDate = findViewById(R.id.edit_pickDate);

        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this,R.array.priority_array,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        priorityEdit.setAdapter(adapter);
        priorityEdit.setSelection(Integer.parseInt(intent.getStringExtra("PRIORITY"))-1);

        priorityEdit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    public void updateTask(View view){
        dao.updateTask(id,titleEdit.getText().toString(),descEdit.getText().toString(),categoryEdit.getText().toString(),currentDate,priority);
        Intent result = new Intent();
        result.putExtra("TITLE",titleEdit.getText().toString());
        result.putExtra("DESC",descEdit.getText().toString());
        result.putExtra("CATEGORY",categoryEdit.getText().toString());
        result.putExtra("DATE",dateEdit.getText().toString());
        result.putExtra("PRIORITY",String.valueOf(priority));
        setResult(RESULT_OK,result);

        finish();


    }

    public void deleteTask(View view){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(ApplicationController.getAppContext().getString(R.string.delete));
                alertDialogBuilder.setNegativeButton(ApplicationController.getAppContext().getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                    MainActivity.getInstance().dao.deleteTask(id);
                                    Intent intent = new Intent(EditTaskActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                            }
                        });

        alertDialogBuilder.setPositiveButton(ApplicationController.getAppContext().getString(R.string.no),null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void cancelUpdateTask(View view){
        Intent result = new Intent();
        setResult(RESULT_CANCELED,result);
        finish();
    }

    public void pickDate(View view){
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd =  new DatePickerDialog(EditTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                currentDate = new Date(year-1900, month, day);
                dateEdit.setText(currentDate.toLocaleString());
            }
        }, day, month, year);
        dpd.show();
    }
}
