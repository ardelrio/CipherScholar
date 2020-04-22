package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class VigenereCipherPractice extends AppCompatActivity {
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
    private String c = ""; //Ciphertext
    private String k; //Key (The nature of this variable changes for diff ciphers)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vigenere_cipher_practice);

        //Set up as encrypt by default
        this.setUpProblem();
        this.setUpPage();

        Switch modeSwitch = (Switch) findViewById(R.id.edSwitch);
        modeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be true if the switch is in the On position
                if(isChecked)
                {
                    mode = "decrypt";
                }
                else
                {
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

    static String getRandomString()
    {
        //Get random word from words
        int index = (int) (words.length * Math.random());
        return words[index].toUpperCase();
    }

    public void setUpPage() {
        TextView instructionsView = (TextView) findViewById(R.id.instructionTextView);
        TextView problemView = (TextView) findViewById(R.id.guessTextView);

        switch(this.mode)
        {
            case "encrypt":
                problemView.setText(this.p);
                instructionsView.setText("Type in the correct ciphertext. Encryption key: \"" + this.k + "\"");
                break;
            case "decrypt":
                problemView.setText(this.c);
                instructionsView.setText("Type in the correct plaintext. Encryption key: \"" + this.k + "\"");
                break;
            default:
                break;
        }
    }

    public void setUpProblem() {
        //Generate 10 random character plaintext
        String plaintext = getRandomString();
        this.p = plaintext;

        //Generate ciphertext for this plaintext--varies depending on cipher
        int index = (int) (keys.length * Math.random());
        String k_string = keys[index]; //Get key from keys
        this.k = k_string.toUpperCase();

        /* convert plaintext to numbers  */
        PlaintextCleaner pc = new PlaintextCleaner();
        plaintext = pc.removeExcess(plaintext);
        int[] pt = pc.convertToNum(plaintext);  // pt = plaintext as number

        /* convert key to numbers  */
        k_string = pc.removeExcess(k_string);
        int[] k = pc.convertToNum(k_string);  // k = key as number

        int[] encrypted_int = new int[p.length()];  // final encrypted word as int
        for (int i = 0; i < p.length(); i++) {
            encrypted_int[i] = (pt[i] + k[i % k.length]) % 26;
        }

        /* Convert cipher-text to string */
        String encrypted_string = pc.convertToString(encrypted_int);  // final encrypted word as string
        this.c = encrypted_string;

    }

    public void onSubmitButtonClicked(View view)
    {
        EditText response = (EditText) findViewById(R.id.responseEditText);
        String r = response.getText().toString();

        switch(this.mode)
        {
            case "encrypt":
                if(c.equals(r.toUpperCase().trim()))
                {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Incorrect.", Toast.LENGTH_SHORT).show();
                }
                break;
            case "decrypt":
                if(p.equals(r.toUpperCase().trim()))
                {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Incorrect.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public void onRevealButtonClicked(View view)
    {
        switch(this.mode)
        {
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

    public void onGenerateButtonClicked(View view)
    {
        this.setUpProblem();
        this.setUpPage();
    }

}