package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VigenereCipher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigenere_cipher);
    }

    public void clickFunction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickEncryptFunction(View view) {
        EditText plaintext = (EditText) findViewById(R.id.editText);
        EditText key = (EditText) findViewById(R.id.editText2);

        TextView textView = (TextView) findViewById(R.id.textView);

        String p = plaintext.getText().toString();  // p = plaintext as string
        String k_string = key.getText().toString();  // k_string = key as string

        // make sure everything is in the correct form
        if (k_string.length() < 1 || p.length() < 1) {
            Toast.makeText(this, "Incorrect formatting of input", Toast.LENGTH_SHORT).show();
        }
        else {

            /* convert plaintext to numbers  */
            PlaintextCleaner pc = new PlaintextCleaner();
            p = pc.removeExcess(p);
            int[] pt = pc.convertToNum(p);  // pt = plaintext as number

            /* convert key to numbers  */
            k_string = pc.removeExcess(k_string);
            int[] k = pc.convertToNum(k_string);  // k = key as number

            int[] encrypted_int = new int[p.length()];  // final encrypted word as int
            for (int i = 0; i < p.length(); i++) {
                encrypted_int[i] = (pt[i] + k[i % k.length]) % 26;
            }

            /* Convert cipher-text to string */
            String encrypted_string = pc.convertToString(encrypted_int);  // final encrypted word as string
            plaintext.setText(encrypted_string);
            textView.setText("Plaintext -> Ciphertext");
        }
    }
}
