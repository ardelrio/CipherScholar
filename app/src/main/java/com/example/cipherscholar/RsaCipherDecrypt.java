package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RsaCipherDecrypt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa_cipher_decrypt);
    }

    public void clickFunction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickDecryptFunction(View view) {
        /* Init some useful stuff */
        EditText ciphertext = (EditText) findViewById(R.id.editText);
        EditText keysquare = (EditText) findViewById(R.id.editText2);
        EditText keyword = (EditText) findViewById(R.id.editText3);
        TextView textView = (TextView) findViewById(R.id.textView);
        PlaintextCleaner pc = new PlaintextCleaner();

        /* Get the plaintext, keysquare, and keyword */
        String ct = ciphertext.getText().toString();
        ct = pc.removeExcess(ct);
        String ks = keysquare.getText().toString();
        ks = pc.removeExcess(ks);
        String kw = keyword.getText().toString();
        kw = pc.removeExcess(kw);

        /* Make sure we got something useful */
        if (    ct == null || ct.length() == 0 ||
                ks == null || ks.length() == 0 ||
                kw == null || kw.length() == 0) {
            return;
        }
        if (ct.length() % 2 != 0) {
            return;
        }

        /* REPLACE I WITH J */
        ct = ct.replaceAll("I", "J");
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

        /* Populate c1 - pre transposition */

        // setup
        int num_rows = ct.length() / kw.length() + ((ct.length() % kw.length() == 0) ? 0 : 1);  // ceiling of ct / kw
        int num_extra = ct.length() % kw.length();  // number of chars that are in the last row ( <= length of the keyword)
        String[][] c1 = new String[num_rows][kw.length()];
        for (int i = 0; i < c1.length; i++) {
            for (int j = 0; j < c1[i].length; j++) {
                c1[i][j] = "";
            }
        }

        int[] map = new int[kw.length()];
        int[] kw_int = pc.convertToNum(kw);
        int min_index = 0;
        int min_num = 0;

        // get the keyword map
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

        // get the binary mask
        int[] mask = new int[map.length];
        for (int i = 0; i < mask.length; i++) {
            if (map[i] < num_extra) {
                mask[i] = 1;
            } else {
                mask[i] = 0;
            }
        }

        // make the index to populate c1
        int[] index = new int[mask.length];
        for (int i = 0; i < index.length; i++) {
            if (mask[i] == 1) {
                index[i] = num_rows;
            } else {
                index[i] = num_rows - 1;
            }
        }

        // populate c1
        char[] ct_char = ct.toCharArray();
        int count = 0;
        for (int j = 0; j < kw.length(); j++) {
            for (int i = 0; i < index[j]; i++) {
                c1[i][j] = Character.toString(ct_char[count]);
                count++;
            }
        }

        /* Perform the transposition */
        String[][] c2 = new String[num_rows][kw.length()];
        for (int i = 0; i < c1.length; i++) {
            for (int j = 0; j < c1[i].length; j++) {
                c2[i][j] = "";
            }
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < num_rows; j++) {
                c2[j][map[i]] = c1[j][i];
            }
        }


        /* Put c2 into a String */
        String c2_str = "";
        for (int i = 0; i < c1.length; i++) {
            for (int j = 0; j < c1[i].length; j++) {
                c2_str += c2[i][j];
            }
        }

        int[] c2_int = pc.convertToNum(c2_str);
        String plain = "";

        for (int i = 0; i < c2_int.length; i += 2) {
            int row = c2_int[i];
            int col = c2_int[i + 1];

            if (row == 0) {

            } else if (row == 3) {
                row = 1;
            } else if (row == 5) {
                row = 2;
            } else if (row == 6) {
                row = 3;
            } else {
                row = 4;
            }

            if (col == 0) {

            } else if (col == 3) {
                col = 1;
            } else if (col == 5) {
                col = 2;
            } else if (col == 6) {
                col = 3;
            } else {
                col = 4;
            }
            plain += square[row][col];
        }

        textView.setText("Ciphertext -> Plaintext");
        ciphertext.setText(plain);

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
