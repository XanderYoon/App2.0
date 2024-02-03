package com.example.app20.ui.assignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app20.MainActivity;
import com.example.app20.R;
import com.example.app20.ui.classes.ClassModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AssignmentAddEdit extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
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

    public static AssignmentAddEdit newInstance(){
        return new AssignmentAddEdit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_assignment, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assignmentList = AssignmentFragment.getAssignmentList();

        saveAssignmentButton = requireView().findViewById(R.id.newAssignmentSaveButton);
        et_assignment = requireView().findViewById(R.id.newAssignmentText);
        et_course = requireView().findViewById(R.id.newAssignmentCourseText);
        mDisplayDate = (TextView) requireView().findViewById(R.id.newAssignmentDateText);

        Bundle args = getArguments();
        if (args != null) {
            id = args.getInt("id", -1);
        }
        Log.d("THE ID PASS THROUGH BUNDLE", String.valueOf(id));
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
                        requireContext(),
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
                dismiss();
            }
        });

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);Bundle bundle = new Bundle();
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        bundle.putString("redirectLoc", "assignment");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
