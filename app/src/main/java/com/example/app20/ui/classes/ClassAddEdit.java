package com.example.app20.ui.classes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.example.app20.MainActivity;
import com.example.app20.R;
import com.example.app20.databinding.FragmentClassesBinding;

import java.io.Serializable;
import java.util.List;


public class ClassAddEdit extends AppCompatActivity {

    Button saveClassButton;
    EditText et_courseNum, et_prof, et_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_class);

        saveClassButton = findViewById(R.id.newClassSaveButton);
        et_courseNum = findViewById(R.id.newCourseNumText);
        et_prof = findViewById(R.id.newProfessorText);
        et_time = findViewById(R.id.newTimeText);

        saveClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create class object

                int nextId = ClassesFragment.getNextId();
                ClassModel newClass = new ClassModel(nextId, et_courseNum.getText().toString(), et_prof.getText().toString(), et_time.getText().toString());

                ClassesFragment.addClass(newClass);
                ClassesFragment.setNextId(++nextId);

                //go back to ClassesFragment
                Intent intent = new Intent(ClassAddEdit.this, MainActivity.class);
                startActivity(intent);
            }
        });

        }

    }
