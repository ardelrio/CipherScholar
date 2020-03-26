package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VigenereCipherDecrypt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigenere_cipher_decrypt);
    }

    public void clickFunction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickDecryptFunction(View view) {
        EditText ciphertext = (EditText) findViewById(R.id.editText);
        EditText key = (EditText) findViewById(R.id.editText2);

        TextView textView = (TextView) findViewById(R.id.textView);

        String c = ciphertext.getText().toString();  // c = ciphertext as string
        String k_string = key.getText().toString();  // k_string = key as string

        // make sure everything is in the correct form
        if (k_string.length() < 1 || c.length() < 1) {
            Toast.makeText(this, "Incorrect formatting of input", Toast.LENGTH_SHORT).show();
        }

        /* convert ciphertext to numbers  */
        PlaintextCleaner pc = new PlaintextCleaner();
        c = pc.removeExcess(c);
        int[] ct = pc.convertToNum(c);  // ct = ciphertext as numbers

        /* convert key to numbers  */
        k_string = pc.removeExcess(k_string);
        int[] k = pc.convertToNum(k_string);  // k = key as number

        int[] decrypted_int = new int[c.length()];  // final decrypted word as int
        for(int i = 0; i < c.length(); i++) {
            int sub = ct[i] - k[i % k.length];
            if(sub < 0) {
                while(sub < 0)
                sub += 26;
            }
            decrypted_int[i] = sub % 26;
        }

        /* Convert cipher-text to string */
        String decrypted_string = pc.convertToString(decrypted_int);  // final decrypted word as string
        ciphertext.setText(decrypted_string);
        textView.setText("Ciphertext -> Plaintext");
    }
}
