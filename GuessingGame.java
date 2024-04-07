import javax.swing.*;
import java.util.Random;

//Interface
interface NumberGuessingGame {
    void multipleRound();
}

//Class
class StartGame implements NumberGuessingGame {

    //randomNumber variable is hold the random number which is generated by the user.
    //And gussNumber variable is used to hold the user's guss number.
    int randomNumber, gussNumber;
    Random random = new Random();
    private int randomNumber() {
        return random.nextInt(1, 100);
    }

    //getGussNumber() method are used to take guss number from the user.
    private int getGussNumber() {
        int num = Integer.parseInt(JOptionPane.showInputDialog("Please Enter Guss Number between 1 to 100 \nEnter guss number: "));
        if(num >= 1 && num <= 100){
            return num;
        }
        return 0;
    }

    //GussNumberCheck() method are used to compare the guss number with generated by the computer.
    private void GussNumberCheck() {
        int attempt = 0;
        JOptionPane.showMessageDialog(null,"************You have 3 Attempts************");
        do{
            attempt++;
            String s1 = String.format("Attempt %d", attempt);
            JOptionPane.showMessageDialog(null,s1);
            randomNumber = randomNumber();
            gussNumber = getGussNumber();

            //Compare the guss number computer generated number.
            if(gussNumber == 0){
                JOptionPane.showMessageDialog(null,"You enter a guss number from out of range. Try Again");
            }
            else{

                if(randomNumber == gussNumber){
                    JOptionPane.showMessageDialog(null,"Guss is correct");
                } else if (randomNumber < gussNumber) {
                    JOptionPane.showMessageDialog(null,"Guss is too high, Try Again!");
                }else{
                    JOptionPane.showMessageDialog(null,"Guss is too low, Try Again!");
                }
            }

        }while(attempt < 3);
        JOptionPane.showMessageDialog(null,"You are losing the game!");
    }

    //multipleRound() method decide to limitation of attempts
    @Override
    public void multipleRound() {
        int choice;
        do{
            choice = Integer.parseInt(JOptionPane.showInputDialog("1.Play Game \n2.Exit \nEnter your choice: "));
            if (choice == 1) {
                GussNumberCheck();
            } else {
                JOptionPane.showMessageDialog(null,"Game Over");
            }
        }while(choice < 2);
    }
}
public class GuessingGame {

    //Main method
    public static void main(String[] args){
        NumberGuessingGame game = new StartGame();
        game.multipleRound();
    }
}