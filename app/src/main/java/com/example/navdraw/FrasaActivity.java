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

public class FrasaActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_frasa);

        // Inisialisasi Text-to-Speech
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
        String[][] data = {
                {"1", "How are you?", "Apa kabar?"},
                {"2", "See you later", "Sampai jumpa"},
                {"3", "I am hungry", "Saya lapar"},
                {"4", "Thank you", "Terima kasih"},
                {"5", "What is your name?", "Siapa nama kamu?"},
                {"6", "Good morning", "Selamat pagi"},
                {"7", "Good night", "Selamat malam"},
                {"8", "How much is this?", "Berapa harganya?"},
                {"9", "I don't understand", "Saya tidak mengerti"},
                {"10", "Please help me", "Tolong bantu saya"},
                {"11", "Where are you from?", "Dari mana kamu?"},
                {"12", "I'm sorry", "Maaf"},
                {"13", "Nice to meet you", "Senang bertemu denganmu"},
                {"14", "Goodbye", "Selamat tinggal"},
                {"15", "Have a nice day!", "Semoga harimu menyenangkan!"},
                {"16", "What's the time?", "Jam berapa sekarang?"},
                {"17", "Where is the restroom?", "Di mana kamar mandi?"},
                {"18", "I am lost", "Saya tersesat"},
                {"19", "Can you speak English?", "Bisa bicara bahasa Inggris?"},
                {"20", "I love you", "Aku cinta kamu"},
                {"21", "Happy birthday!", "Selamat ulang tahun!"},
                {"22", "Congratulations!", "Selamat!"},
                {"23", "Good luck!", "Semoga berhasil!"},
                {"24", "What's your phone number?", "Berapa nomor teleponmu?"},
                {"25", "See you tomorrow", "Sampai besok"},
        };

        // Buat header tabel
        TableRow headerRow = new TableRow(this);
        String[] headerText = {"No", "Phrase", "Translation", "Action"};
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
                String phrase = row[1];  // Frasa dalam bahasa Inggris
                textToSpeech.speak(phrase, TextToSpeech.QUEUE_FLUSH, null, null);
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
