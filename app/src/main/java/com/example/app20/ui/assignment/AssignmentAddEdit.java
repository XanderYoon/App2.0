package com.example.app20.ui.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app20.MainActivity;
import com.example.app20.R;
import com.example.app20.ui.classes.ClassModel;

import java.util.Calendar;
import java.util.List;

public class AssignmentAddEdit extends AppCompatActivity {
    Button saveAssignmentButton;
    EditText et_assignment, et_course, et_date;
    List<AssignmentModel> assignmentList;
    AssignmentModel currAssignment = null;
    private boolean toUpdate = false;
    private int month;
    private int day;
    int id;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mDisplayDate;
    private AssignmentModel newAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_assignment);
        assignmentList = AssignmentFragment.getAssignmentList();

        saveAssignmentButton = findViewById(R.id.newAssignmentSaveButton);
        et_assignment = findViewById(R.id.newAssignmentText);
        et_course = findViewById(R.id.newAssignmentCourseText);

        mDisplayDate = (TextView) findViewById(R.id.newAssignmentDateText);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        if (id >= 0) {
            for (AssignmentModel c : assignmentList){
                if (c.getId() == id) {
                    currAssignment = c;
                    toUpdate = true;
                    Log.d("AssignmentAddEdit", "found matching ids " + id);
                }
            }
            et_assignment.setText(currAssignment.getAssignment());
            et_course.setText(currAssignment.getCourse());
        }

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month;
                int day;
                if (toUpdate) {
                    month = currAssignment.getMonth() - 1;
                    day = currAssignment.getDay();
                } else {
                    month = cal.get(Calendar.MONTH);
                    day = cal.get(Calendar.DAY_OF_MONTH);
                }

                DatePickerDialog dialog = new DatePickerDialog(
                        AssignmentAddEdit.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day;
                mDisplayDate.setText(date);
                newAssignment = new AssignmentModel(id, et_assignment.getText().toString(), et_course.getText().toString(), month,  day);
            }
        };
        saveAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toUpdate) { //update
//                    AssignmentModel update = new AssignmentModel(id, et_assignment.getText().toString(), et_course.getText().toString(), et_date.getText().toString());
                    AssignmentFragment.setAssignmentAtIndex(id, newAssignment);
                    AssignmentFragment.setToUpdate(true);
                } else { //add
                    int nextId = AssignmentFragment.getNextId();
//                    AssignmentModel newAssignment = new AssignmentModel(nextId, et_assignment.getText().toString(), et_course.getText().toString(), et_date.getText().toString());
                    newAssignment.setId(nextId);
                    AssignmentFragment.addAssignment(newAssignment);
                    AssignmentFragment.setNextId(++nextId);
                    AssignmentFragment.setToUpdate(false);
                }
                //go back to AssignmentFragment
                Intent intent = new Intent(AssignmentAddEdit.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
