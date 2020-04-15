package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.Random;

public class RsaCipher extends AppCompatActivity {

    BigInteger p = null;
    BigInteger q = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa_cipher);
    }

    public void clickFunction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void clickEncryptFunction(View view) {
        /* Init some useful stuff */
        EditText plaintext = (EditText) findViewById(R.id.editText);
        EditText keysquare = (EditText) findViewById(R.id.editText2);
        EditText keyword = (EditText) findViewById(R.id.editText3);
        TextView textView = (TextView) findViewById(R.id.textView);
        PlaintextCleaner pc = new PlaintextCleaner();

        /* Get the plaintext, keysquare, and keyword */
        String pt = plaintext.getText().toString();
        pt = pc.removeExcess(pt);
        String ks = keysquare.getText().toString();
        ks = pc.removeExcess(ks);
        String kw = keyword.getText().toString();
        kw = pc.removeExcess(kw);

        /* Make sure we got something useful */
        if (    pt == null || pt.length() == 0 ||
                ks == null || ks.length() == 0 ||
                kw == null || kw.length() == 0) {
            return;
        }

        /* REPLACE I WITH J */
        pt = pt.replaceAll("I", "J");
        ks = ks.replaceAll("I", "J");
        kw = kw.replaceAll("I", "J");

        String[][] square = new String[5][5];  // used to encrypt the plaintext
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                square[i][j] = "";
            }
        }

        String[] alph = {"A", "B", "C", "D", "E", "F", "G",
                "H", "J", "K", "L", "M", "N", "O", "P",
                "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        /* Populate the keysquare (square) */
        char[] ks_char = ks.toCharArray();
        for (int i = 0; i < ks_char.length; i++) {
            if (!isAdded(square, Character.toString(ks_char[i]))) {
                add(square, Character.toString(ks_char[i]));
            }
        }

        for (int i = 0; i < alph.length; i++) {
            if (!isAdded(square, alph[i])) {
                add(square, alph[i]);
            }
        }

        /* Perform first level of encryption */
        String[] adfgx = {"A", "D", "F", "G", "X"};

        int pt2 = 2 * pt.length();
        int num_rows = pt2 / kw.length() + ((pt2 % kw.length() == 0) ? 0 : 1);
        String[][] c1 = new String[num_rows][kw.length()];

        for (int i = 0; i < c1.length; i++) {
            for (int j = 0; j < c1[i].length; j++) {
                c1[i][j] = "";
            }
        }

        char[] pt_char = pt.toCharArray();
        for (int i = 0; i < pt_char.length; i++) {  // for each letter in the plaintext
            for (int j = 0; j < 5; j++) {  // loop thru square to find it
                for (int k = 0; k < 5; k++) {
                    if (square[j][k].equals(Character.toString(pt_char[i]))) {
                        add(c1, adfgx[j]);
                        add(c1, adfgx[k]);
                    }
                }
            }
        }

        /* Perform key columnar transposition */
        String c2 = "";

        // find the order to add to c2
        int[] map = new int[kw.length()];
        int[] kw_int = pc.convertToNum(kw);
        int min_index = 0;
        int min_num = 0;

        for (int i = 0; i < kw_int.length; i++) {
            min_num = 26;  // highest possible value in kw_int is 25
            for (int j = 0; j < kw_int.length; j++) {
                if (kw_int[j] == -1) {  // we've already counted this one
                    continue;
                }
                if (kw_int[j] < min_num) {  // current smallest
                    min_num = kw_int[j];
                    min_index = j;
                }
            }
            map[i] = min_index;  // found the smallest number
            kw_int[min_index] = -1;  // don't count it twice
        }

        // add the letters to c2
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < c1.length; i++) {
                c2 += c1[i][map[j]];
            }
        }

        /* Set the ciphertext */
        textView.setText("Plaintext -> Ciphertext");
        plaintext.setText(c2);

    }

    public boolean isAdded(String[][] sq, String a) {
        for (int i = 0; i < sq.length; i++) {
            for (int j = 0; j < sq[i].length; j++) {
                if (sq[i][j].equals(a)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void add(String[][] sq, String a) {
        for (int i = 0; i < sq.length; i++) {
            for (int j = 0; j < sq[i].length; j++) {
                if (sq[i][j].equals("")) {
                    sq[i][j] = a;
                    return;
                }
            }
        }
    }

}
