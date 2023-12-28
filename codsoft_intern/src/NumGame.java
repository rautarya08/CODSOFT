import java.util.Random;
import java.util.Scanner;

public class NumGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int maxRange = 100; // Set the maximum range for the random number
        int attemptsLimit = 10; // Set the maximum number of attempts

        int score = 0;
        boolean playAgain = true;

        while (playAgain) {
            int numberToGuess = random.nextInt(maxRange) + 1;
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("Welcome to the Number Guessing Game!");
            System.out.println("Try to guess the number between 1 and " + maxRange);

            while (attempts < attemptsLimit) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess == numberToGuess) {
                    guessedCorrectly = true;
                    break;
                } else if (userGuess < numberToGuess) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (guessedCorrectly) {
                System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                score++;
            } else {
                System.out.println("Sorry! You ran out of attempts. The correct number was: " + numberToGuess);
            }

            System.out.print("Do you want to play again? (yes/no): ");
            String playAgainInput = scanner.next().toLowerCase();
            playAgain = playAgainInput.equals("yes");

            System.out.println("Your current score: " + score);
        }

        System.out.println("Thanks for playing!");
    }
}

