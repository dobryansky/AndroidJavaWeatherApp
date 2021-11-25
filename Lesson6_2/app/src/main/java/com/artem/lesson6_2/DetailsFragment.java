package com.artem.lesson6_2;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class DetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int position = 0;
        if (getArguments() != null) {
            position = this.getArguments().getInt("position", 0);
        }

        initViews(view, position);
    }

    private void initViews(@NonNull View view, int position) {

        FragmentManager fragmentManager = getParentFragmentManager();
        TextView noteDate = view.findViewById(R.id.noteDetailsDate);
        TextView noteName = view.findViewById(R.id.noteDetailsName);
        TextView noteDescription = view.findViewById(R.id.noteDetailsDescription);
        ImageView img = view.findViewById(R.id.imgDetails);
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yy MMMM yyyy, H:m:ss");
        noteDate.setText(formatForDateNow.format(DataBaseNotes.notes.get(position).getDate()));
        noteName.setText(DataBaseNotes.notes.get(position).getName());
        noteDescription.setText(DataBaseNotes.notes.get(position).getDescription());
        img.setImageResource(ListFragment.images.get(position));
        MaterialButton btnEdit = view.findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditFragment editFragment = new EditFragment();
                Bundle setPosition = new Bundle();
                setPosition.putInt("position",position);
                editFragment.setArguments(setPosition);
                fragmentManager.beginTransaction()
                        .replace(R.id.list_container, editFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}