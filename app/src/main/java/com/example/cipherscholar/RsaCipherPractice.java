package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RsaCipherPractice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa_cipher_practice);
    }

    public void clickFunction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}