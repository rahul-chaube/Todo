package com.rahulchaube.tododemo.ui.todolist;

import android.content.Context;
import android.util.Log;
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

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    Context context;
    TodoEvent listner;

    public TodoListAdapter(Context context, TodoEvent listner) {
        this.context = context;
        this.listner = listner;
    }
    void resetSearch()
    {
        Log.e("Reset is Called ", " **********  " );
        filterList.clear();
        filterList.addAll(data);
        notifyDataSetChanged();
    }

    void setData(List<TodoTable> data)
    {
        this.data=data;
        filterList.addAll(this.data);
        notifyDataSetChanged();
    }
    void filterString(String string)
    {
        Log.e("Test Data "," "+data.size());
        filterList.clear();
        for (TodoTable todoTable: data
             ) {

            Log.e("Test is "," "+todoTable.getContent());
            if (todoTable.getContent().toLowerCase().contains(string.toLowerCase()) || todoTable.getTitle().toLowerCase().contains(string.toLowerCase()))
            {
                filterList.add(todoTable);
            }
        }
        Log.e("Search Called ",string+" "+filterList.size()+"  "+data.size());
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
        Date date = new Date(todoTable.getCreatedTime());
        Format format = new SimpleDateFormat("dd-MMM-yy");
        holder.textViewDate.setText(format.format(date));
        if (todoTable.getStatus()==2)
        {
            holder.textViewStatus.setText("Done");
            holder.cardViewMain.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }
        else
        {
            holder.textViewStatus.setText("Pending");
            holder.cardViewMain.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewMain;
        TextView textViewTitle,textViewContent,textViewStatus,textViewDate;
        ImageView imageViewDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate=itemView.findViewById(R.id.date);
            textViewStatus=itemView.findViewById(R.id.status);
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
