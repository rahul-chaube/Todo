package com.rahulchaube.tododemo.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rahulchaube.tododemo.data.dao.TodoDao;
import com.rahulchaube.tododemo.data.entitiy.TodoTable;

@Database(entities = {TodoTable.class},version = 1,exportSchema = false)
public abstract class ToDoDatabase extends RoomDatabase {
    private static ToDoDatabase INSTANCE;
    public abstract TodoDao wordDao();

    public static ToDoDatabase getDatabase(final Context context)
    {
        if (INSTANCE==null)
        {
            synchronized (ToDoDatabase.class)
            {
                if (INSTANCE==null)
                {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),ToDoDatabase.class,"ToDo_Database")
                            .fallbackToDestructiveMigration()

                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
