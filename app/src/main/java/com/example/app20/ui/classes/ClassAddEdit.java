package com.example.app20.ui.classes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app20.MainActivity;
import com.example.app20.R;

import java.util.List;


public class ClassAddEdit extends AppCompatActivity {

    Button saveClassButton;
    EditText et_courseNum, et_prof, et_time;
    List<ClassModel> classList;
    ClassModel currClass = null;
    private boolean toUpdate = false;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_class);
        classList = ClassesFragment.getClassList();

        saveClassButton = findViewById(R.id.newClassSaveButton);
        et_courseNum = findViewById(R.id.newCourseNumText);
        et_prof = findViewById(R.id.newProfessorText);
        et_time = findViewById(R.id.newTimeText);

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
            et_time.setText(currClass.getTime());
        }
        saveClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toUpdate) { //update
                    ClassModel update = new ClassModel(id, et_courseNum.getText().toString(), et_prof.getText().toString(), et_time.getText().toString());
                    ClassesFragment.setClassAtIndex(id, update);
                    ClassesFragment.setToUpdate(true);
                } else { //add
                    int nextId = ClassesFragment.getNextId();
                    ClassModel newClass = new ClassModel(nextId, et_courseNum.getText().toString(), et_prof.getText().toString(), et_time.getText().toString());
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
