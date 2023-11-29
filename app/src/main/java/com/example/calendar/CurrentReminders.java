package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class CurrentReminders extends AppCompatActivity {


    private static final String PREFS_NAME = "NotePrefs";
    private static final String KEY_NOTE_COUNT = "NoteCount";
    private LinearLayout notesContainer;
    private List<Note> noteList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentreminders);

        notesContainer = findViewById(R.id.notesContainer);
//        Button saveButton = findViewById(R.id.saveButton);

        noteList = new ArrayList<>();

//        saveButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                saveNote();
//            }
//        });

        loadNotesFromPreferences();
        displayNotes();
    }

    private void displayNotes() {

        for (Note note : noteList) {

            createNoteView(note);
        }
    }

    private void loadNotesFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int noteCount = sharedPreferences.getInt(KEY_NOTE_COUNT, 0);

        for (int i = 0; i < noteCount; i++) {

            String title = sharedPreferences.getString("note_title_" + i, "");
            String content = sharedPreferences.getString("note_content_" + i, "");
            String date = sharedPreferences.getString("note_date_" + i, "");
            String time = sharedPreferences.getString("note_time_" + i, "");


            Note note = new Note();
            note.setTitle(title);
            note.setContent(content);
            note.setDate(date);
            note.setTime(time);

            noteList.add(note);

        }
    }

//    private void saveNote() {
//        EditText titleEditText = findViewById(R.id.titleEditText);
//        EditText contentEditText = findViewById(R.id.contentEditText);
//
//        String title = titleEditText.getText().toString();
//        String content = contentEditText.getText().toString();
//
//        if(!content.isEmpty() && !title.isEmpty()) {
//
//            Note note = new Note();
//
//            note.setTitle(title);
//            note.setContent(content);
//
//            noteList.add(note);
//            saveNoteToPreferences();
//
//            createNoteView(note);
//            clearInputFields();
//
//            startActivity(new Intent(CurrentNotes.this, MainActivity.class));
//
//        }
//    }

    private void clearInputFields() {

        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText contentEditText = findViewById(R.id.contentEditText);

        titleEditText.getText().clear();
        contentEditText.getText().clear();

    }

    private void createNoteView(final Note note) {

        View noteView = getLayoutInflater().inflate(R.layout.note_item, null);
        TextView titleTextView = noteView.findViewById(R.id.titleTextView);
        TextView contentTextView = noteView.findViewById(R.id.contentTextView);
        TextView getDate = noteView.findViewById((R.id.displayTheDay));
        TextView getTime = noteView.findViewById(R.id.reminderTime);

        getTime.setText(note.getTime());
        titleTextView.setText(note.getTitle());
        contentTextView.setText(note.getContent());
        getDate.setText(note.getDate());
        notesContainer.addView(noteView);
        noteView.setClickable(true);
        noteView.setLongClickable(true);
        noteView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                deleteNoteAndRefresh(note);
                return true;

            }
        });

    }



    private void deleteNoteAndRefresh(Note note) {

        noteList.remove(note);
        saveNoteToPreferences();
        refreshNotesView();
    }

    private void refreshNotesView() {

        notesContainer.removeAllViews();
        displayNotes();
    }


    private void saveNoteToPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_NOTE_COUNT, noteList.size());
        for (int i = 0; i < noteList.size(); i++) {

            Note note = noteList.get(i);

            editor.putString("note_title_" + i, note.getTitle().toString());
            editor.putString("note_content_" + i, note.getContent());
            editor.putString("note_date_" + 1, note.getDate());
            editor.putString("note_time_" + i, note.getTime());
        }

        editor.apply();
    }

    public class Note {
        private String title;
        private String Content;

        private String Date;

        private String Time;

        public Note() {


        }

        public String getTime() {

            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {

            this.Date = Date;
        }

        public String getTitle() {

            return title;

        }

        public void setTitle(String title) {

            this.title = title;
        }

        public String getContent() {

            return Content;
        }

        public void setContent(String content) {

            this.Content = content;
        }

        public Note(String title, String content, String Date, String Time) {

            this.title = title;
            this.Content = content;
            this.Date = Date;
            this.Time = Time;

        }


    }

    public void goBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}