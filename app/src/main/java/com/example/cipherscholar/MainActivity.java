package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String selectedCipher = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRadioButtonClicked(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId())
        {
            case R.id.shiftCipher:
                if(checked)
                {
                    this.selectedCipher = "shift";
                }
                break;
            case R.id.vigenereCipher:
                if(checked)
                {
                    this.selectedCipher = "vigenere";
                }
                break;
            case R.id.affineCipher:
                if(checked)
                {
                    this.selectedCipher = "affine";
                }
                break;
            case R.id.hillCipher:
                if(checked)
                {
                    this.selectedCipher = "hill";
                }
                break;
            case R.id.rsaCipher:
                if(checked)
                {
                    this.selectedCipher = "rsa";
                }
                break;
        }
    }

    public void onEncryptButtonClicked(View view)
    {
        switch(this.selectedCipher)
        {
            case "shift":
                //Go to shift encrypt activity
                Intent intent = new Intent(this, ShiftCipher.class);
                startActivity(intent);
                break;
            case "vigenere":
                //Go to vigenere encrypt activity
                intent = new Intent(this, VigenereCipher.class);
                startActivity(intent);
                break;
            case "hill":
                //Go to hill encrypt activity
                break;
            case "affine":
                //Go to affine encrypt activity
                break;
            case "rsa":
                //Go to rsa encrypt activity
                break;
        }
    }

    public void onDecryptButtonClicked(View view)
    {
        switch(this.selectedCipher)
        {
            case "shift":
                //Go to shift decrypt activity
                Intent intent = new Intent(this, ShiftCipherDecrypt.class);
                startActivity(intent);
                break;
            case "vigenere":
                //Go to vigenere decrypt activity
                intent = new Intent(this, VigenereCipherDecrypt.class);
                startActivity(intent);
                break;
            case "hill":
                //Go to hill decrypt activity
                break;
            case "affine":
                //Go to affine decrypt activity
                break;
            case "rsa":
                //Go to rsa decrypt activity
                break;
        }
    }

    public void onLearnButtonClicked(View view)
    {
        switch(this.selectedCipher)
        {
            case "shift":
                //Go to shift learn activity
                break;
            case "vigenere":
                //Go to vigenere learn activity
                break;
            case "hill":
                //Go to hill learn activity
                break;
            case "affine":
                //Go to affine learn activity
                break;
            case "rsa":
                //Go to rsa learn activity
                break;
        }
    }

    public void onPracticeButtonClicked(View view)
    {
        //Go to practice activity
    }
}
