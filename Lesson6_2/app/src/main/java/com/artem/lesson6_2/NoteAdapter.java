package com.artem.lesson6_2;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    ArrayList<Note> notes;
    DataBaseNotes dataBase = DataBaseNotes.getInstanse();
    Context context;

    public NoteAdapter(Context context, DataBaseNotes dataBaseNotes) {
        this.dataBase = dataBaseNotes;
        this.context = context;
    }

    public NoteAdapter(Context context, ArrayList<Note> notes) {
        this.notes = notes;
        this.context = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.noteName.setText(dataBase.getNotes().get(position).getName());
        holder.noteDate.setText(dataBase.getNotes().get(position).getDate());
        holder.noteImage.setImageDrawable(dataBase.getNotes().get(position).getImage().getDrawable());
        //holder.doneState.setChecked(dataBase.getNotes().get(position).getDoneState());
        if (holder.doneState.isChecked()) {
            dataBase.getNotes().get(position).setDoneState(true);
        }
        holder.detailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(view, position);
            }
        });

    }

    private void showFragment(View view, int position) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        Fragment detailFragment = new DetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        detailFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.list_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public int getItemCount() {
        DataBaseNotes dataBaseNotes = DataBaseNotes.getInstanse();
        return dataBaseNotes.getNotes().size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteName, noteDate;
        ImageView noteImage;
        CardView detailsLayout;
        CheckBox doneState;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteName = itemView.findViewById(R.id.noteName);
            noteDate = itemView.findViewById(R.id.noteDate);
            noteImage = itemView.findViewById(R.id.imageView_item);
            detailsLayout = itemView.findViewById(R.id.details_layout);
            doneState = itemView.findViewById(R.id.note_checkbox);

        }


    }


}
