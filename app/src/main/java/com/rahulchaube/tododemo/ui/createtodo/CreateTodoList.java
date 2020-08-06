package com.rahulchaube.tododemo.ui.createtodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.rahulchaube.tododemo.R;
import com.rahulchaube.tododemo.data.entitiy.TodoTable;
import com.rahulchaube.tododemo.util.Constant;

public class CreateTodoList extends AppCompatActivity {

    EditText editTextTitle,editTextContent;
    TodoTable todoTable;
    TextInputLayout textInputLayoutTitle,textInputLayoutContent;
    int status=1;
    LinearLayout linearLayoutCheck;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_todo_list);
        editTextTitle=findViewById(R.id.et_todo_title);
        editTextContent=findViewById(R.id.et_todo_content);
        linearLayoutCheck=findViewById(R.id.ll_check);
        textInputLayoutTitle=findViewById(R.id.til_todo_title);
        textInputLayoutContent=findViewById(R.id.til_todo_content);
        checkBox=findViewById(R.id.taskCompleted);
        Intent intent=getIntent();
        if (intent!=null && intent.hasExtra(Constant.DATA))
        {
            todoTable= (TodoTable) intent.getSerializableExtra(Constant.DATA);
            prePopulatedData(todoTable);
            linearLayoutCheck.setVisibility(View.VISIBLE);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    status=2;
                else
                    status=1;
                Log.e("Status Chenged "," ********* ");
            }
        });
    }

    private void prePopulatedData(TodoTable todoTable) {
        editTextContent.setText(todoTable.getContent());
        editTextTitle.setText(todoTable.getTitle());
        if (todoTable.getStatus()==2)
        {
            checkBox.setChecked(true);
        }
        else
            checkBox.setChecked(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_list_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.save)
        {
            saveData();
        }
        return true;
    }

    public void saveData()
    {
        if (validateField())
        {
            long id= todoTable == null ? 0L : todoTable.getId();
            TodoTable todoTable=new TodoTable(id,editTextTitle.getText().toString(),
                    editTextContent.getText().toString(),status,System.currentTimeMillis());
            Intent intent=new Intent();
            intent.putExtra(Constant.DATA,todoTable);
            setResult(RESULT_OK,intent);
            finish();
        }

    }
    boolean validateField()
    {
        if (editTextTitle.getText().toString().isEmpty())
        {
            textInputLayoutTitle.setError(getString(R.string.enter_title));
            editTextTitle.requestFocus();
            return false;
        }
        if (editTextContent.getText().toString().isEmpty())
        {
            textInputLayoutContent.setError(getString(R.string.content));
            editTextContent.requestFocus();
            return false;
        }
        return true;

    }
}