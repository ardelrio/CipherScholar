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

import java.util.ArrayList;
import java.util.Arrays;

public class HillCipherPractice extends AppCompatActivity {
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

    private String mode = "encrypt";
    private String p = ""; //Plaintext
    private String c = ""; //Ciphertext
    private int k1; //Key (The nature of this variable changes for diff ciphers)
    private int k2;
    private int k3;
    private int k4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hill_cipher_practice);

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
                instructionsView.setText("Type in the correct ciphertext. Encryption matrix: " + "a = " + this.k1 + ", b = " + this.k2 + ", c = " + this.k3 + ", d = " + this.k4);
                break;
            case "decrypt":
                problemView.setText(this.c);
                instructionsView.setText("Type in the correct plaintext. Encryption matrix: " + "a = " + this.k1 + ", b = " + this.k2 + ", c = " + this.k3 + ", d = " + this.k4);
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
        ArrayList coprimes = new ArrayList<Integer>(Arrays.asList(1,3,5,7,9,11,15,17,19,21,23,25));

        //Generate keys: determinant must be coprime
        int key1 = 0;
        int key2 = 0;
        int key3 = 0;
        int key4 = 0;

        while(!coprimes.contains( ((key1 * key4) - (key2 * key3)) % 26 ))
        {
            key1 = (int)(27 * Math.random());
            key2 = (int)(27 * Math.random());
            key3 = (int)(27 * Math.random());
            key4 = (int)(27 * Math.random());
        }


        this.k1 = key1;
        this.k2 = key2;
        this.k3 = key3;
        this.k4 = key4;

        /* convert plaintext to numbers  */
        PlaintextCleaner pc = new PlaintextCleaner();
        plaintext = pc.removeExcess(plaintext);

        int[] pt = pc.convertToNum(plaintext);  // pt = plaintext as number
        ArrayList<Integer> result = new ArrayList<Integer>();

        if (pt.length % 2 == 1)
        {
            pt = new int[]{pt[0], 25};
        }

        for(int i = 0; i < pt.length - 1; i=i+2)
        {
            result.add((key1 * pt[i] + key2 * pt[i+1]) % 26);
            result.add((key3 * pt[i] + key4 * pt[i+1]) % 26);
        }

        int [] encrypted_int = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            encrypted_int[i] = result.get(i);
        }

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