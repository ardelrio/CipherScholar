package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class HillCipherDecrypt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hill_cipher_decrypt);
    }

    public void clickFunction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickDecryptFunction(View view) {
        ArrayList coprimes = new ArrayList<Integer>(Arrays.asList(1,3,5,7,9,11,15,17,19,21,23,25));
        TextView textView = (TextView) findViewById(R.id.textView);
        TextView daTextView = (TextView) findViewById(R.id.textViewD_00);
        TextView dbTextView = (TextView) findViewById(R.id.textViewD_01);
        TextView dcTextView = (TextView) findViewById(R.id.textViewD_10);
        TextView ddTextView = (TextView) findViewById(R.id.textViewD_11);

        //Get ciphertext
        EditText ciphertext = (EditText) findViewById(R.id.editText);
        String ct = ciphertext.getText().toString();  // ct = ciphertext as string

        //Get key matrix values
        EditText keyA = (EditText) findViewById(R.id.editTextE_00);
        EditText keyB = (EditText) findViewById(R.id.editTextE_01);
        EditText keyC = (EditText) findViewById(R.id.editTextE_10);
        EditText keyD = (EditText) findViewById(R.id.editTextE_11);

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
            int det = ((a*d) - (b*c)) % 26;
            if (det < 0)
            {
                det = 26 + det;
            }

            //Check that inputs are formatted correctly
            if (ct.length() < 1) {
                Toast.makeText(this, "Incorrect formatting of inputs: No blank spaces", Toast.LENGTH_SHORT).show();
            }
            else if ((a < 0 || a > 26)
                    || (b < 0 || b > 26)
                    || (c < 0 || c > 26)
                    || (d < 0 || d > 26)) {
                Toast.makeText(this, "Key values: enter a number 0-26", Toast.LENGTH_SHORT).show();
            }
            else if (!coprimes.contains(det))
            {
                Toast.makeText(this, "Encryption matrix is not invertible in mod 26, cannot decrypt", Toast.LENGTH_LONG).show();
            }
            else
            {
                //Perform decryption

                //If ciphertext length is odd, add Z
                if (ct.length() % 2 != 0)
                {
                    ct = ct + 'Z';
                }

                /* convert ciphertext to numbers  */
                PlaintextCleaner pc = new PlaintextCleaner();
                ct = pc.removeExcess(ct);
                int[] cn = pc.convertToNum(ct);  // cn = ciphertext as numbers

                //Get determinant inverse in mod 26
                int detI = 0; //Inverse of the determinant % 26
                for(int i = 0; i < coprimes.size(); i++)
                {
                    int product = det * (int) coprimes.get(i);
                    if (product % 26 == 1)
                    {
                        detI = (int) coprimes.get(i);
                        break;
                    }
                }

                //Get decryption matrix values
                int da = (d * detI) % 26;
                if (da < 0)
                {
                    da = 26 + da;
                }

                int db = (-b * detI) % 26;
                if (db < 0)
                {
                    db = 26 + db;
                }

                int dc = (-c * detI) % 26;
                if (dc < 0)
                {
                    dc = 26 + dc;
                }

                int dd = (a * detI) % 26;
                if (dd < 0)
                {
                    dd = 26 + dd;
                }

                ArrayList<Integer> result = new ArrayList<Integer>();

                for(int i = 0; i < cn.length - 1; i=i+2)
                {
                    result.add((da * cn[i] + db * cn[i+1]) % 26);
                    result.add((dc * cn[i] + dd * cn[i+1]) % 26);
                }

                int [] decrypted_int = new int[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    decrypted_int[i] = result.get(i);
                }

                String encrypted_string = pc.convertToString(decrypted_int);  // final decrypted word as string
                ciphertext.setText(encrypted_string);
                textView.setText("Ciphertext -> Plaintext");
                daTextView.setText("" + da);
                dbTextView.setText("" + db);
                dcTextView.setText("" + dc);
                ddTextView.setText("" + dd);

            }
        }

    }
}
