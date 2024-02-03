package com.example.app20.ui.assignment;

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
import com.example.app20.databinding.FragmentAssignmentBinding;
import com.example.app20.ui.classes.ClassModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        ToggleButton sortToggle = root.findViewById(R.id.sortToggle);

        recyclerView = root.findViewById(R.id.assignmentsRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewAdapter(assignmentList, requireContext(), this);
        recyclerView.setAdapter(mAdapter);

        sortToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Collections.sort(assignmentList, AssignmentModel.dateSort);
                } else {
                    Collections.sort(assignmentList, AssignmentModel.courseSort);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", -1);
                AssignmentAddEdit fragment = new AssignmentAddEdit();
                fragment.setArguments(bundle);
                fragment.show(requireActivity().getSupportFragmentManager(), AssignmentAddEdit.TAG);
            }
        });
        if (toUpdate) {
            Log.d("TO UPDATE", "ITS BEING UPDATED!!!!");
            mAdapter.notifyItemChanged(updateId);
        } else {
            Log.d("NOT UPDATE", "ITS BEING ADDDED!!!!");
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