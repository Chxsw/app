package com.example.vmusic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class CasaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.casa);

        // Configuración del VideoView para reproducción local
        VideoView videoView = findViewById(R.id.videoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.videoplayback;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // Agregar controles de reproducción al VideoView
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();

        // Configuración del botón "Volver al Inicio de Sesión"
        Button btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(CasaActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Configuración del botón "Ir a Ubicación"
        Button btnAvanzar = findViewById(R.id.btnAvanzar);
        btnAvanzar.setOnClickListener(v -> {
            Intent intent = new Intent(CasaActivity.this, ubicacionActivity.class);
            startActivity(intent);
        });
    }
}




