import java.util.Scanner;

public class PasswordChecker {

    public static String checkStrength(String password) {
        int score = 0;
        StringBuilder feedback = new StringBuilder();

        // Length check
        if (password.length() >= 8) {
            score++;
        } else {
            feedback.append("- Use at least 8 characters\n");
        }

        // Uppercase check
        if (password.matches(".*[A-Z].*")) {
            score++;
        } else {
            feedback.append("- Add an uppercase letter\n");
        }

        // Lowercase check
        if (password.matches(".*[a-z].*")) {
            score++;
        } else {
            feedback.append("- Add a lowercase letter\n");
        }

        // Digit check
        if (password.matches(".*[0-9].*")) {
            score++;
        } else {
            feedback.append("- Add a number\n");
        }

        // Special character check
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            score++;
        } else {
            feedback.append("- Add a special character (!@#$%^&* etc.)\n");
        }

        String strength;
        if (score <= 2) {
            strength = "Weak";
        } else if (score <= 4) {
            strength = "Moderate";
        } else {
            strength = "Strong";
        }

        return "Strength: " + strength + " (" + score + "/5)\n" +
               (feedback.length() > 0 ? "Suggestions:\n" + feedback : "Great password!");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        System.out.println(checkStrength(password));

        sc.close();
    }
}