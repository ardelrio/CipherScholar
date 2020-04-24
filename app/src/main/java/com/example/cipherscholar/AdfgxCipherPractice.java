package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class AdfgxCipherPractice extends AppCompatActivity {
    private static String[] words = new String[]{"absolutely","accessible","activities","additional",
            "affordable","apparently","appearance","applicable","applicants","appreciate","assessment",
            "assistance","associated","atmosphere","attractive","background","basketball","businesses",
            "candidates","categories","challenges","characters","collection","commercial","commission",
            "commitment","comparison","compatible","completely","completion","compliance","components",
            "conclusion","conditions","conference","confidence","connection","considered","consistent",
            "constantly","containing","continuous","contribute","controller","convenient","conversion",
            "curriculum","definitely","definition","department","determined","developers",
            "developing","difference","difficulty","directions","discovered","discussion","efficiency",
            "electrical","electronic","employment","encouraged","engagement","enterprise","especially",
            "evaluation","eventually","everything","everywhere","experience","expression","facilities",
            "foundation","frequently","functional","generation","government","guidelines","historical",
            "identified","importance","impossible","impressive","increasing","incredible","incredibly",
            "individual","industrial","industries","initiative","innovation","innovative","instrument",
            "integrated","interested","introduced","investment","leadership","literature","management",
            "medication","membership","operations","originally","particular","percentage","performing",
            "permission","personally","philosophy","population","prevention","previously","principles",
            "procedures","processing","production","properties","protection","reasonable","recognized",
            "regardless","relatively","represents","reputation","resistance","resolution","restaurant",
            "retirement","scientific","scientists","situations","specialist","statements","statistics",
            "strategies","structures","successful","sufficient","supporting","techniques","technology",
            "television","themselves","throughout","tournament","transition","treatments","ultimately",
            "understand","understood","university","vegetables","volunteers"};

    private static String[] keys = new String[]{"of","to","in","it","is","be","as","at","so","we","he",
            "by","or","on","do","if","me","my","up","an","go","no","us","am","the","and","for","are",
            "but","not","you","all","any","can","had","her","was","one","our","out","day","get","has",
            "him","his","how","man","new","now","old","see","two","way","who","boy","did","its","let",
            "put","say","she","too","use","that","with","have","this","will","your","from","they",
            "know","want","been","good","much","some","time"};

    private String mode = "encrypt";
    private String p = ""; //Plaintext
    private String pUnChanged = ""; //Plaintext to be shown w/o changing I -> J
    private String c = ""; //Ciphertext
    private String k; //Key (The nature of this variable changes for diff ciphers)
    private String kUnChanged = ""; //Keyword to be shown w/o changing I -> J
    private String ks; //Keysquare

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adfgx_cipher_practice);

        //Set up as encrypt by default
        this.setUpProblem();
        this.setUpPage();

        Switch modeSwitch = (Switch) findViewById(R.id.edSwitch);
        modeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be true if the switch is in the On position
                if(isChecked) {
                    mode = "decrypt";
                } else {
                    mode = "encrypt";
                }
                setUpPage();
            }
        });
    }

    public void clickFunction(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Retrieves a new random word for each new problem
     * @return randomly selected word to be used as the plaintext
     */
    static String getRandomString() {
        //Get random word from words
        int index = (int) (words.length * Math.random());
        return words[index].toUpperCase();
    }

    /**
     * Helper method for generateKeySquare, removes an element from list at index, and returns the
     * new list
     * @param list list to be modified
     * @param index index to be removed
     * @return modified list
    **/
    public static String[] removeAndResize(String[] list, int index) {
        String[] myList = new String [list.length - 1];
        for (int i = 0; i < list.length - 1; i++) {
            if (i < index) {
                myList[i] = list[i];
            } else {
                myList[i] = list[i+1];
            }
        }
        return myList;
    }

    /**
     * Generates a new random keysquare for each problem
     * @return random keysquare in the form of a String
     */
    public String generateKeySquare() {
        String keysquare = "";
        String [] alpha = {"A", "B", "C", "D", "E", "F", "G",
                "H", "J", "K", "L", "M", "N", "O", "P",
                "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for (int i =0; i < 25; i++) {
            int index = (int) (alpha.length * Math.random());
            keysquare += alpha[index];
            alpha = removeAndResize(alpha, index);
        }
        return keysquare;
    }

    public void setUpPage() {
        TextView instructionsView = (TextView) findViewById(R.id.instructionTextView);
        TextView problemView = (TextView) findViewById(R.id.guessTextView);

        switch(this.mode) {
            case "encrypt":
                problemView.setText(this.pUnChanged);
                instructionsView.setText("Type in the correct ciphertext. Encryption keys: " + "keysquare = " + this.ks + ",  keyword = " + this.kUnChanged);
                break;
            case "decrypt":
                problemView.setText(this.c);
                instructionsView.setText("Type in the correct ciphertext. Encryption keys: " + "keysquare = " + this.ks + ",  keyword = " + this.kUnChanged);
                break;
            default:
                break;
        }
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

    public void setUpProblem() {
        PlaintextCleaner pc = new PlaintextCleaner();
        //Generate 10 random character plaintext
        String plaintext = getRandomString();
        this.pUnChanged = pc.removeExcess(plaintext);
        this.p = pUnChanged.replaceAll("I", "J");

        //Generate ciphertext for this plaintext--varies depending on cipher
        int index = (int) (keys.length * Math.random());
        String k_string = keys[index]; //Get key from keys
        this.kUnChanged = pc.removeExcess(k_string);
        this.k = kUnChanged.toUpperCase().replaceAll("I", "J");

        this.ks = pc.removeExcess(generateKeySquare()); //Already contains no I

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

        int pt2 = 2 * p.length();
        int num_rows = pt2 / k.length() + ((pt2 % k.length() == 0) ? 0 : 1);
        String[][] c1 = new String[num_rows][k.length()];

        for (int i = 0; i < c1.length; i++) {
            for (int j = 0; j < c1[i].length; j++) {
                c1[i][j] = "";
            }
        }

        char[] pt_char = p.toCharArray();
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
        int[] map = new int[k.length()];
        int[] kw_int = pc.convertToNum(k);
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
        this.c = c2;
    }

    public void onSubmitButtonClicked(View view)
    {
        EditText response = (EditText) findViewById(R.id.responseEditText);
        String r = response.getText().toString();

        switch(this.mode)
        {
            case "encrypt":
                if(c.equals(r.toUpperCase().trim())) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Incorrect.", Toast.LENGTH_SHORT).show();
                }
                break;
            case "decrypt":
                if(p.equals(r.toUpperCase().trim())) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Incorrect.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public void onRevealButtonClicked(View view) {
        switch(this.mode) {
            case "encrypt":
                Toast.makeText(this, c, Toast.LENGTH_SHORT).show();
                break;
            case "decrypt":
                Toast.makeText(this, p, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void onGenerateButtonClicked(View view) {
        this.setUpProblem();
        this.setUpPage();
    }

}
