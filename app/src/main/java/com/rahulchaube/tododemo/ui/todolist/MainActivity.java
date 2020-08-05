package com.rahulchaube.tododemo.ui.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rahulchaube.tododemo.R;
import com.rahulchaube.tododemo.data.entitiy.TodoTable;
import com.rahulchaube.tododemo.ui.createtodo.CreateTodoList;
import com.rahulchaube.tododemo.util.Constant;

import java.util.EventListener;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoListAdapter.TodoEvent {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    TodoListAdapter todoListAdapter;
    TodoViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_todo_list);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        todoListAdapter = new TodoListAdapter(this, this);
        recyclerView.setAdapter(todoListAdapter);
        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        todoViewModel.getmAllTodo().observe(this, new Observer<List<TodoTable>>() {
            @Override
            public void onChanged(List<TodoTable> todoTables) {
                todoListAdapter.setData(todoTables);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            TodoTable todoTable = (TodoTable) data.getSerializableExtra(Constant.DATA);
            if (requestCode == Constant.UPDATE_TODO) {
                todoViewModel.update(todoTable);
            } else if (requestCode == Constant.CREATE_TODO) {
                todoViewModel.insert(todoTable);
            }
        }
    }

    @Override
    public void onDeleteEvent(TodoTable todoTable) {
        todoViewModel.delete(todoTable);
    }

    @Override
    public void updateEvent(TodoTable todoTable) {
        Intent intent = new Intent(this, CreateTodoList.class);
        intent.putExtra(Constant.DATA, todoTable);
        startActivityForResult(intent, Constant.UPDATE_TODO);
    }

    public void favClicled(View view) {
        Intent intent = new Intent(this, CreateTodoList.class);
        startActivityForResult(intent, Constant.CREATE_TODO);

    }
}