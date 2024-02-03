package com.example.app20.ui.todo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app20.MainActivity;
import com.example.app20.R;
import com.example.app20.databinding.FragmentToDoBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment {
    private static List<TodoModel> todoList = new ArrayList<>();
    private static int nextId = 0;
    private static int updateId;
    private RecyclerView recyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fab;
    private FragmentToDoBinding binding;
    private static TodoFragment instance;
    private static boolean toUpdate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentToDoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        super.onCreate(savedInstanceState);
        instance = this;

        fab = root.findViewById(R.id.todoFab);

        recyclerView = root.findViewById(R.id.todoRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewAdapter(todoList, requireContext(), this);
        recyclerView.setAdapter(mAdapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", -1);
                TodoAddEdit fragment = new TodoAddEdit();
                fragment.setArguments(bundle);
                fragment.show(requireActivity().getSupportFragmentManager(), TodoAddEdit.TAG);
            }
        });
        if (toUpdate) {
            mAdapter.notifyItemChanged(updateId);
        } else {
            mAdapter.notifyItemInserted(todoList.size() - 1);
        }
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public boolean isToUpdate() {
        return toUpdate;
    }

    public static void setToUpdate(boolean toUpdate) {
        TodoFragment.toUpdate = toUpdate;
    }

    public static List<TodoModel> getTodoList() {
        return todoList;
    }
    public static void addTodo(TodoModel newTodo) {
        todoList.add(newTodo);
        toUpdate = false;

    }
    public static void setTodoAtIndex(int id, TodoModel updateTodo) {
        todoList.set(id, updateTodo);
        toUpdate = true;
        updateId = id;
    }

    public static void deleteTodo(TodoModel delTodo) {
        todoList.remove(delTodo);
        --nextId;
    }
    public static void deleteTodo(int position) {
        todoList.remove(position);
        --nextId;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        TodoFragment.nextId = nextId;
    }

    public static TodoFragment getInstance() {
        return instance;
    }


}