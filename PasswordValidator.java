import java.io.*;
import java.util.*;

public class PasswordValidator {
    public static void main(String[] args) throws IOException {
        List<String> dictionary = readDictionary("src/wordlist.10000.txt");

        SeparateChainingHashST<String, Integer> scHashTableOld = new SeparateChainingHashST<>(true);
        SeparateChainingHashST<String, Integer> scHashTableCurr = new SeparateChainingHashST<>(false);
        LinearProbingHashST<String, Integer> lpHashTableOld = new LinearProbingHashST<>(true);
        LinearProbingHashST<String, Integer> lpHashTableCurr = new LinearProbingHashST<>(false);

        for (String word : dictionary) {
            scHashTableOld.put(word, 1);
            scHashTableCurr.put(word, 1);
            lpHashTableOld.put(word, 1);
            lpHashTableCurr.put(word, 1);
        }

        String[] passwords = {
                "account8", "accountability", "9a$D#qW7!uX&Lv3zT",
                "B@k45*W!c$Y7#zR9P", "X$8vQ!mW#3Dz&Yr4K5"
        };

        for (String password : passwords) {
            boolean isStrong = isStrongPassword(password, scHashTableOld, scHashTableCurr, lpHashTableOld, lpHashTableCurr);
            System.out.println("Password: " + password);
            System.out.println("Strong password? " + (isStrong ? "Yes" : "No"));

            System.out.println("Comparisons (Separate Chaining - Old Hash): " + scHashTableOld.getComparisons());
            System.out.println("Comparisons (Separate Chaining - Current Hash): " + scHashTableCurr.getComparisons());

            System.out.println("Comparisons (Linear Probing - Old Hash): " + lpHashTableOld.getComparisons());
            System.out.println("Comparisons (Linear Probing - Current Hash): " + lpHashTableCurr.getComparisons());

            System.out.println("-------------------------------------------");
        }
    }

    private static boolean isStrongPassword(String password, SeparateChainingHashST<String, Integer> scOld, SeparateChainingHashST<String, Integer> scCurr,
                                            LinearProbingHashST<String, Integer> lpOld, LinearProbingHashST<String, Integer> lpCurr) {
        if (password.length() < 8) {
            return false;
        }

        if (scOld.get(password) != null || scCurr.get(password) != null) {
            return false;
        }

        if (lpOld.get(password) != null || lpCurr.get(password) != null) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                String base = password.substring(0, i);
                if (scOld.get(base) != null || scCurr.get(base) != null) {
                    return false;
                }
            }
        }

        return true;
    }

    private static List<String> readDictionary(String fileName) throws IOException {
        List<String> dictionary = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            dictionary.add(line.trim());
        }
        reader.close();
        return dictionary;
    }
}