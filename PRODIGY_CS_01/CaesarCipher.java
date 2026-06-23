import java.util.Scanner;

public class CaesarCipher {

    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        
        for (char ch : text.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                char shifted = (char) (((ch - 'A' + shift) % 26 + 26) % 26 + 'A');
                result.append(shifted);
            } else if (Character.isLowerCase(ch)) {
                char shifted = (char) (((ch - 'a' + shift) % 26 + 26) % 26 + 'a');
                result.append(shifted);
            } else {
                result.append(ch);
            }
        }
        
        return result.toString();
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, 26 - (shift % 26));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter message: ");
        String message = sc.nextLine();
        
        System.out.print("Enter shift value: ");
        int shift = sc.nextInt();
        
        String encrypted = encrypt(message, shift);
        String decrypted = decrypt(encrypted, shift);
        
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
        
        sc.close();
    }
}