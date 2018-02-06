package etc;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author sankar
 */
public class Chaos {

    double alpha;
    double beta;
    double d1, d2, d3, d4;
    static JFrame frame = new JFrame();
    static JPanel panel1 = new JPanel();
    static JPanel panel2 = new JPanel();
    static JPanel panel3 = new JPanel();
    static JPanel panel4 = new JPanel();
    static JPanel panel5 = new JPanel();
    static JPanel panel6 = new JPanel();
   // static List<String> list = new PermutationGeneration().list;

    /* set initial values */
    public Chaos() {
        //   x[0] = 0.987654321012345;
        alpha = 1.1;
        beta = 5.0;
        d1 = 1 - Math.pow(beta, -4);
        d2 = 1.0 / Math.tan(alpha / (1 + beta));
        d3 = Math.pow((1 + (1 / beta)), beta);

     //   String[][] permut = new String[list.size()][8];
       // for (int i = 0; i < list.size(); i++) {
         //   String[] t = list.get(i).split(" ");
           // System.arraycopy(t, 0, permut[i], 0, 8);
       // }


        frame.setLayout(new GridLayout(1, 1));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Actual Image", panel1);

        tabbedPane.addTab("Pixel Values of Actual Image", panel2);
        tabbedPane.addTab("NCP", panel3);
        tabbedPane.addTab("XOR Operation", panel4);
        tabbedPane.addTab("Enoded Image", panel5);
        tabbedPane.addTab("Reconstructed Image", panel6);
        tabbedPane.setSelectedIndex(0);
        frame.getContentPane().add(tabbedPane);

        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth() - 100);
        int ySize = ((int) tk.getScreenSize().getHeight() - 100);
        frame.setSize(xSize, ySize);
       // frame.setTitle("Non Linear Chaotic System: S. Sankar and Dr. S Nagarajan");
        frame.setVisible(true);
    }

    public String[] calculateNCAMap(int numberofiterations) throws Exception {
        double[] x = new double[numberofiterations];
        x[0] = 0.987654321012345;   //value of  x[0] is assinged here
        String[] ncp = new String[numberofiterations];
        String s = "";
        ncp[0] = "" + x[0];
        //  System.out.println("j="+0+" "+ncp[0]);
        for (int i = 0, j = 1; i < numberofiterations - 1; i++, j++) {
            x[i + 1] = d1 * d2 * d3 * Math.tan(alpha * x[i]) * Math.pow((1 - x[i]), beta);
            s = "" + new BigDecimal((x[i + 1]));
            ncp[j] = s.replace(".", "").substring(0, 15);
            //  System.out.println("j=" + j + " " + ncp[j]);
        }
        // System.out.println();
        return ncp;
    }

    public int split(String ncp) {
        int[] decimals = new int[5];
        String[] s = (ncp.split("(?<=\\G...)"));
        for (int j = 0; j < s.length; j++) {
            // System.out.print(s[j]+"\t");
            decimals[j] = Integer.parseInt(s[j]);
            //  System.out.print(decimals[j] + "\t");
        }
        //  System.out.println();
        return decimals[4];
    }

    public String convertToBinaryFormat(int number) {
        int binary[] = new int[8];
        int index = 0;
        String s = "";
        while (number > 0) {
            binary[index++] = number % 2;
            number = number / 2;
        }
        for (int i = binary.length - 1; i >= 0; i--) {
            //System.out.print(binary[i]);
            s = s + binary[i];
        }
        return s;
    }

    public int doModulus(int decimals) {
        return decimals % 256;
    }

    public int convertBinaryToDecimals(String s) {
        int deci = 0;
        int binary = Integer.parseInt(s);
        deci = 0;
        int p = 1;
        int rem = 0;
        while (binary > 0) {
            rem = binary % 10;
            deci = deci + (rem * p);
            p = p * 2;
            binary = binary / 10;
        }
        return deci;
    }

    public int[] convertToIntArray(String st[]) {
        int[] intarray = new int[st.length];
        for (int i = 0; i < st.length; i++) {
            intarray[i] = Integer.parseInt(st[i]);
            //   System.out.println(intarray[i]);
        }
        return intarray;
    }

    public static int[][] convert1DTo2D(int[] value, int height, int width) throws Exception {
        System.out.println("number of values" + value.length);
        int pixels[][] = new int[height][width];
        int k = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixels[i][j] = value[k];
                k++;
                //   System.out.println(pixels[i][j] + " ");
            }
            //System.out.println();
        }
        return pixels;
    }

    public int[] convert2Dto1D(int[][] PixelArray) {
        int[] pixeldata = new int[(PixelArray.length * PixelArray[0].length)];
        int cnt = 0;
        for (int i = 0; i < PixelArray.length; i++) {
            for (int j = 0; j < PixelArray[0].length; j++) {
                pixeldata[cnt] = PixelArray[i][j];
                cnt++;
            }
        }
        return pixeldata;
    }

    public static void setUIFont(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }

   /* public void inverseTrans(int tr, String permut) {
        String pixelinbinary=convertToBinaryFormat(tr);
         System.out.println("binary of permuted pixel:" + pixelinbinary);
         System.out.println("binary of permuted data:" + permut);
         String[] bin = new String[8];
        System.out.println("Before inverse permutation");
        for (int i = 0; i < pixelinbinary.length(); i++) {
            bin[i] = pixelinbinary.charAt(i) + "";
            System.out.print(bin[i]);
        }
        System.out.println();
        String[] perm = permut.split(" ");
        String[] permutedpixel = new String[perm.length];
        System.out.println("After inverse permutation");
        for (int i = 0; i < perm.length; i++) {
            bin[i] = perm[Integer.parseInt(perm[i])];
            System.out.print(permutedpixel[i]);
            tr = convertBinaryToDecimals(permutedpixel[i]);
            System.out.println("Permuted pixel in deciaml:" + tr);

        }
    }

    public int trans(String pixelinbinary, String permut) {
        int tr = 0;
        System.out.println("permtation:" + permut);
        System.out.println("binary of pixel data:" + pixelinbinary);
        String[] bin = new String[8];
        System.out.println("Before permutation");
        for (int i = 0; i < pixelinbinary.length(); i++) {
            bin[i] = pixelinbinary.charAt(i) + "";
            System.out.print(bin[i]);
        }
        System.out.println();
        String[] perm = permut.split(" ");
        String[] permutedpixel = new String[perm.length];
        System.out.println("After permutation");
        for (int i = 0; i < perm.length; i++) {
            permutedpixel[i] = bin[Integer.parseInt(perm[i])];
            System.out.print(permutedpixel[i]);
            tr = convertBinaryToDecimals(permutedpixel[i]);
            System.out.println("Permuted pixel in deciaml:" + tr);

        }
        return tr;
    }*/

    public static void main(String sr[]) throws Exception {
        Chaos c = new Chaos();

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        setUIFont(new FontUIResource(new Font("Verdana", 0, 12)));

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to open");

        fileChooser.showOpenDialog(frame);
        File fileToOpen = fileChooser.getSelectedFile();
        System.out.println("file: " + fileToOpen.getAbsolutePath());

        BufferedImage image = ImageIO.read(fileToOpen);

        ImageIcon im1 = new ImageIcon(image);
        JLabel imageLabel1 = new JLabel(im1);
        panel1.add(imageLabel1);
        frame.validate();
        frame.repaint();

        int[][] pix = new ImageReadWrite().readImageData(image);
        JTextArea ta1 = new JTextArea();
        JScrollPane jsp1 = new JScrollPane(ta1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //  jsp.setSize(500, 500);

        for (int i = 0; i < pix.length; i++) {
            for (int j = 0; j < pix[0].length; j++) {
                ta1.append(pix[i][j] + "  ");
            }
            ta1.append("\n");
        }

        panel2.setLayout(new BorderLayout());
        panel2.add("Center", jsp1);
        frame.validate();
        frame.repaint();

        int[] imagepixels = c.convert2Dto1D(pix);
        System.out.println("No of pixels " + imagepixels.length);

        int numberofiterations = (imagepixels.length * 3) + 100;

        String[] ncps = c.calculateNCAMap(numberofiterations);     // calculating ncp for whole image size*3+100
        JTextArea ta2 = new JTextArea();
        JScrollPane jsp2 = new JScrollPane(ta2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        for (int i = 0; i < ncps.length; i++) {
            ta2.append("NCP[" + i + "]" + "\t" + ncps[i] + "\n");
        }

        panel3.setLayout(new BorderLayout());
        panel3.add("Center", jsp2);
        frame.validate();
        frame.repaint();

        int iteration = 100;

        StringBuilder sb1 = new StringBuilder();
        System.out.println("Enc");
        //  System.out.println("ncp1");
        for (int i = 0; i < imagepixels.length; i++) {
            iteration += 3;

            String ncp = ncps[(iteration - 1)];

            //System.out.println(ncp);

            int decimals = c.split(ncp);      // split ncp into 5 decimals
            int modulatednumber = c.doModulus(decimals);  // do modulation on each decimals
            //    System.out.println(modulatednumber);
            int encryptedpixel = imagepixels[i] ^ modulatednumber;
            System.out.println("im: " + imagepixels[i]);

            //Do Transposition for bit shuffle
           // c.trans(c.convertToBinaryFormat(encryptedpixel), list.get(modulatednumber));

            sb1.append(encryptedpixel).append("\n");    //XOR operation on each bit
        }
        
        JTextArea ta3 = new JTextArea();
        JScrollPane jsp3 = new JScrollPane(ta3, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        ta3.append(sb1.toString());
        panel4.setLayout(new BorderLayout());
        panel4.add("Center", jsp3);
        frame.validate();
        frame.repaint();

        String[] st = sb1.toString().split("\n");
        int[] encryptedimagedta = c.convertToIntArray(st);



        //Create encrypted image
        int[][] pix_enc_image = Chaos.convert1DTo2D(encryptedimagedta, image.getHeight(), image.getWidth());

        new ImageReadWrite().constructEncodedImage(image.getHeight(), image.getWidth(), pix_enc_image, "enc_image.jpg");

        BufferedImage buff = ImageIO.read(new File("A:\\ETC\\test images\\standard test images\\enc_image.jpg"));

        ImageIcon im2 = new ImageIcon(buff);
        JLabel imageLabel2 = new JLabel(im2);

        panel5.add(imageLabel2);

        StringBuilder sb2 = new StringBuilder();
        iteration = 100;

        System.out.println("Dec");
        for (int i = 0; i < encryptedimagedta.length; i++) {
        iteration += 3;

        String ncp = ncps[(iteration - 1)];
        //     System.out.println(ncp);

        int decimals = c.split(ncp);      // split ncp into 5 decimals

        int modulatednumber = c.doModulus(decimals);  // do modulation on each decimals
        // System.out.println(modulatednumber);
        sb2.append(encryptedimagedta[i] ^ modulatednumber).append(" ");    //XOR operation on each bit

        }

        String[] st1 = sb2.toString().split(" ");

        int[] decryptedimagedta = c.convertToIntArray(st1);
        int[][] rawdata1 = Chaos.convert1DTo2D(decryptedimagedta, image.getHeight(), image.getWidth());
        new ImageReadWrite().reconstructImage(image.getHeight(), image.getWidth(), rawdata1, "saved.jpg");

        BufferedImage bi = ImageIO.read(new File("A:\\ETC\\test images\\standard test images\\saved.jpg"));
        ImageIcon im3 = new ImageIcon(bi);
        JLabel imageLabel3 = new JLabel(im3);

        panel6.add(imageLabel3);


        // Display values.



        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         

    }
}
