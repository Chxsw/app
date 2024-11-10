package com.example.vmusic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.passworr);
    }

    // Método que se ejecutará al hacer clic en el botón de acceso
    public void OnClickAcceder(View view) {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.equals("vicente@gmail.com") && password.equals("1234")) {
            // Acceder a la siguiente actividad
            Intent intent = new Intent(MainActivity.this, CasaActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Mensaje de error
            Toast.makeText(MainActivity.this, "Datos incorrectos. Inténtelo de nuevo.", Toast.LENGTH_SHORT).show();
        }
    }
}


