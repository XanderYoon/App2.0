package com.example.app20.ui.exam;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app20.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<com.example.app20.ui.exam.RecyclerViewAdapter.MyViewHolder> {
    List<ExamModel> examList;
    Context context;
    private ExamFragment activity;

    public RecyclerViewAdapter(List<ExamModel> examList, Context context, ExamFragment activity) {
        this.examList = examList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inline_exam,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_exam.setText(examList.get(position).getExam());
        holder.tv_location.setText(examList.get(position).getLocation());
        holder.tv_date.setText(makeDateString(examList.get(position).getDay(), examList.get(position).getMonth()));
        holder.tv_time.setText(makeTime(examList.get(position).getHour(),examList.get(position).getMinute()));
        holder.tv_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", position);
                ExamAddEdit fragment = new ExamAddEdit();
                fragment.setArguments(bundle);
                fragment.show(activity.requireActivity().getSupportFragmentManager(), ExamAddEdit.TAG);
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
                .setMessage("Are you sure you want to delete this exam?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed deletion
                        ExamFragment.deleteExam(position);
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

    @Override
    public int getItemCount() {
        return examList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_exam;
        TextView tv_location;
        TextView tv_date;
        TextView tv_time;
        ImageButton delButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_exam = itemView.findViewById(R.id.examName);
            tv_location = itemView.findViewById(R.id.examLocation);
            tv_date = itemView.findViewById(R.id.examDate);
            tv_time = itemView.findViewById(R.id.examTime);
            delButton = itemView.findViewById(R.id.inlineExamDeleteButton);
        }
    }

    public static String makeTime(int hour, int min)
    {
        return hour + ":" + min;
    }
    public static String makeDateString(int day, int month)
    {
        return getMonthFormat(month) + " " + day;
    }
    public static String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }
}