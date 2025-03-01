package com.example.navdraw;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.navdraw.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Tombol untuk berpindah ke aktivitas lainnya
        Button btnFrasa = findViewById(R.id.btnFrasa);
        Button btnKosakata = findViewById(R.id.btnKosakata);

        btnFrasa.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FrasaActivity.class);
            startActivity(intent);
        });

        btnKosakata.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, KosakataActivity.class);
            startActivity(intent);
        });

        // Toolbar
        setSupportActionBar(binding.appBarMain.toolbar);

        // Drawer dan NavigationView
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Konfigurasi menu navigasi
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_share, R.id.nav_slideshow ,R.id.nav_exit)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Menangani klik item navigasi
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_share) {
                shareToWhatsApp();
            } else if (id == R.id.nav_slideshow) {
                Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_exit) {
                finish();
            }

            drawer.closeDrawers(); // Tutup drawer setelah item dipilih
            return true;
        });
    }

    // Fungsi untuk membagikan aplikasi ke WhatsApp
    private void shareToWhatsApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String message = "Hai! Cek aplikasi KosaKataKu di sini:\n\n" +
                "📱 Download sekarang: https://www.example.com\n" +
                "Dapatkan pengalaman belajar kosa kata yang mudah dan menyenangkan!";
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setPackage("com.whatsapp");

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Snackbar.make(binding.getRoot(), "WhatsApp tidak ditemukan!", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}
