package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HillCipher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hill_cipher);
    }

    public void clickFunction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickEncryptFunction(View view) {
        TextView textView = (TextView) findViewById(R.id.textView);

        //Get plaintext
        EditText plaintext = (EditText) findViewById(R.id.editText);
        String p = plaintext.getText().toString();  // p = plaintext as string

        //Get key matrix values
        EditText keyA = (EditText) findViewById(R.id.editTextM_00);
        EditText keyB = (EditText) findViewById(R.id.editTextM_01);
        EditText keyC = (EditText) findViewById(R.id.editTextM_10);
        EditText keyD = (EditText) findViewById(R.id.editTextM_11);

        String keyAString = keyA.getText().toString();
        String keyBString = keyB.getText().toString();
        String keyCString = keyC.getText().toString();
        String keyDString = keyD.getText().toString();

        if (keyAString.length() < 1
                || keyBString.length() < 1
                || keyCString.length() < 1
                || keyDString.length() < 1) {
            Toast.makeText(this, "Incorrect formatting of inputs: No blank spaces", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int a = Integer.parseInt(keyA.getText().toString());
            int b = Integer.parseInt(keyB.getText().toString());
            int c = Integer.parseInt(keyC.getText().toString());
            int d = Integer.parseInt(keyD.getText().toString());

            //Check that inputs are formatted correctly
            if (p.length() < 1) {
                Toast.makeText(this, "Incorrect formatting of inputs: No blank spaces", Toast.LENGTH_SHORT).show();
            }
            else if ((a < 0 || a > 26)
                    || (b < 0 || b > 26)
                    || (c < 0 || c > 26)
                    || (d < 0 || d > 26)) {
                Toast.makeText(this, "Key values: enter a number 0-26", Toast.LENGTH_SHORT).show();
            }
            else {
                //Perform encryption

                /* convert plaintext to numbers  */
                PlaintextCleaner pc = new PlaintextCleaner();
                p = pc.removeExcess(p);

                int[] pt = pc.convertToNum(p);  // pt = plaintext as number

                ArrayList<Integer> result = new ArrayList<Integer>();

                if (pt.length % 2 == 1)
                {
                    int [] newpt = new int[pt.length + 1];
                    for(int i = 0; i < newpt.length; i++)
                    {
                        if (i == newpt.length - 1)
                        {
                            newpt[i] = 25;
                        }
                        else
                        {
                            newpt[i] = pt[i];
                        }
                    }
                    pt = newpt;
                }

                for(int i = 0; i < pt.length - 1; i=i+2)
                {
                    result.add((a * pt[i] + b * pt[i+1]) % 26);
                    result.add((c * pt[i] + d * pt[i+1]) % 26);
                }

                int [] encrypted_int = new int[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    encrypted_int[i] = result.get(i);
                }

                String encrypted_string = pc.convertToString(encrypted_int);  // final encrypted word as string
                plaintext.setText(encrypted_string);
                textView.setText("Plaintext -> Ciphertext");

            }
        }
    }
}
