package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AffineCipher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affine_cipher);
    }

    public void clickFunction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickEncryptFunction(View view) {
        EditText plaintext = (EditText) findViewById(R.id.editText);
        EditText key1 = (EditText) findViewById(R.id.editText2);
        EditText key2 = (EditText) findViewById(R.id.editText3);

        TextView textView = (TextView) findViewById(R.id.textView);

        String p = plaintext.getText().toString();  // p = plaintext as string
        int k1 = Integer.parseInt(key1.getText().toString());
        int k2 = Integer.parseInt(key2.getText().toString());

        // make sure everything is in the correct form
        if (p.length() < 1) {
            Toast.makeText(this, "Incorrect formatting of input", Toast.LENGTH_SHORT).show();
        }
        else if (k1 < 0 || k1 > 26) {
            Toast.makeText(this, "Enter a number 0-26", Toast.LENGTH_SHORT).show();
        }
        else if (k2 < 0 || k2 > 26) {
            Toast.makeText(this, "Enter a number 0-26", Toast.LENGTH_SHORT).show();
        }
        else {
            /* convert plaintext to numbers  */
            PlaintextCleaner pc = new PlaintextCleaner();
            p = pc.removeExcess(p);
            int[] pt = pc.convertToNum(p);  // pt = plaintext as number

            int[] encrypted_int = new int[p.length()];  // final encrypted word as int
            for (int i = 0; i < p.length(); i++) {
                encrypted_int[i] = ((k1 * pt[i]) + k2) % 26;
            }

            /* Convert cipher-text to string */
            String encrypted_string = pc.convertToString(encrypted_int);  // final encrypted word as string
            plaintext.setText(encrypted_string);
            textView.setText("Plaintext -> Ciphertext");
        }
    }
}
