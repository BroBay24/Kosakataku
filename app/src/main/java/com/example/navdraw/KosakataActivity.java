package com.example.navdraw;

import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class KosakataActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_kosakata);

        // Inisialisasi kelas Text-to-Speech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        // Terapkan padding untuk system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Buat tabel
        setupTable();
    }

    private void setupTable() {
        TableLayout tableLayout = findViewById(R.id.tableLayout);
// Data untuk tabel
        String[][] data = {
                {"1", "Man", "Pria"},
                {"2", "Woman", "Wanita"},
                {"3", "Boy", "Anak laki-laki"},
                {"4", "Girl", "Anak perempuan"},
                {"5", "Father", "Ayah"},
                {"6", "Mother", "Ibu"},
                {"7", "Son", "Anak laki-laki (anak)"},
                {"8", "Daughter", "Anak perempuan (anak)"},
                {"9", "Brother", "Saudara laki-laki"},
                {"10", "Sister", "Saudara perempuan"},
                {"11", "Grandfather", "Kakek"},
                {"12", "Grandmother", "Nenek"},
                {"13", "Uncle", "Paman"},
                {"14", "Aunt", "Bibi"},
                {"15", "Cousin", "Sepupu"},
                {"16", "Nephew", "Keponakan laki-laki"},
                {"17", "Niece", "Keponakan perempuan"},
                {"18", "Husband", "Suami"},
                {"19", "Wife", "Istri"},
                {"20", "Friend", "Teman"},
                {"21", "Neighbor", "Tetangga"},
                {"22", "Child", "Anak"},
                {"23", "Parent", "Orang tua"},
                {"24", "Relative", "Kerabat"},
                {"25", "Family", "Keluarga"},
        };


        // Buat header tabel
        TableRow headerRow = new TableRow(this);
        String[] headerText = {"No", "English", "Indonesian", "Action"};
        for (String text : headerText) {
            TextView textView = new TextView(this);
            textView.setText(text);
            textView.setPadding(16, 16, 16, 16);
            textView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            textView.setTextColor(getResources().getColor(android.R.color.white));
            textView.setTypeface(null, Typeface.BOLD);
            headerRow.addView(textView);
        }
        tableLayout.addView(headerRow);

        // Tambahkan data ke tabel
        for (String[] row : data) {
            TableRow tableRow = new TableRow(this);

            for (int i = 0; i < row.length; i++) {
                TextView textView = new TextView(this);
                textView.setText(row[i]);
                textView.setPadding(16, 16, 16, 16);
                tableRow.addView(textView);
            }

            // Tambahkan tombol mic
            Button micButton = new Button(this);
            micButton.setText("🔊");
            micButton.setOnClickListener(v -> {
                String word = row[1];  // Kata bahasa Inggris
                textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, null);
            });

            tableRow.addView(micButton);
            tableLayout.addView(tableRow);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
