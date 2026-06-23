import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class ImageEncryption {

    // XOR is self-reversing — same function does both encrypt AND decrypt
    public static BufferedImage processImage(BufferedImage image, int key) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);

                int alpha = (pixel >> 24) & 0xff;
                int red   = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue  = pixel & 0xff;

                // XOR each channel with the key
                red   = red ^ key;
                green = green ^ key;
                blue  = blue ^ key;

                int newPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                result.setRGB(x, y, newPixel);
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter input image path: ");
        String inputPath = sc.nextLine();

        System.out.print("Enter key (0-255): ");
        int key = sc.nextInt();

        BufferedImage original = ImageIO.read(new File(inputPath));

        BufferedImage encrypted = processImage(original, key);
        ImageIO.write(encrypted, "png", new File("encrypted.png"));
        System.out.println("Encrypted image saved as encrypted.png");

        BufferedImage decrypted = processImage(encrypted, key);
        ImageIO.write(decrypted, "png", new File("decrypted.png"));
        System.out.println("Decrypted image saved as decrypted.png");

        sc.close();
    }
}