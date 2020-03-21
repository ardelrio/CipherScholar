package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.math.BigInteger;
import java.util.Random;

public class RsaCipher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa_cipher);
    }

    public void clickFunction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Generate random large prime numbers p and q
     * @param view idk what the point of this is but ok
     */
    public void generate(View view) {
        EditText e1 = (EditText) findViewById(R.id.editText2);
        EditText e2 = (EditText) findViewById(R.id.editText3);

        BigInteger p = BigInteger.probablePrime(160, new Random());
        BigInteger q = BigInteger.probablePrime(160, new Random());

        e1.setText(p.toString());
        e2.setText(e2.toString());
    }

    public void clickEncryptFunction(View view) {
        //TODO add encrypt functionality
    }
}
