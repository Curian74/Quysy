package Utility;

public class QuizUtils {
    public static int calculateCorrectQuestions(double scorePercentage, int totalQuestions) {
        return (int) Math.round((scorePercentage / 10) * totalQuestions);
    }
}
