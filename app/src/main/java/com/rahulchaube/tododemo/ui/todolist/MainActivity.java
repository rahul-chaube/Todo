package com.rahulchaube.tododemo.ui.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ferfalk.simplesearchview.SimpleSearchView;
import com.rahulchaube.tododemo.R;
import com.rahulchaube.tododemo.data.entitiy.TodoTable;
import com.rahulchaube.tododemo.ui.createtodo.CreateTodoList;
import com.rahulchaube.tododemo.util.Constant;

import java.util.EventListener;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements TodoListAdapter.TodoEvent {
    RecyclerView recyclerView;
    StaggeredGridLayoutManager linearLayoutManager;
    TodoListAdapter todoListAdapter;
    TodoViewModel todoViewModel;
    SimpleSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        searchView=findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.rv_todo_list);
        linearLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
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
        searchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SimpleSearchView", "Submit:" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty())
                todoListAdapter.filterString(newText);
                Log.d("SimpleSearchView", "Text changed:" + newText);
                return false;
            }

            @Override
            public boolean onQueryTextCleared() {
                todoListAdapter.resetSearch();
                Log.d("SimpleSearchView", "Text cleared");
                return false;
            }
        });
        searchView.setOnSearchViewListener(new SimpleSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                todoListAdapter.resetSearch();
            }

            @Override
            public void onSearchViewShownAnimation() {

            }

            @Override
            public void onSearchViewClosedAnimation() {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.home_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onBackPressed() {
        if (searchView.onBackPressed()) {
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void onDeleteEvent(final TodoTable todoTable) {

        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover Task")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        todoViewModel.delete(todoTable);
                        sDialog
                                .setTitleText("Deleted!")
                                .setContentText("Task List deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                })
                .show();

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