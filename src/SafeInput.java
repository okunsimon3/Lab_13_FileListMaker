import java.util.Scanner;

public class SafeInput {
    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString = ""; // Set this to zero length. Loop runs until it isn’t
        do {
            System.out.print("\n" + prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        } while (retString.length() == 0);
        return retString;
    }
    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return an int response that is not a String
     */
    public static int getInt(Scanner pipe, String prompt) {
        int ret = 0;
        boolean done = false;
        String trash = "";

        do {
            System.out.print("\n" + prompt + ": ");
            if(pipe.hasNextInt()) {
                ret = pipe.nextInt();
                pipe.nextLine();
                done = true;
            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter a valid number not " + trash);
            }
        }while(!done);
        return ret;
    }
    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a double response that is not a String
     */
    public static double getDouble(Scanner pipe, String prompt) {
        double ret = 0.0;
        boolean done = false;
        String trash = "";

        do {
            System.out.print("\n" + prompt + ": ");
            if(pipe.hasNextDouble()) {
                ret = pipe.nextDouble();
                pipe.nextLine();
                done = true;
            } else {
                trash = pipe.nextLine();
                System.out.println("Please enter a valid number not " + trash);
            }
        } while (!done);
        return ret;
    }
    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return an int response that is between the low and high numbers given
     */
    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int ret = 0;
        boolean done = false;
        String trash = "";

        do {
            System.out.print(prompt + "[" + low + " - " + high + "]: ");
            if (pipe.hasNextInt()) {
                ret = pipe.nextInt();
                pipe.nextLine();
                if (ret >= low && ret <= high) {
                    done = true;
                } else {
                    System.out.println("You must enter a value between " + low + " and " + high + ". Not " + ret);
                }

            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter a valid number not " + trash);
            }
        } while (!done);
        return ret;
    }
    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a double response that is between the low and high numbers given
     */
    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double ret = 0.0;
        boolean done = false;
        String trash = "";

        do {
            System.out.print(prompt + "[" + low + " - " + high + "]: ");
            if (pipe.hasNextDouble()) {
                ret = pipe.nextDouble();
                pipe.nextLine();
                if (ret >= low && ret <= high) {
                    done = true;
                } else {
                    System.out.println("You must enter a value between " + low + " and " + high + ". Not " + ret);
                }

            } else {
                trash = pipe.nextLine();
                System.out.println("You must enter a valid number not " + trash);
            }
        } while (!done);
        return ret;
    }
    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a boolean response that is true for Y or y and false for N or n
     */
    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String doneResponse = "";
        boolean done = false;
        String trash = "";

        do {
            System.out.print(prompt + "[Y/N]: ");
            doneResponse = pipe.nextLine();

            if(doneResponse.equalsIgnoreCase("N")||doneResponse.equalsIgnoreCase("Y")) {
                done = true;
            }
            else {
                trash = doneResponse;
                System.out.println("You must enter a valid answer not " + trash);
            }
        }while(!done);
        if(doneResponse.equalsIgnoreCase("N")) {
            done = false;
        } else {
            done = true;
        }
        return done;
    }
    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a string response that matches a RegEx pattern (SSN)
     */
    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String value = "";
        boolean done = false;

        do {
            System.out.print(prompt + " " + regEx + ": ");
            value = pipe.nextLine();
            if(value.matches(regEx)) {
                done = true;
            } else {
                System.out.println("\nInvalid input: " + value);
            }
        }while(!done);
        return value;
    }
    /**
     *
     * @param msg the message the user enters
     */
    public static void prettyHeader(String msg) {
        String message = msg;


        for(int row = 1; row <= 60; row++) {
            System.out.printf("%1s", "*");
        }
        System.out.println("\n");

        for(int row = 1; row <= 3; row ++) {
            System.out.printf("%1s", "*");
        }
        System.out.println(message);
        for(int row = 57; row <= 60; row ++) {
            System.out.printf("%1s", "*");
        }
        for(int row = 1; row <= 60; row++) {
            System.out.printf("%1s", "*");
        }


    }
}
