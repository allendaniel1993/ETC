/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package etc;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

/**
 *
 * @author user
 */
public class ImageFusion {

    private static String[] openFile(String file) throws Exception {
        InputStream in = new FileInputStream(new File(file));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line).append(" ");

        }
        reader.close();
        String[] s = (out.toString().split(" "));

        return s;
    }

    private static double[] fusion(String[] s1, String[] s2) {
        String[] s = new String[s1.length];
        double[] d = new double[s1.length];
        for (int i = 0; i < s1.length; i++) {
            s[i] = (0.5 * ((Integer.parseInt(s2[i]) - Integer.parseInt(s1[i])) + Integer.parseInt(s1[i]))) + "";
            d[i] = Math.ceil(Double.parseDouble(s[i]));
        }
        return d;
    }

    private static void printOnConsole(double[] a, double[] r, double[] g, double[] b) {
        System.out.println("printing");
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i] + " " + r[i] + " " + g[i] + " " + b[i]);
        }
    }

    private static double[] createPixelArray(double[] alpha, double[] red, double[] green, double[] blue) {
        double[] value = new double[alpha.length];
        for (int i = 0; i < alpha.length; i++) {
            value[i] = ((int) alpha[i] << 24) | ((int) red[i] << 16) | ((int) green[i] << 8) | ((int) blue[i]);
        }
        return value;
    }

    private static double[][] convert1DTo2D(double[] value) {
        double pixels[][] = new double[512][512];
        int k = 0;
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels.length; j++) {
                pixels[i][j] = value[k];
                k++;
               // System.out.println(pixels[i][j]);
            }
        }
        return pixels;
    }

    private static void createImage(int imageHeight, int imageWidth, double[][] pixels) throws Exception {
        // Let's create a BufferedImage for a gray level image.
        BufferedImage image = new BufferedImage(imageHeight, imageWidth, BufferedImage.TYPE_BYTE_GRAY);
        // We need its raster to set the pixels' values.
        WritableRaster wr = image.getRaster();
        Graphics2D g2d = image.createGraphics();
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                wr.setSample(j, i, 0, pixels[i][j]);
            }
        }

        image.setData(wr);
     //   g2d.drawImage(image, 0, 0, null);
        ImageIO.write(image, "jpg", new File("A:\\ETC\\test images\\standard test images", "fused.jpg"));

        System.out.println("image is created..");
    }

    public static void main(String[] foo) {
        try {
            //read pixels from BI
            String s11 = "a:\\etc\\test images\\input image1\\alphapixelvalue.txt";
            String s12 = "a:\\etc\\test images\\input image1\\redpixelvalue.txt";
            String s13 = "a:\\etc\\test images\\input image1\\greenpixelvalue.txt";
            String s14 = "a:\\etc\\test images\\input image1\\bluepixelvalue.txt";
            // printPixelARGB(pixels); // print pixels on console

            String[] alpha1 = openFile(s11);
            String[] red1 = openFile(s12);
            String[] green1 = openFile(s13);
            String[] blue1 = openFile(s14);

            String s21 = "a:\\etc\\test images\\input image2\\alphapixelvalue.txt";
            String s22 = "a:\\etc\\test images\\input image2\\redpixelvalue.txt";
            String s23 = "a:\\etc\\test images\\input image2\\greenpixelvalue.txt";
            String s24 = "a:\\etc\\test images\\input image2\\bluepixelvalue.txt";

            String[] alpha2 = openFile(s21);
            String[] red2 = openFile(s22);
            String[] green2 = openFile(s23);
            String[] blue2 = openFile(s24);

            double[] alpha = fusion(alpha1, alpha2);
            double[] red = fusion(red1, red2);
            double[] green = fusion(green1, green2);
            double[] blue = fusion(blue1, blue2);

            // printOnConsole(alpha, red, green, blue);
            double pixels1D[] = createPixelArray(alpha, red, green, blue);
            double[][] pixels = convert1DTo2D(pixels1D);
            createImage(512, 512, pixels);

        } catch (Exception e) {
        }
    }
}
