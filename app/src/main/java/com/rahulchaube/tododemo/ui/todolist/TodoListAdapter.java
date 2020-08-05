package com.rahulchaube.tododemo.ui.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rahulchaube.tododemo.R;
import com.rahulchaube.tododemo.data.entitiy.TodoTable;

import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    Context context;
    TodoEvent listner;

    public TodoListAdapter(Context context, TodoEvent listner) {
        this.context = context;
        this.listner = listner;
    }

    void setData(List<TodoTable> data)
    {
        this.data=data;
        filterList=this.data;
        notifyDataSetChanged();
    }
    void filterString(String string)
    {
        filterList.clear();
        for (TodoTable todoTable: data
             ) {
            if (todoTable.getContent().toLowerCase().contains(string.toLowerCase()) || todoTable.getTitle().toLowerCase().contains(string.toLowerCase()))
            {
                filterList.add(todoTable);
            }
        }
        notifyDataSetChanged();
    }

    List<TodoTable> filterList=new ArrayList<>();
    List<TodoTable> data=new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final TodoTable todoTable=filterList.get(position);
        holder.textViewContent.setText(todoTable.getContent());
        holder.textViewTitle.setText(todoTable.getTitle());
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onDeleteEvent(todoTable);
            }
        });
        holder.cardViewMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.updateEvent(todoTable);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewMain;
        TextView textViewTitle,textViewContent;
        ImageView imageViewDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewMain=itemView.findViewById(R.id.main);
            textViewTitle=itemView.findViewById(R.id.tv_item_title);
            textViewContent=itemView.findViewById(R.id.tv_item_content);
            imageViewDelete=itemView.findViewById(R.id.iv_item_delete);
        }
    }
    interface TodoEvent
    {
        void onDeleteEvent(TodoTable todoTable);
        void  updateEvent(TodoTable todoTable);
    }
}
