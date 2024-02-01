package com.example.app20.ui.classes;

import com.example.app20.databinding.FragmentClassesBinding;

import android.content.Intent;
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

import com.example.app20.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassesFragment extends Fragment {
    private static List<ClassModel> classList = new ArrayList<>();
    private static int nextId = 5;
    private RecyclerView recyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fab;
    private FragmentClassesBinding binding;
    private static ClassesFragment instance;

    private boolean flag = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentClassesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        super.onCreate(savedInstanceState);
        instance = this;

        fab = root.findViewById(R.id.addClassButton);


        recyclerView = root.findViewById(R.id.classRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewAdapter(classList, requireContext());
        recyclerView.setAdapter(mAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), ClassAddEdit.class);
                startActivity(intent);


                Intent classRecieved = getActivity().getIntent();
                int id = classRecieved.getIntExtra("id",0);
                String courseNum = classRecieved.getStringExtra("courseNum");
                String prof = classRecieved.getStringExtra("prof");
                String time = classRecieved.getStringExtra("time");
                flag = classRecieved.getBooleanExtra("flag", true);

                if (flag){
                    ClassModel addtheclass = new ClassModel(id, courseNum,prof,time);
                    addClass(addtheclass);

                }
            }
        });


        mAdapter.notifyItemInserted(classList.size() - 1);

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void updateRecyclerView(){
        mAdapter = new RecyclerViewAdapter(classList, requireContext());
    }

    public static List<ClassModel> getClassList() {
        return classList;
    }
    public static void addClass(ClassModel newClass) {
        classList.add(newClass);
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        ClassesFragment.nextId = nextId;
    }

    public static ClassesFragment getInstance() {
        return instance;
    }
}