package com.rahulchaube.tododemo.data.entitiy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class TodoTable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    long id;
    String title;
    String content;
    int status;
    long createdTime;

    public TodoTable(long id, String title, String content, int status, long createdTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.createdTime = createdTime;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
