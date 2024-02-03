package com.example.app20.ui.assignment;

import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Button;

import com.example.app20.R;
import com.example.app20.databinding.FragmentAssignmentBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AssignmentFragment extends Fragment {
    private static List<AssignmentModel> assignmentList = new ArrayList<>();
    private static int nextId = 0;
    private static int updateId;
    private RecyclerView recyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fab;
    private FragmentAssignmentBinding binding;
    private static AssignmentFragment instance;
    private static boolean toUpdate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentAssignmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        super.onCreate(savedInstanceState);
        instance = this;

        fab = root.findViewById(R.id.assignmentfab);


        recyclerView = root.findViewById(R.id.assignmentsRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewAdapter(assignmentList, requireContext());
        recyclerView.setAdapter(mAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AssignmentAddEdit.class);
                startActivity(intent);

            }
        });
        if (toUpdate) {
            mAdapter.notifyItemChanged(updateId);
        } else {
            mAdapter.notifyItemInserted(assignmentList.size() - 1);
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
        AssignmentFragment.toUpdate = toUpdate;
    }

    public static List<AssignmentModel> getAssignmentList() {
        return assignmentList;
    }
    public static void addAssignment(AssignmentModel newAssignment) {
        assignmentList.add(newAssignment);
        toUpdate = false;

    }
    public static void setAssignmentAtIndex(int id, AssignmentModel updateAssignment) {
        assignmentList.set(id, updateAssignment);
        toUpdate = true;
        updateId = id;
    }

    public static void deleteAssignment(AssignmentModel delAssignment) {
        assignmentList.remove(delAssignment);
        --nextId;
    }
    public static void deleteAssignment(int position) {
        assignmentList.remove(position);
        --nextId;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        AssignmentFragment.nextId = nextId;
    }

    public static AssignmentFragment getInstance() {
        return instance;
    }


}