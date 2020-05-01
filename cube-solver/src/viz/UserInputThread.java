package viz;

import java.util.*;

public class UserInputThread extends Thread {
    public void run() {
        try {
            Scanner keyBoard = new Scanner(System.in);
            while(true){
                System.out.println("INPUT: ");
                String input = keyBoard.nextLine();
                System.out.println(input);
            }

        } catch (Exception exception) {
            System.out.println("Exception in UserInput thread: " + exception.getMessage());
        }
    }
}