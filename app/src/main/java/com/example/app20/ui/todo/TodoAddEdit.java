package com.example.app20.ui.todo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app20.MainActivity;
import com.example.app20.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;
import java.util.Objects;

public class TodoAddEdit extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    Button saveTodoButton;
    EditText et_todo;
    List<TodoModel> todoList;
    TodoModel currTodo = null;
    private boolean toUpdate;
    int id;

    public static TodoAddEdit newInstance(){
        return new TodoAddEdit();
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

        View view = inflater.inflate(R.layout.new_todo, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todoList = TodoFragment.getTodoList();

        saveTodoButton = requireView().findViewById(R.id.new_todo_save);
        et_todo = requireView().findViewById(R.id.newTask);

        Bundle args = getArguments();
        if (args != null) {
            id = args.getInt("id", -1);
        }
        if (id >= 0) {
            for (TodoModel c : todoList){
                if (c.getId() == id) {
                    currTodo = c;
                    toUpdate = true;
                }
            }
            et_todo.setText(currTodo.getTask());
        }

        saveTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toUpdate) { //update
                    TodoModel newTodo = new TodoModel(id, et_todo.getText().toString());
                    TodoFragment.setTodoAtIndex(id, newTodo);
                    TodoFragment.setToUpdate(true);
                } else { //add
                    int nextId = TodoFragment.getNextId();
                    TodoModel newTodo = new TodoModel(nextId, et_todo.getText().toString());
                    TodoFragment.addTodo(newTodo);
                    TodoFragment.setNextId(++nextId);
                    TodoFragment.setToUpdate(false);
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
        bundle.putString("redirectLoc", "todo");
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
