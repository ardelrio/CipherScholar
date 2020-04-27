package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ShiftCipherDecrypt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_cipher_decrypt);
    }

    public void clickFunction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickDecryptFunction(View view) {
        // get the ciphertext input
        EditText ciphertext = (EditText) findViewById(R.id.editText);
        String c = ciphertext.getText().toString();

        // get the key input & make sure it is a number
        EditText key = (EditText) findViewById(R.id.editText2);
        String k_str = key.getText().toString();
        for (int i = 0; i < k_str.length(); i++) {
            char j = k_str.charAt(i);
            if (!Character.isDigit(j)) {
                return;  // TODO make a popup that says to enter a number
            }
        }
        int k = Integer.parseInt(k_str);

        // put into computationally useful format
        PlaintextCleaner pc = new PlaintextCleaner();
        c = pc.removeExcess(c);
        int [] c_num = pc.convertToNum(c);

        // perform the decryption
        for (int i = 0; i < c_num.length; i++) {
            c_num[i] = (c_num[i] - k) % 26;
            if (c_num[i] < 0)
            {
                c_num[i] = 26 + c_num[i];
            }
        }

        // turn into plaintext
        String p = pc.convertToString(c_num);

        // display plaintext
        ciphertext.setText(p);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Ciphertext -> Plaintext");
    }
}
