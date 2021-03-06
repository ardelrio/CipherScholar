package com.example.cipherscholar;

/**
 * Use this class to to do some common operations on strings for encryption and decryption
 */
public class PlaintextCleaner {

    /**
     * Removes everything from a string except letters a through z, capital or lowercase
     * @param in the input string
     * @return the new string with letters A - Z
     */
    public String removeExcess(String in) {
        String ans = "";
        Character j;
        for (int i = 0; i < in.length(); i++) {
            j = in.charAt(i);
            if ((j >= 65 && j <= 90) || (j >= 97 && j <= 122)) {
                ans += Character.toUpperCase(j);
            }
        }
        return ans;
    }

    /**
     * Takes an input String that CAN ONLY BE LETTERS A - Z (see removeExcess) and returns an
     * Integer array containing each letter converted into a number
     * @param in the string containing A - Z ALL UPPERCASE
     * @return an int array with the letters represented as numbers
     */
    public int[] convertToNum(String in) {
        int[] ans = new int[in.length()];
        for (int i = 0; i < in.length(); i++) {
            ans[i] = in.charAt(i) - 65;
        }
        return ans;
    }

    /**
     * Turns an int array into letters.
     * @param in int[] with numbers BETWEEN 0 AND 25
     * @return a string of their ASCII representations
     */
    public String convertToString(int[] in) {
        String ans = "";
        for (int i = 0; i < in.length; i++) {
            ans += (char) (in[i] + 65);
        }
        return ans;
    }

}
