/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package etc;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageReadWrite {

    public int[][] readImageData(BufferedImage bufferimage) throws Exception {
        int height = bufferimage.getHeight();
        int width = bufferimage.getWidth();
        int[][] PixelArray = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                int pixel = bufferimage.getRGB(i, j);
                /*
                int Pixel = pix >> 16 | pix >> 8 | pix;
                PixelArray[j][i]=Pixel;
                 *
                 */
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
        PixelArray[j][i]=Integer.parseInt(pixelGrayData);

            }
        }
        return PixelArray;
    }

    public void constructEncodedImage(int imageHeight, int imageWidth, int[][] pixels, String fname) throws Exception {
        // Let's create a BufferedImage for a gray level image.
        BufferedImage image = new BufferedImage(imageHeight, imageWidth, BufferedImage.TYPE_BYTE_GRAY);
        // We need its raster to set the pixels' values.
        WritableRaster wr = image.getRaster();
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int Pixel = pixels[j][i] << 16 | pixels[j][i] << 8 | pixels[j][i];
                wr.setSample(j, i, 0, Pixel);
            }
        }

        image.setData(wr);

        ImageIO.write(image, "jpg", new File("A:\\ETC\\test images\\standard test images", fname));

        System.out.println("image is created..");
    }
        public void reconstructImage(int imageHeight, int imageWidth, int[][] pixels, String fname) throws Exception {
        // Let's create a BufferedImage for a gray level image.
        BufferedImage image = new BufferedImage(imageHeight, imageWidth, BufferedImage.TYPE_BYTE_GRAY);
        // We need its raster to set the pixels' values.
        WritableRaster wr = image.getRaster();
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                int Pixel = pixels[j][i] << 16 | pixels[j][i] << 8 | pixels[j][i];
                wr.setSample(i, j, 0, Pixel);
            }
        }

        image.setData(wr);

        ImageIO.write(image, "jpg", new File("A:\\ETC\\test images\\standard test images", fname));
    }

    public static void main(String args[]) throws Exception {
        ImageReadWrite c = new ImageReadWrite();
        BufferedImage bi = ImageIO.read(new File("A:\\ETC\\test images\\standard test images\\input2.jpg"));
        int[][] PixelArray = c.readImageData(bi);
        c.reconstructImage(PixelArray.length, PixelArray[0].length, PixelArray, "saved.jpg");
    }
}
