/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package etc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class PermutationGeneration {

    private static final String FILEPATH = "A:\\ETC\\test images\\NumberCombination.txt";
    public List<String> list = new ArrayList<String>();

    public PermutationGeneration() {
        //Permutation without repetition
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(i).append(" ");
        }
        String[] set = sb.toString().split(" ");
        FindPermutations("", set);
        useByfferedFileWriter(list, FILEPATH);

    }

    public static void main(String[] args) {
        new PermutationGeneration();
    }

    /**
     * Write a big list of Strings to a file - Use a BufferedWriter
     */
    public static void useByfferedFileWriter(List<String> content,
            String filePath) {

        File file = new File(filePath);
        Writer fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {

            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);

            // Write the lines one by one
            for (String line : content) {
                line += System.getProperty("line.separator");
                bufferedWriter.write(line);

                // alternatively add bufferedWriter.newLine() to change line
            }

        } catch (IOException e) {
            System.err.println("Error writing the file : ");
            e.printStackTrace();
        } finally {

            if (bufferedWriter != null && fileWriter != null) {
                try {
                    bufferedWriter.close();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

     private void FindPermutations(String permutation, String[] set) {
        if (set.length == 1) {
            list.add(permutation + set[0]);
        }

        for (int i = 0; i < set.length; i++) {
            String n = set[i];
            String newPermutation = permutation + n + " ";
            String[] subset = new String[set.length - 1];
            int j = 0;
            for (int k = 0; k < set.length; k++) {
                if (set[k] != n) {
                    subset[j++] = set[k];
                }
            }
            FindPermutations(newPermutation, subset);
        }
    }
}
