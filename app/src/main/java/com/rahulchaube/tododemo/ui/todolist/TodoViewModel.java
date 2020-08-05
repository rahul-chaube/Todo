package com.rahulchaube.tododemo.ui.todolist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rahulchaube.tododemo.data.entitiy.TodoTable;
import com.rahulchaube.tododemo.data.repository.TodoRepository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private TodoRepository mRepository;
    private LiveData<List<TodoTable>> mAllTodo;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        mRepository=new TodoRepository(application);
        mAllTodo =mRepository.getmAllWords();
    }
    public LiveData<List<TodoTable>> getmAllTodo() {
        return mAllTodo;
    }

    public void setmAllTodo(LiveData<List<TodoTable>> mAllTodo) {
        this.mAllTodo = mAllTodo;
    }

    public void insert(TodoTable word)
    {
        mRepository.insertTodo(word);
    }

    public void update(TodoTable word)
    {
        mRepository.updateTodo(word);
    }
    public void delete(TodoTable word)
    {
        mRepository.deleteTodo(word);
    }
}
