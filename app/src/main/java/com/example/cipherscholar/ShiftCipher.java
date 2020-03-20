package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ShiftCipher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_cipher);
    }

    public void clickFunction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickEncryptFunction(View view) {
        //TODO add encrypt functionality
        EditText plaintext = (EditText) findViewById(R.id.editText);
        EditText key = (EditText) findViewById(R.id.editText2);

        TextView textView = (TextView) findViewById(R.id.textView);

        String p = plaintext.getText().toString();
        String k_string = key.getText().toString();

        // TODO supplement return statements with a toast that says the correct formatting for plaintext
        // make sure everything is in the correct form
        if (k_string.length() < 1 || p.length() < 1) { return; }

//        char k = k_string.charAt(0);  // get the first char in the key and use it as the shift

        // parse the shift from k
        int shift = 0;
        shift = Integer.parseInt(k_string);  // only accept a number

        // Commented out because I figured let's only accept a number instead of a letter or number
//        if (Character.isDigit(k)) {
//            shift = Character.getNumericValue(k);
//        } else if (Character.isLetter(k)) {
//            // ASCII -> int
//            if (k >= 65 && k <= 90) {  // capital letter
//                shift = k - 65;
//            } else if (k >= 97 && k <= 122) {  // lower case
//                shift = k - 97;
//            } else {
//                return;
//            }
//        } else {
//            return; // key was in wrong form
//        }

        /* convert plaintext to numbers  */
        PlaintextCleaner pc = new PlaintextCleaner();
        p = pc.removeExcess(p);
        int[] pt = pc.convertToNum(p);  // pt = plaintext

        /* Do the shift */
        int[] ct = new int[pt.length];
        for (int i = 0; i < ct.length; i++) {
            ct[i] = (pt[i] + shift) % 26;
        }

        /* Convert cipher-text to string */
        String ciphertext = pc.convertToString(ct);

        // TODO change. Just putting this here for now to test my work
        plaintext.setText(ciphertext);
        textView.setText("Plaintext -> Ciphertext");

        // Maybe a toast?
        // Maybe a new activity/screen like in Currency Converter?
    }


}
