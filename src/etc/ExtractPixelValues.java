/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package etc;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class ExtractPixelValues extends Component {

    private static int[] marchThroughForPixels(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        System.out.println("width, height: " + w + ", " + h);
        int k = 0;
        int[] pixels = new int[w * h];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int value = image.getRGB(j, i);
                pixels[k] = value;
                k++;
            }
        }
        return pixels;
    }

    private static void writeAlphaToFile(File file, int[] pixels) throws Exception {
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pixels.length; i++) {
            sb.append((pixels[i] >> 24) & 0xff).append(" ");
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(sb.toString());
        bw.close();
        System.out.println("Done");
    }

    private static void writeRedToFile(File file, int[] pixels) throws Exception {
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pixels.length; i++) {
            sb.append((pixels[i] >> 16) & 0xff).append(" ");
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(sb.toString());
        bw.close();
        System.out.println("Done");

    }

    private static void writeGreenToFile(File file, int[] pixels) throws Exception {
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pixels.length; i++) {
            sb.append((pixels[i] >> 8) & 0xff).append(" ");
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(sb.toString());
        bw.close();
        System.out.println("Done");

    }

    private static void writeBlueToFile(File file, int[] pixels) throws Exception {
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pixels.length; i++) {
            sb.append(pixels[i] & 0xff).append(" ");
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(sb.toString());
        bw.close();
        System.out.println("Done");
    }

    private static void delete(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile() && f.exists()) {
                f.delete();
                // System.out.println("successfully deleted");
            } else {
                System.out.println("cant delete a file due to open or error");
            }
        }
    }

    private static void deleteFiles() {
        delete("a:\\etc\\test images\\input image1");
        delete("a:\\etc\\test images\\input image1");
        delete("a:\\etc\\test images\\input image1");
        delete("a:\\etc\\test images\\input image1");

        delete("a:\\etc\\test images\\input image2");
        delete("a:\\etc\\test images\\input image2");
        delete("a:\\etc\\test images\\input image2");
        delete("a:\\etc\\test images\\input image2");
    }

    private static String[] openFileToPrint(String file) throws Exception {
        InputStream in = new FileInputStream(new File(file));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line).append(" ");
        }
        reader.close();
        return out.toString().split(" ");
    }

    private static void printOnConsole(String[] a, String[] r, String[] g, String[] b) {

        System.out.println("printing");
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i] + " " + r[i] + " " + g[i] + " " + b[i]);
        }
    }

    public static void main(String[] foo) {
        try {

            System.out.println("Cleaning directory..");
            deleteFiles(); // clean the directory

            String inputimage1 = "A:\\ETC\\test images\\standard test images\\input1.jpg";
            BufferedImage image1 = ImageIO.read(new File(inputimage1));  // converting an image into bufferedimage
            int[] pixels1 = marchThroughForPixels(image1);    //read pixels from BI
            String s11 = "a:\\etc\\test images\\input image1\\alphapixelvalue.txt";
            writeAlphaToFile(new File(s11), pixels1); // write alpha channel into a file
            String s12 = "a:\\etc\\test images\\input image1\\redpixelvalue.txt";
            writeRedToFile(new File(s12), pixels1);   // write red channel into a file
            String s13 = "a:\\etc\\test images\\input image1\\greenpixelvalue.txt";
            writeGreenToFile(new File(s13), pixels1);  // write green channel into a file
            String s14 = "a:\\etc\\test images\\input image1\\bluepixelvalue.txt";
            writeBlueToFile(new File(s14), pixels1);  // write blue channel into a file
            // printPixelARGB(pixels); // print pixels on console

            String[] alpha1 = openFileToPrint(s11);
            String[] red1 = openFileToPrint(s12);
            String[] green1 = openFileToPrint(s13);
            String[] blue1 = openFileToPrint(s14);
            printOnConsole(alpha1, red1, green1, blue1);

            String inputimage2 = "A:\\ETC\\test images\\standard test images\\input1.jpg";
            BufferedImage image2 = ImageIO.read(new File(inputimage2));  // converting an image into bufferedimage
            int[] pixels2 = marchThroughForPixels(image2);    //read pixels from BI
            String s21 = "a:\\etc\\test images\\input image2\\alphapixelvalue.txt";
            writeAlphaToFile(new File(s21), pixels2); // write alpha channel into a file
            String s22 = "a:\\etc\\test images\\input image2\\redpixelvalue.txt";
            writeRedToFile(new File(s22), pixels2);   // write red channel into a file
            String s23 = "a:\\etc\\test images\\input image2\\greenpixelvalue.txt";
            writeGreenToFile(new File(s23), pixels2);  // write green channel into a file
            String s24 = "a:\\etc\\test images\\input image2\\bluepixelvalue.txt";
            writeBlueToFile(new File(s24), pixels2);  // write blue channel into a file
            // printPixelARGB(pixels); // print pixels on console

            String[] alpha2 = openFileToPrint(s21);
            String[] red2 = openFileToPrint(s22);
            String[] green2 = openFileToPrint(s23);
            String[] blue2 = openFileToPrint(s24);
            printOnConsole(alpha2, red2, green2, blue2);

        } catch (Exception e) {
        }
    }
}
