import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class quiz {

    private static final Scanner scanner = new Scanner(System.in);

    private static int score = 0;
    private static int currentQuestionIndex = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to the Quiz Application!");
        System.out.println("You will have 15 seconds to answer each question.\n");

        // Add your quiz questions here
        List<Question> questions = Arrays.asList(
                new Question("What is the capital of France?", Arrays.asList("A. London", "B. Paris", "C. Berlin"), "B"),
                new Question("Which planet is known as the Red Planet?", Arrays.asList("A. Venus", "B. Mars", "C. Jupiter"), "B"),
                // Add more questions as needed
                new Question("What is the largest mammal?", Arrays.asList("A. Elephant", "B. Blue Whale", "C. Giraffe"), "B")
        );

        ScheduledExecutorService timerExecutor = Executors.newScheduledThreadPool(1);

        for (Question question : questions) {
            displayQuestion(question);
            TimerTask timerTask = createTimerTask(question, timerExecutor);
            timerExecutor.schedule(timerTask, 15, TimeUnit.SECONDS);

            String userAnswer = getUserInput();
            timerTask.cancel(); // Cancel the timer task after user input

            if (question.isCorrect(userAnswer)) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Incorrect! The correct answer is: " + question.getCorrectAnswer() + "\n");
            }

            currentQuestionIndex++;
        }

        timerExecutor.shutdown(); // Shutdown the timerExecutor after all questions are answered
        displayResults(questions.size());
    }

    private static void displayQuestion(Question question) {
        System.out.println(question.getQuestion());
        for (String option : question.getOptions()) {
            System.out.println(option);
        }
    }

    private static TimerTask createTimerTask(Question question, ScheduledExecutorService timerExecutor) {
        return new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up! Moving to the next question.");
                currentQuestionIndex++;
                timerExecutor.shutdownNow(); // Stop the timerExecutor after the time is up
            }
        };
    }

    private static String getUserInput() {
        System.out.print("Your answer: ");
        return scanner.nextLine().toUpperCase(); // Convert to uppercase for case-insensitive comparison
    }

    private static void displayResults(int totalQuestions) {
        System.out.println("Quiz completed!");
        System.out.println("Your score: " + score + " out of " + totalQuestions);

        int correctAnswers = score;
        int incorrectAnswers = totalQuestions - score;

        System.out.println("Correct answers: " + correctAnswers);
        System.out.println("Incorrect answers: " + incorrectAnswers);
    }
}

class Question {
    private final String question;
    private final List<String> options;
    private final String correctAnswer;

    public Question(String question, List<String> options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer.toUpperCase(); // Convert to uppercase for case-insensitive comparison
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isCorrect(String userAnswer) {
        return correctAnswer.equals(userAnswer);
    }
}
