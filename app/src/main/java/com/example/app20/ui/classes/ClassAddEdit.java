package com.example.app20.ui.classes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.app20.MainActivity;
import com.example.app20.R;

import java.util.Calendar;
import java.util.List;


public class ClassAddEdit extends AppCompatActivity {

    Button saveClassButton;
    EditText et_courseNum, et_prof, et_time;
    TextView timeButton;
    List<ClassModel> classList;
    ClassModel currClass = null;
    private boolean toUpdate = false;
    private int newHour;
    private int newMinute;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_class);
        classList = ClassesFragment.getClassList();

        saveClassButton = findViewById(R.id.newClassSaveButton);
        et_courseNum = findViewById(R.id.newCourseNumText);
        et_prof = findViewById(R.id.newProfessorText);
        timeButton = findViewById(R.id.newTimeText);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        if (id >= 0) {
            for (ClassModel c : classList){
                if (c.getId() == id) {
                    currClass = c;
                    toUpdate = true;
                    Log.d("ClassAddEdit", "found matching ids " + id);
                }
            }
            et_courseNum.setText(currClass.getCourseNum());
            et_prof.setText(currClass.getProf());
        }

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ClassAddEdit.this,
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
                        android.text.format.DateFormat.is24HourFormat(ClassAddEdit.this)
                );
                timePickerDialog.show();
            }
        });
        saveClassButton.setOnClickListener(new View.OnClickListener() {
            //int id, int hour, int minute, String courseNum, String prof
            @Override
            public void onClick(View v) {
                if (toUpdate) { //update
                    ClassModel update = new ClassModel(id, newHour, newMinute, et_courseNum.getText().toString(), et_prof.getText().toString());
                    ClassesFragment.setClassAtIndex(id, update);
                    ClassesFragment.setToUpdate(true);
                } else { //add
                    int nextId = ClassesFragment.getNextId();
                    ClassModel newClass = new ClassModel(nextId, newHour, newMinute, et_courseNum.getText().toString(), et_prof.getText().toString());
                    ClassesFragment.addClass(newClass);
                    ClassesFragment.setNextId(++nextId);
                    ClassesFragment.setToUpdate(false);
                }
                //go back to ClassesFragment
                Intent intent = new Intent(ClassAddEdit.this, MainActivity.class);
                startActivity(intent);
            }
        });

        }

    }
