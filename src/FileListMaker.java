import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

public class FileListMaker {
    private static Scanner in = new Scanner(System.in);

    private static ArrayList<String> lines = new ArrayList<>();

    private static File curFile;

    private static String curFileName;

    private static String line = "";

    private static boolean isDirty = false; // Indicates that the current memo has not been saved


    public static void main(String[] args) {

        String menuPrompt = "O - Open  S - Save  A - Add  D - Delete  V - View  Q - Quit";
        String cmd = ""; // O, S, A, D, V, or Q
        boolean done = false;

        try {
            do {
                //Display Current List
                displayList();

                //Display command menu in the input prompt and get user cmd input
                cmd = SafeInput.getRegExString(in, menuPrompt, "[AaDdVvQqOoSs]");

                cmd = cmd.toUpperCase();

                //execute
                switch (cmd) {
                    case "A":
                        addItem();
                        isDirty = true;
                        break;
                    case "D":
                        deleteItem();
                        isDirty = true;
                        break;
                    case "V":
                        displayList();
                        break;
                    case "O":
                        if (isDirty) {
                            System.out.println("You are about to lose the current memo data.");
                            boolean saveYN = SafeInput.getYNConfirm(in, "Do you want to save the current memo?");

                            if (saveYN) {
                                saveFile(curFileName);
                            }
                        }
                        openFile();
                        isDirty = false;
                        break;
                    case "S":
                        if (!lines.isEmpty()) {
                            if (curFile == null)
                                curFileName = SafeInput.getNonZeroLenString(in, "Enter the name for the file you would like to save");
                            else
                                curFileName = curFile.getName();

                            saveFile(curFileName);
                            isDirty = false;
                        } else
                            System.out.println("Nothing to save!");
                        break;
                    case "Q":
                        quitList();
                        break;
                }

            } while (!done);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    } // End of Main Method



    private static void quitList() throws IOException {
        boolean quitYN = false;
        boolean saveYN = false;
        if(isDirty) {
            System.out.println("You are about to lose the data for your memo.");
            System.out.println("Save the file before you Quit");

            saveYN = SafeInput.getYNConfirm(in, "Save the file? ");
            if(saveYN) {
                saveFile(curFileName);
                System.out.println("File Saved, exiting...");
            }
            System.exit(0);
        }
        quitYN = SafeInput.getYNConfirm(in, "Are you sure you want to Quit?: ");

        if(quitYN) {
            System.exit(0);
        }
    }

    private static void addItem() {
        line = SafeInput.getNonZeroLenString(in, "Enter the new line to add to the memo");
        lines.add(line);
    }

    private static void deleteItem() {
        //get an item number for the item to delete from the user
        int itemToDelete = 0;
        int indexToDelete = 0;

        itemToDelete = SafeInput.getRangedInt(in, "Enter the number of the item you want to delete: ", 1, lines.size());
        //convert the item to an index
        itemToDelete = itemToDelete - 1;
        lines.remove(itemToDelete);
    }

    private static void openFile() throws IOException, FileNotFoundException {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path wd = workingDirectory.toPath();
        wd = Paths.get(workingDirectory + "\\src");
        workingDirectory = wd.toFile();

        // Typically, we want the user to pick the file so we use a file chooser
        // kind of ugly code to make the chooser work with NIO.
        // Because the chooser is part of Swing it should be thread safe.
        chooser.setCurrentDirectory(workingDirectory);
        // Using the chooser adds some complexity to the code.
        // we have to code the complete program within the conditional return of
        // the filechooser because the user can close it without picking a file

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            curFile = selectedFile;
            Path file = selectedFile.toPath();
            // Typical java pattern of inherited classes
            // we wrap a BufferedWriter around a lower level BufferedOutputStream
            InputStream in =
                    new BufferedInputStream(Files.newInputStream(file, CREATE));
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(in));

            // Clear any existing list items when you open a file
            lines.clear();

            // Finally we can read the file LOL!
            int line = 0;
            while (reader.ready()) {
                rec = reader.readLine();
                line++;
                lines.add(rec);
            }
            reader.close(); // must close the file to seal it and flush buffer
            System.out.println("\n\nData file read!");


        } else  // User closed the chooser without selecting a file
        {
            System.out.println("No file selected!!! ... exiting.\nRun the program again and select a file.");
        }
    }

    private static void saveFile(String fileName) throws IOException {
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + fileName);

        // Typical java pattern of inherited classes
        // we wrap a BufferedWriter around a lower level BufferedOutputStream
        OutputStream out =
                new BufferedOutputStream(Files.newOutputStream(file, CREATE));
        BufferedWriter writer =
                new BufferedWriter(new OutputStreamWriter(out));

        if(curFile == null)
            curFile = file.toFile();

        // Finally can write the file LOL!

        for(String rec : lines)
        {
            writer.write(rec, 0, rec.length());  // stupid syntax for write rec
            // 0 is where to start (1st char) the write
            // rec. length() is how many chars to write (all)
            writer.newLine();  // adds the new line
            System.out.println("Writing file data: " + rec);

        }
        writer.close(); // must close the file to seal it and flush buffer
        System.out.println("Data file written: " + curFile.getName());
    }

    private static void displayList() {
        System.out.println("==========================================");
        if(lines.size() > 0) {
            int itemNum = 1;
            for(String l:lines) {
                System.out.println(itemNum + ". " + l);
                itemNum++;
            }
        } else {
            System.out.println("The list is currently empty!");
        }
        System.out.println("==========================================");
    }

}
