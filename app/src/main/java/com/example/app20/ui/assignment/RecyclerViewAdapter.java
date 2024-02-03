package com.example.app20.ui.assignment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<com.example.app20.ui.assignment.RecyclerViewAdapter.MyViewHolder> {
    List<AssignmentModel> assignmentList;
    Context context;

    public RecyclerViewAdapter(List<AssignmentModel> assignmentList, Context context) {
        this.assignmentList = assignmentList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inline_assignment,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_assignment.setText(assignmentList.get(position).getAssignment());
        holder.tv_course.setText(assignmentList.get(position).getCourse());
        holder.tv_date.setText(makeDateString(assignmentList.get(position).getDay(), assignmentList.get(position).getMonth()));
        holder.tv_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edit item
                Intent intent = new Intent(context, AssignmentAddEdit.class);
                intent.putExtra("id", assignmentList.get(position).getId());
                context.startActivity(intent);
            }
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
                .setMessage("Are you sure you want to delete this assignment?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User confirmed deletion
                        AssignmentFragment.deleteAssignment(position);
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
        return assignmentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_assignment;
        TextView tv_course;
        TextView tv_date;
        ImageButton delButton;
        ConstraintLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_assignment = itemView.findViewById(R.id.assignmentName);
            tv_course = itemView.findViewById(R.id.assignmentCourse);
            tv_date = itemView.findViewById(R.id.assignmentDate);
//            parentLayout = itemView.findViewById(R.id.inLineAssignmentLayout);
            delButton = itemView.findViewById(R.id.inlineAssignmentDeleteButton);
        }
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