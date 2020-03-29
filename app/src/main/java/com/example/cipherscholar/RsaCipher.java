package com.example.cipherscholar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

    /**
     * Generate random large prime numbers p and q
     * @param view idk what the point of this is but ok
     */
    public void generate(View view) {
        EditText e1 = (EditText) findViewById(R.id.editText2);
        EditText e2 = (EditText) findViewById(R.id.editText3);

        p = BigInteger.probablePrime(160, new Random());
        q = BigInteger.probablePrime(160, new Random());

        e1.setText(p.toString());
        e2.setText(q.toString());
    }

    public void clickEncryptFunction(View view) {
        if (p == null || q == null) { return; }  // TODO maybe only accept generated values of p and q?

        BigInteger n = p.multiply(q);
        BigInteger p_m = p.subtract(BigInteger.ONE);
        BigInteger q_m = q.subtract(BigInteger.ONE);
        BigInteger phi = p_m.multiply(q_m);

        /* Find e s.t. e is coprime to phi */
        BigInteger it = BigInteger.ZERO;
        BigInteger e = BigInteger.ZERO;
        while (!it.equals(BigInteger.ONE)) {
            e = new BigInteger(phi.bitLength(), new Random());
            it = gcd(e, phi);
        }

        /* find e s.t. ed = 1 mod phi */
        BigInteger d = e.modInverse(phi);

        /* ciphertext = message^e mod n */
        int e_int = e.intValue();
        int n_int = n.intValue();

        EditText plaintext = (EditText) findViewById(R.id.editText);
        String pt = plaintext.getText().toString();

        PlaintextCleaner pc = new PlaintextCleaner();

        pt = pc.removeExcess(pt);
        int[] pt_num = pc.convertToNum(pt);
        int[] ct = new int[pt_num.length];
        for (int i = 0; i < pt_num.length; i++) {
            ct[i] = ((int) Math.pow(pt_num[i], e_int)) % n_int;
        }

        String ct_str = pc.convertToString(ct);
        plaintext.setText(ct_str);
    }

    /**
     * Find the greatest common divisor of two BigIntegers
     * @param a
     * @param b
     * @return the gcd
     */
    public BigInteger gcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) return a;
        else return gcd(b, a.mod(b));
    }

}
