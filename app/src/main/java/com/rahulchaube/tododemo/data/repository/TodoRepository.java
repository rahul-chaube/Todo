package com.rahulchaube.tododemo.data.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.rahulchaube.tododemo.data.ToDoDatabase;
import com.rahulchaube.tododemo.data.dao.TodoDao;
import com.rahulchaube.tododemo.data.entitiy.TodoTable;

import java.util.List;

public class TodoRepository {
    private TodoDao mTodoDao;
    private LiveData<List<TodoTable>> mAllToDo;

    public TodoRepository(Application application)
    {
        ToDoDatabase db=ToDoDatabase.getDatabase(application);
        mTodoDao=db.wordDao();
        mAllToDo=mTodoDao.getAllData();

    }

    public LiveData<List<TodoTable>> getmAllWords() {
        return mAllToDo;
    }

    public void setmAllWords(LiveData<List<TodoTable>> mAllWords) {
        this.mAllToDo = mAllWords;
    }
    public void insertTodo(final TodoTable todo)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mTodoDao.insertTodo(todo);
                Log.e("Inserted ","Data is Inserted *************  ");
                return null;
            }
        }.execute();
    }

    public void updateTodo(final TodoTable todo)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mTodoDao.updateTodo(todo);
                Log.e("Inserted ","Data is Inserted *************  ");
                return null;
            }
        }.execute();
    }

    public void deleteTodo(final TodoTable todo)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mTodoDao.deleteTodo(todo);
                Log.e("Inserted ","Data is Inserted *************  ");
                return null;
            }
        }.execute();
    }
}
