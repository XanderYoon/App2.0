package com.example.app20.ui.classes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.app20.R;
import java.util.List;


public class ClassAddEdit extends AppCompatActivity {

    Button saveClassButton;
    List<ClassModel> classList;
    EditText et_courseNum, et_prof, et_time;
//    MyApplication myApplication = (MyApplication) this.getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_class);

//        classList = myApplication.getClassList();

        saveClassButton = findViewById(R.id.newClassSaveButton);
        et_courseNum = findViewById(R.id.newCourseNumText);
        et_prof = findViewById(R.id.newProfessorText);
        et_time = findViewById(R.id.newTimeText);

        saveClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create class object
//                int nextId = MyApplication.getNextId();
//                ClassModel newClass = new ClassModel(nextId, et_courseNum.getText().toString(), et_prof.getText().toString(), et_time.getText().toString());
//
//                classList.add(newClass);
//                myApplication.setNextId(++nextId);

                //go back to main activity
                Intent intent = new Intent(ClassAddEdit.this, ClassesFragment.class);
                startActivity(intent);
            }
        });
    }
}