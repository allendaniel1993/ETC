package etc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;

public class SplitImage {

    public void imageSplit(File selectedFile) throws Exception {

        // Load image file into memory
        BufferedImage image = ImageIO.read(selectedFile); //reading the image file

        //Divide how many small blocks
        int cols = (int) Math.ceil((image.getWidth() * 1.00) / (8.0 * 1.00));
        int rows = (int) Math.ceil((image.getHeight() * 1.00) / (8.0 * 1.00));
        int chunks = rows * cols;  // no of blocks
        int chunkWidth = image.getWidth() / cols;
        int chunkHeight = image.getHeight() / rows;

        String s = "Image size:" + image.getWidth() + " x " + image.getHeight() + "\nNo. of blocks:" + chunks + "\n";
        System.out.println(s);
        //Define an Image array to hold image chunks
        int count = 0;
        BufferedImage imgs[] = new BufferedImage[chunks];
        // Fill the Image array with splitted image parts
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();

                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        String[] str = new String[count];
        //Save mini images into image files
        for (int i = 0; i < imgs.length; i++) {
            File f = new File("A:\\ETC\\test images\\splits\\", ("" + i + ".jpg"));
            ImageIO.write(imgs[i], "jpg", f);
            str[i] = f.getAbsolutePath();
        }
    }

    public static void main(String[] args) throws Exception {
        SplitImage ist = new SplitImage();
        ist.imageSplit(new File("A:\\ETC\\test images\\standard test images\\input1.jpg"));
    }
}
