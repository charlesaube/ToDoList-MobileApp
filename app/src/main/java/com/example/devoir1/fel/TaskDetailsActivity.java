package com.example.devoir1.fel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devoir1.R;

public class TaskDetailsActivity extends AppCompatActivity {

    TextView txtTitle,txtDesc,txtCategory,txtDate,txtPriority;
    Button btnEdit;
    String id;
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);


        Intent intent =  getIntent();
        id = intent.getStringExtra("ID");

        btnEdit = findViewById(R.id.btnEditTask);

        txtTitle= (TextView) findViewById(R.id.task_detail_title);
        txtTitle.setText(intent.getStringExtra("TITLE"));

        txtDesc= (TextView) findViewById(R.id.task_detail_desc);
        txtDesc.setText(intent.getStringExtra("DESC"));

        txtCategory= (TextView) findViewById(R.id.task_detail_category);
        txtCategory.setText(intent.getStringExtra("CATEGORY"));

        txtDate= (TextView) findViewById(R.id.task_detail_date);
        txtDate.setText(intent.getStringExtra("DATE"));

        txtPriority= (TextView) findViewById(R.id.task_detail_priority);
        txtPriority.setText(intent.getStringExtra("PRIORITY"));

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskDetailsActivity.this,EditTaskActivity.class);
                intent.putExtra("ID",id);
                intent.putExtra("TITLE",txtTitle.getText().toString());
                intent.putExtra("DESC",txtDesc.getText().toString());
                intent.putExtra("CATEGORY",txtCategory.getText().toString());
                intent.putExtra("DATE",txtDate.getText().toString());
                intent.putExtra("PRIORITY",txtPriority.getText().toString());

                requestCode = 1;
                startActivityForResult(intent,requestCode);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch(item.getItemId()){
            case R.id.add_item:
                Intent intent = new Intent(TaskDetailsActivity.this,AddTaskActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.show_item:
                Intent intent1 = new Intent(TaskDetailsActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int resultRequestCode, int resultCode, Intent result){
        super.onActivityResult(resultRequestCode,resultCode,result);

        if(resultRequestCode == requestCode){
            if(resultCode == RESULT_OK){
                txtTitle.setText(result.getStringExtra("TITLE"));
                txtDesc.setText(result.getStringExtra("DESC"));
                txtCategory.setText(result.getStringExtra("CATEGORY"));
                txtDate.setText(result.getStringExtra("DATE"));
                txtPriority.setText(result.getStringExtra("PRIORITY"));
            }

        }
    }
}
