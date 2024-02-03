package com.example.app20.ui.exam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app20.MainActivity;
import com.example.app20.R;

import java.util.List;

public class ExamAddEdit extends AppCompatActivity {
    Button saveExamButton;
    EditText et_exam, et_course, et_date;
    List<ExamModel> examList;
    ExamModel currExam = null;
    private boolean toUpdate = false;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_exam);
        examList = ExamFragment.getExamList();

        saveExamButton = findViewById(R.id.newExamSaveButton);
        et_exam = findViewById(R.id.newExamText);
        et_course = findViewById(R.id.newExamCourseText);
        et_date = findViewById(R.id.newExamDueDate);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        if (id >= 0) {
            for (ExamModel c : examList){
                if (c.getId() == id) {
                    currExam = c;
                    toUpdate = true;
                    Log.d("ExamAddEdit", "found matching ids " + id);
                }
            }
            et_exam.setText(currExam.getExam());
            et_course.setText(currExam.getCourse());
            et_date.setText(currExam.getDate());
        }
        saveExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toUpdate) { //update
                    ExamModel update = new ExamModel(id, et_exam.getText().toString(), et_course.getText().toString(), et_date.getText().toString());
                    ExamFragment.setExamAtIndex(id, update);
                    ExamFragment.setToUpdate(true);
                } else { //add
                    int nextId = ExamFragment.getNextId();
                    ExamModel newExam = new ExamModel(nextId, et_exam.getText().toString(), et_course.getText().toString(), et_date.getText().toString());
                    ExamFragment.addExam(newExam);
                    ExamFragment.setNextId(++nextId);
                    ExamFragment.setToUpdate(false);
                }
                //go back to ExamFragment
                Intent intent = new Intent(ExamAddEdit.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
