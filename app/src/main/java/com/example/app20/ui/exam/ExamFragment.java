package com.example.app20.ui.exam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.app20.MainActivity;
import com.example.app20.R;
import com.example.app20.databinding.FragmentExamBinding;
import com.example.app20.ui.classes.ClassModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ExamFragment extends Fragment {
    private static List<ExamModel> examList = new ArrayList<>();
    private static int nextId = 0;
    private static int updateId;
    private RecyclerView recyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fab;
    private FragmentExamBinding binding;
    private static ExamFragment instance;
    private static boolean toUpdate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentExamBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        super.onCreate(savedInstanceState);
        instance = this;

        fab = root.findViewById(R.id.examFab);

        recyclerView = root.findViewById(R.id.examRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewAdapter(examList, requireContext(), this);
        recyclerView.setAdapter(mAdapter);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", -1);
                ExamAddEdit fragment = new ExamAddEdit();
                fragment.setArguments(bundle);
                fragment.show(requireActivity().getSupportFragmentManager(), ExamAddEdit.TAG);
            }
        });
        if (toUpdate) {
            mAdapter.notifyItemChanged(updateId);
        } else {
            mAdapter.notifyItemInserted(examList.size() - 1);
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
        ExamFragment.toUpdate = toUpdate;
    }

    public static List<ExamModel> getExamList() {
        return examList;
    }
    public static void addExam(ExamModel newExam) {
        examList.add(newExam);
        toUpdate = false;

    }
    public static void setExamAtIndex(int id, ExamModel updateExam) {
        examList.set(id, updateExam);
        toUpdate = true;
        updateId = id;
    }

    public static void deleteExam(ExamModel delExam) {
        examList.remove(delExam);
        --nextId;
    }
    public static void deleteExam(int position) {
        examList.remove(position);
        --nextId;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        ExamFragment.nextId = nextId;
    }

    public static ExamFragment getInstance() {
        return instance;
    }


}