/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package etc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GetImagePixels {

    public GetImagePixels() {
    }

    public GetImagePixels(String filename) {
        /**
         * Read a sample image from the file system
         */
        BufferedImage image = readImage(filename);

        /**
         * Call the method that prints out ARGB color Information.
         * ARGB = Alpha, Red, Green and Blue
         */
        getAllGrayDetails(image);
    }

    public static void printAllARGBDetails(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println("Image Dimension: Height-" + height + ", Width-"
                + width);
        System.out.println("Total Pixels: " + (height * width));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                int pixel = image.getRGB(i, j);
                System.out.println("Pixel Location(" + i + "," + j + ")- ["
                        + getARGBPixelData(pixel) + "]");
            }
        }
    }

    public static String getARGBPixelData(int pixel) {
        String pixelARGBData = "";
        /**
         * Shift all pixels 24 bits to the right.
         * Do a logical and with 0x000000FF
         * i.e. 0000 0000 0000 0000 0000 0000 1111 1111
         * You will get the alpha value for the pixel
         */
        int alpha = (pixel >> 24) & 0x000000FF;

        /**
         * Shift all pixels 16 bits to the right.
         * Do a logical and with 0x000000FF
         * i.e. 0000 0000 0000 0000 0000 0000 1111 1111
         * You will get the red value for the pixel
         */
        int red = (pixel >> 16) & 0x000000FF;

        /**
         * Shift all pixels 8 bits to the right.
         * Do a logical and with 0x000000FF
         * i.e. 0000 0000 0000 0000 0000 0000 1111 1111
         * You will get the green value for the pixel
         */
        int green = (pixel >> 8) & 0x000000FF;

        /**
         * Do not do any shift.
         * Do a logical and with 0x000000FF
         * i.e. 0000 0000 0000 0000 0000 0000 1111 1111
         * You will get the blue value for the pixel
         */
        int blue = (pixel) & 0x000000FF;

        pixelARGBData = "Alpha: " + alpha + ", " + "Red: " + red + ", "
                + "Green: " + green + ", " + "Blue: " + blue;

        return pixelARGBData;
    }

    public static int[] getAllGrayDetails(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int k = 0;
        int[] pix = new int[width * height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = image.getRGB(i, j);
                pix[k] = Integer.parseInt(getGrayPixelData(pixel));
                //     System.out.println(pix[k] + " ");
                k++;
            }
        }
        return pix;
    }

    /**
     * Image Pixels are Arrays of Integers [32 bits/4Bytes]
     * Consider a 32 pixel as 11111111-00110011-00111110-00011110
     *
     * First Byte From Left [11111111]= Alpha
     * Second Byte From Left[00110011]= Red
     * Third Byte From Left [00111110]= Green
     * Fourth Byte From Left[00011110]= Blue
     *
     * The following method will do a proper bit shift and
     * logical AND operation to extract the correct values
     * of different color/alpha components.
     *
     */
    public static String getGrayPixelData(int pixel) {
        String pixelGrayData = "";
        /**
         * Shift all pixels 24 bits to the right.
         * Do a logical and with 0x000000FF
         * i.e. 0000 0000 0000 0000 0000 0000 1111 1111
         * You will get the alpha value for the pixel
         */
        int alpha = (pixel >> 24) & 0x000000FF;

        /**
         * Shift all pixels 16 bits to the right.
         * Do a logical and with 0x000000FF
         * i.e. 0000 0000 0000 0000 0000 0000 1111 1111
         * You will get the red value for the pixel
         */
        int red = (pixel >> 16) & 0x000000FF;

        /**
         * Shift all pixels 8 bits to the right.
         * Do a logical and with 0x000000FF
         * i.e. 0000 0000 0000 0000 0000 0000 1111 1111
         * You will get the green value for the pixel
         */
        int green = (pixel >> 8) & 0x000000FF;

        /**
         * Do not do any shift.
         * Do a logical and with 0x000000FF
         * i.e. 0000 0000 0000 0000 0000 0000 1111 1111
         * You will get the blue value for the pixel
         */
        int blue = (pixel) & 0x000000FF;

        pixelGrayData = "" + (red + green + blue) / 3;

        return pixelGrayData;
    }

    /**
     * This method reads an image from the file
     * @param fileLocation -- > eg. "C:/testImage.jpg"
     * @return BufferedImage of the file read
     */
    public static BufferedImage readImage(String fileLocation) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileLocation));
        } catch (IOException e) {
        }
        return img;
    }

    public static void main(String args[]) {
        GetImagePixels getimagepixels = new GetImagePixels("A:\\ETC\\test images\\standard test images\\input1.jpg");
    }
}
