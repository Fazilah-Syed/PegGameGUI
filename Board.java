package PegGameGUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * 
 * This class reads a file and return a value which specifies the dimensions
 * of the board in which the game is played.
 */
public class Board {
    private static int lineOne; // declaring an attribute lineOne
    public static String filename; // declaring an attribute filename

    /**
     * Mutator for the filename
     * 
     * @param file sets the file taken as an argument into the filename field of
     *             this class
     */
    public static void setFilename(String file) {
        filename = file;
    }

    /**
     * Accessor for the filename
     * 
     * @return filename
     */
    public static String getFilename() {
        return filename;
    }

    /**
     * Creating a method to read data from the file
     * 
     * @return an integer value of the size of the board
     */
    public static int readingFile(String filename) {
        // Scanner s = new Scanner(System.in);
        // System.out.println("Enter the file name: ");
        // filename = s.nextLine();
        // s.close();
        try { 
            FileReader fr = new FileReader(filename); // creating
                                                                                                                                            // a
                                                                                                                                            // FileReader
                                                                                                                                            // object
                                                                                                                                            // to
                                                                                                                                            // read
                                                                                                                                            // from
                                                                                                                                            // a
                                                                                                                                            // specific
                                                                                                                                            // file
                                                                                                                                            // location
            // Note:please give complete absolute path above
            BufferedReader reader = new BufferedReader(fr); // creating a BufferedReader object to continue reading each
                                                            // character of the line in a flow
            String line = ""; // declaring a String variable line
            while (true) {
                line = reader.readLine(); // reading from the file and storing it into line
                if (line == null) {
                    break; // if the line is empty, break out of the loop
                }
                // Splitting the line and storing it into words
                String[] words = line.split(" ");
                // converting the first String value of index 0 as an integer and storing it in
                // lineOne
                Board.lineOne = Integer.parseInt(words[0]);

                break;
            }
            reader.close(); // closing BufferedReader
            fr.close(); // closing FleReader
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Board.lineOne; // returning lineOne

    }

    /**
     * Main method to test the above method manually
     * 
     * @param args
     */
    public static void main(String[] args) {
        // System.out.println(readingFile());
        
    }

}
