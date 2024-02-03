package com.example.app20.ui.exam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.app20.MainActivity;
import com.example.app20.R;
import com.example.app20.ui.classes.ClassesFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ExamAddEdit extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    Button saveExamButton;
    EditText et_examName, et_examLocation;
    List<ExamModel> examList;
    ExamModel currExam = null;
    private boolean toUpdate = false;
    private int newMonth;
    private int newDay;
    private int newHour;
    private int newMinute;
    int id;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mDisplayDate, timeButton;

    public static ExamAddEdit newInstance(){
        return new ExamAddEdit();
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

        View view = inflater.inflate(R.layout.new_exam, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        examList = ExamFragment.getExamList();

        saveExamButton = requireView().findViewById(R.id.newExamSaveButton);
        et_examName = requireView().findViewById(R.id.newExamText);
        et_examLocation = requireView().findViewById(R.id.newExamLocation);
        mDisplayDate = (TextView) requireView().findViewById(R.id.newExamDateText);
        timeButton = (TextView) requireView().findViewById(R.id.newExamTimeText);

        Bundle args = getArguments();
        if (args != null) {
            id = args.getInt("id", -1);
        }
        if (id >= 0) {
            for (ExamModel c : examList){
                if (c.getId() == id) {
                    currExam = c;
                    toUpdate = true;
                }
            }
            et_examName.setText(currExam.getExam());
            et_examLocation.setText(currExam.getLocation());
        }

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        requireContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                newHour = hourOfDay;
                                newMinute = minute;
                                String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                                timeButton.setText(selectedTime);
                            }
                        },
                        hour,
                        minute,
                        android.text.format.DateFormat.is24HourFormat(requireContext())
                );
                timePickerDialog.show();
            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month;
                int day;
                if (toUpdate) {
                    month = currExam.getMonth() - 1;
                    day = currExam.getDay();
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
                newMonth = month + 1;
                newDay = day;
                String date = month + "/" + day;
                mDisplayDate.setText(date);
            }
        };

        saveExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toUpdate) { //update
                    ExamModel newExam = new ExamModel(id, newDay, newMonth, newHour, newMinute, et_examName.getText().toString(), et_examLocation.getText().toString());
                    ExamFragment.setExamAtIndex(id, newExam);
                    ExamFragment.setToUpdate(true);
                } else { //add
                    int nextId = ExamFragment.getNextId();
                    ExamModel newExam = new ExamModel(nextId, newDay, newMonth, newHour, newMinute, et_examName.getText().toString(), et_examLocation.getText().toString());
                    ExamFragment.addExam(newExam);
                    ExamFragment.setNextId(++nextId);
                    ExamFragment.setToUpdate(false);
                }
                dismiss();
            }
        });

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Bundle bundle = new Bundle();
        Intent intent = new Intent(requireActivity(), MainActivity.class);
        bundle.putString("redirectLoc", "exam");
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
