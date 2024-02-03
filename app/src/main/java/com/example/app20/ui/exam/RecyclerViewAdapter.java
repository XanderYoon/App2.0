package com.example.app20.ui.exam;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app20.R;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<com.example.app20.ui.exam.RecyclerViewAdapter.MyViewHolder> {
    List<ExamModel> examList;
    Context context;

    public RecyclerViewAdapter(List<ExamModel> examList, Context context) {
        this.examList = examList;
        this.context = context;
    }

    @NonNull
    @Override
    public com.example.app20.ui.exam.RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inline_exam,parent, false);
        com.example.app20.ui.exam.RecyclerViewAdapter.MyViewHolder holder = new com.example.app20.ui.exam.RecyclerViewAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.app20.ui.exam.RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tv_exam.setText(examList.get(position).getExam());
        holder.tv_course.setText(examList.get(position).getCourse());
        holder.tv_date.setText(examList.get(position).getDate());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit item
                Intent intent = new Intent(context, ExamAddEdit.class);
                intent.putExtra("id", examList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_exam;
        TextView tv_course;
        TextView tv_date;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_exam = itemView.findViewById(R.id.examName);
            tv_course = itemView.findViewById(R.id.examCourse);
            tv_date = itemView.findViewById(R.id.examDate);
            parentLayout = itemView.findViewById(R.id.inLineExamLayout);
        }
    }
}
