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
            case R.id.adfgxCipher:
                if(checked)
                {
                    this.selectedCipher = "adfgx";
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
                intent = new Intent(this, HillCipher.class);
                startActivity(intent);
                break;
            case "affine":
                //Go to affine encrypt activity
                intent = new Intent(this, AffineCipher.class);
                startActivity(intent);
                break;
            case "adfgx":
                //Go to rsa encrypt activity
                intent = new Intent(this, AdfgxCipher.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Please select a cipher", Toast.LENGTH_SHORT).show();
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
                Intent intent1 = new Intent(this, HillCipherDecrypt.class);
                startActivity(intent1);
                break;
            case "affine":
                //Go to affine decrypt activity
                Intent intent2 = new Intent(this, AffineCipherDecrypt.class);
                startActivity(intent2);
                break;
            case "adfgx":
                //Go to rsa decrypt activity
                Intent intent3 = new Intent(this, AdfgxCipherDecrypt.class);
                startActivity(intent3);
                break;
            default:
                Toast.makeText(this, "Please select a cipher", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onLearnButtonClicked(View view)
    {
        switch(this.selectedCipher)
        {
            case "shift":
                //Go to shift learn activity
                Intent intent = new Intent(this, ShiftCipherLearn.class);
                startActivity(intent);
                break;
            case "vigenere":
                //Go to vigenere learn activity
                intent = new Intent(this, VigenereCipherLearn.class);
                startActivity(intent);
                break;
            case "hill":
                //Go to hill learn activity
                Intent intent1 = new Intent(this, HillCipherLearn.class);
                startActivity(intent1);
                break;
            case "affine":
                //Go to affine learn activity
                Intent intent2 = new Intent(this, AffineCipherLearn.class);
                startActivity(intent2);
                break;
            case "adfgx":
                //Go to rsa learn activity
                Intent intent3 = new Intent(this, AdfgxCipherLearn.class);
                startActivity(intent3);
                break;
            default:
                Toast.makeText(this, "Please select a cipher", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onPracticeButtonClicked(View view)
    {
        switch(this.selectedCipher)
        {
            case "shift":
                //Go to shift learn activity
                Intent intent = new Intent(this, ShiftCipherPractice.class);
                startActivity(intent);
                break;
            case "vigenere":
                //Go to vigenere learn activity
                intent = new Intent(this, VigenereCipherPractice.class);
                startActivity(intent);
                break;
            case "hill":
                //Go to hill learn activity
                Intent intent1 = new Intent(this, HillCipherPractice.class);
                startActivity(intent1);
                break;
            case "affine":
                //Go to affine learn activity
                Intent intent2 = new Intent(this, AffineCipherPractice.class);
                startActivity(intent2);
                break;
            case "adfgx":
                //Go to rsa learn activity
                Intent intent3 = new Intent(this, AdfgxCipherPractice.class);
                startActivity(intent3);
                break;
            default:
                Toast.makeText(this, "Please select a cipher", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
