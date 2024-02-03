package com.example.app20.ui.classes;

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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    List<ClassModel> classList;
    Context context;

    public RecyclerViewAdapter(List<ClassModel> classList, Context context) {
        this.classList = classList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inline_class,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_courseNum.setText(classList.get(position).getCourseNum());
        holder.tv_prof.setText(classList.get(position).getProf());
        holder.tv_time.setText(classList.get(position).getTime());
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit item
                Intent intent = new Intent(context, ClassAddEdit.class);
                intent.putExtra("id", classList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_courseNum;
        TextView tv_prof;
        TextView tv_time;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_courseNum = itemView.findViewById(R.id.courseNumAns);
            tv_prof = itemView.findViewById(R.id.profAns);
            tv_time = itemView.findViewById(R.id.timeAns);
            parentLayout = itemView.findViewById(R.id.inLineClassLayout);
        }
    }
}
