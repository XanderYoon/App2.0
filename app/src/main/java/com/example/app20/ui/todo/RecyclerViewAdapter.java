package com.example.app20.ui.todo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app20.R;
import com.example.app20.ui.todo.TodoAddEdit;
import com.example.app20.ui.todo.TodoAddEdit;
import com.example.app20.ui.todo.TodoFragment;
import com.example.app20.ui.todo.TodoModel;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<com.example.app20.ui.todo.RecyclerViewAdapter.MyViewHolder> {
    List<TodoModel> toDoList;
    Context context;
    private TodoFragment activity;

    public RecyclerViewAdapter(List<TodoModel> toDoList, Context context, TodoFragment activity) {
        this.toDoList = toDoList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inline_todo,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_toDo.setText(toDoList.get(position).getTask());
        holder.tv_toDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", position);
                TodoAddEdit fragment = new TodoAddEdit();
                fragment.setArguments(bundle);
                fragment.show(activity.requireActivity().getSupportFragmentManager(), TodoAddEdit.TAG);
                notifyItemChanged(position);            }
        });
        holder.delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete item
                showDeleteConfirmationDialog(position);
            }
        });
    }
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete this toDo?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed deletion
                        TodoFragment.deleteTodo(position);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User canceled deletion
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_toDo;
        ImageButton delButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_toDo = itemView.findViewById(R.id.todoTextView);
            delButton = itemView.findViewById(R.id.inlineTodoDeleteButton);
        }
    }

}