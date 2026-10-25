import java.io.File;
import java.util.Scanner;

public class AmazonReviewDatasetAnalyzer {

    public static void main(String[] args) {
        String filePath = "Reviews.csv";
        analyzeDatasetFromFile(filePath);
    }

    public static void analyzeDatasetFromFile(String path) {
        long longTotalScore = 0;
        int longCount = 0;
        long shortTotalScore = 0;
        int shortCount = 0;

        try {
            File file = new File(path);
            Scanner fileScanner = new Scanner(file);

            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

                if (parts.length >= 10) {
                    int starScore = Integer.parseInt(parts[6].trim());
                    String reviewText = parts[9].trim();

                    int wordCount = 0;
                    int j = 0;
                    while (j < reviewText.length()) {
                        if (reviewText.charAt(j) == ' ') {
                            wordCount++;
                        }
                        j++;
                    }
                    wordCount++;

                    if (wordCount > 10) {
                        longTotalScore += starScore;
                        longCount++;
                    } else {
                        shortTotalScore += starScore;
                        shortCount++;
                    }
                }
            }
            fileScanner.close();

            double longAvg = longCount > 0 ? (double) longTotalScore / longCount : 0.0;
            double shortAvg = shortCount > 0 ? (double) shortTotalScore / shortCount : 0.0;

            System.out.println("Dataset Analysis Results:");
            System.out.println("Long Reviews Average Score: " + longAvg);
            System.out.println("Short Reviews Average Score: " + shortAvg);

            if (longAvg < shortAvg) {
                System.out.println("Conclusion: Longer reviews tend to have lower (more negative) scores.");
            } else {
                System.out.println("Conclusion: Longer reviews do not necessarily tend to be more negative.");
            }

        } catch (Exception e) {
            System.out.println("Error reading or parsing the dataset file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}