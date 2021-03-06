package com.artem.lesson6_2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class EditFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private Button btnSave, btnBack;
    private int position;
    private TextView editDate,textDate;
    private TextInputEditText editNoteName,  editDescription;
    DataBaseNotes database= DataBaseNotes.getInstanse();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

    }

    private void initViews(@NonNull View view) {
        editNoteName = view.findViewById(R.id.edit_note_name);
        editDescription = view.findViewById(R.id.edit_note_description);
        editDate = view.findViewById(R.id.edit_date);
        textDate = view.findViewById(R.id.text_date);
        btnSave = view.findViewById(R.id.btn_save);
        btnBack= view.findViewById(R.id.btn_back);
        editNoteName.setText(database.getNotes().get(position).getName());
        editDescription.setText(database.getNotes().get(position).getDescription());
        textDate.setText(database.getNotes().get(position).getDate());

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePicker= new DatePickerFragment();
                datePicker.setTargetFragment(EditFragment.this, 0);
                datePicker.show(getParentFragmentManager(),"date picker");
            }

        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName=editNoteName.getText().toString();
                String newDescription=editDescription.getText().toString();
                DataBaseNotes.notes.get(position).setName(newName);
                DataBaseNotes.notes.get(position).setDescription(newDescription);
                DataBaseNotes.notes.get(position).setDate( textDate.getText().toString());
                MainActivity.needToUpdateRecView=1;
                returnBack();

            }


        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnBack();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar= Calendar.getInstance();
        calendar.set (Calendar.YEAR,year);
        calendar.set (Calendar.MONTH,month);
        calendar.set (Calendar.DAY_OF_MONTH,day);
        String currentDateString = DateFormat.getDateInstance().format(calendar.getTime());
        textDate.setText(currentDateString);

    }
    private void returnBack() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}