package com.rahulchaube.tododemo.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rahulchaube.tododemo.data.entitiy.TodoTable;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insertTodo(TodoTable todoTable);

    @Update
    void updateTodo(TodoTable todoTable);

    @Delete
    void deleteTodo(TodoTable todoTable);

    @Query("select * from todotable order by id desc")
    LiveData<List<TodoTable>> getAllData();

}
