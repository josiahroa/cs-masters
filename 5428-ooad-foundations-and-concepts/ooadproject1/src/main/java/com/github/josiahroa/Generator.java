package com.github.josiahroa;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.text.DecimalFormat;

/*
 * Josiah Roa
 * CSCA 5428 OOAD Foundations and Concepts Project 1
 * 8/28/2025
 */
public class Generator {
    // Example: class attribute
    // Define the width of the columns in the output for consistency in the display output
    private static final int COL_WIDTH = 10;
    
    public static void main(String[] args) {
        Generator g = new Generator();
        g.execute();
    }

    // Example: class definition
    // Provides a common interface for all random number generators to implement
    interface RandomNumberGenerator {
        // Example: method definition
        public double generateRandomNumber();
    }

    // Example: class definition
    // Random number generator that uses the Java.util.Random class
    class RandRandomNumberGenerator implements RandomNumberGenerator {
        // Example: method definition
        public double generateRandomNumber() {
            return new Random().nextDouble();
        }
    }

    // Example: class definition
    // Random number generator that uses the Math.random() method
    class MathRandomNumberGenerator implements RandomNumberGenerator {
        // Example: method definition
        public double generateRandomNumber() {
            return Math.random();
        }
    }

    // Example: class definition
    // Random number generator that uses the ThreadLocalRandom class
    class ThreadLocalRandomRandomNumberGenerator implements RandomNumberGenerator {
        // Example: method definition
        public double generateRandomNumber() {
            return ThreadLocalRandom.current().nextDouble();
        }
    }

    // Example: class definition
    /**
     * Factory class for creating random number generators based on the given generator id.
     * This is useful for adding new random number generators in the future without modifying the code.
     */
    class RandomNumberGeneratorFactory {
        // Example: method definition
        public RandomNumberGenerator getRandomNumberGenerator(int randNumGen) {
            // Example: instantiation
            if (randNumGen == 0) return new RandRandomNumberGenerator();
            // Example: instantiation
            if (randNumGen == 1) return new MathRandomNumberGenerator();
            // Example: instantiation
            if (randNumGen == 2) return new ThreadLocalRandomRandomNumberGenerator();
            return null;
        }
    }

    // Example: method definition
    /**
     * Creates an array list of n random numbers using a specified random number generator
     *
     * @param n the number of random numbers to generate
     * @param randNumGen the random number generator to use
     * @return an array list of n random numbers
     */
    ArrayList<Double> populate(int n, int randNumGen) {
        ArrayList<Double> randomValues = new ArrayList<>();
        // Example: instantiation
        RandomNumberGeneratorFactory factory = new RandomNumberGeneratorFactory();
        RandomNumberGenerator generator = factory.getRandomNumberGenerator(randNumGen);

        for (int i = 0; i < n; i++) {
            double rv = generator.generateRandomNumber();
            randomValues.add(rv);
        }

        return randomValues;
    }

    // Example: method definition
    // Example: accessibility
    /**
     * Calculates the mean of the randomValues provided
     *
     * @param randomValues
     * @return the mean of the randomValues
     */
    private double mean(ArrayList<Double> randomValues) {
        double sum = 0;
        for (double value : randomValues) {
            sum += value;
        }
        return sum / randomValues.size();
    }

    // Example: method definition
    // Example: accessibility
    /**
     * Calculates the sample standard deviation of the randomValues provided
     *
     * @param randomValues
     * @return the sample standard deviation of the randomValues
     */
    private double stddev(ArrayList<Double> randomValues) {
        double mean = mean(randomValues);
        double sum = 0;
        for (double value : randomValues) {
            sum += Math.pow(value - mean, 2);
        }
        return Math.sqrt(sum / randomValues.size());
    }

    // Example: method definition
    // Example: accessibility
    /**
     * Calculates the minimum of the randomValues provided
     *
     * @param randomValues
     * @return the minimum of the randomValues
     */
    private double min(ArrayList<Double> randomValues) {
        double min = randomValues.get(0);
        for (double value : randomValues) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    // Example: method definition
    // Example: accessibility
    /**
     * Calculates the maximum of the randomValues provided
     *
     * @param randomValues
     * @return the maximum of the randomValues
     */
    private double max(ArrayList<Double> randomValues) {
        double max = randomValues.get(0);
        for (double value : randomValues) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
 
    // Example: method definition
    /**
     * Calculates mean, sample standard deviation, minimum, and maximum
     * from the randomValues provided
     *
     * @param randomValues
     * @return an ArrayList with five results [n, mean, stddev, min, max]
     */
    ArrayList<Double> statistics(ArrayList<Double> randomValues) {
        ArrayList<Double> results = new ArrayList<>();
        results.add(0, (double)randomValues.size());
        results.add(1, mean(randomValues));
        results.add(2, stddev(randomValues));
        results.add(3, min(randomValues));
        results.add(4, max(randomValues));
        return results; 
    }

    // Example: method definition
    /**
     * Displays the results in the format from the project description
     * 
     * @param results
     * @param headerOn
     */
    void display(ArrayList<Double> results, boolean headerOn) {
        // Example: instantiation
        // Utilize DecimalFormat to reduce the number of decimal places to 4 to keep the output clean
        // https://docs.oracle.com/javase/8/docs/api/index.html?java/text/DecimalFormat.html
        DecimalFormat df = new DecimalFormat("#.####");

        double n = results.get(0);
        String mean = df.format(results.get(1));
        String stddev = df.format(results.get(2));
        String min = df.format(results.get(3));
        String max = df.format(results.get(4));
        System.out.printf("%-" + COL_WIDTH + "s %-" + COL_WIDTH + "s %-" + COL_WIDTH + "s %-" + COL_WIDTH + "s %-" + COL_WIDTH + "s\n", n, mean, stddev, min, max);
    }

    // calls populate, statistics, and display methods for each combination of n and
    // randomNumberGenerator â€“ a total of 9 results
    void execute() {
        String[] rm = { "Java.util.Random", "Math.random", "ThreadLocalRandom" };
        // select the random number generator
        for (int k = 0; k < 3; k++) {
            // select n
            for (int i = 1; i < 4; i++) {
                int n = (int) Math.pow(10.0, (double) i);
                ArrayList<Double> rv = populate(n, k);    // calls your populate method
                ArrayList<Double> res = statistics(rv);   // calls your statistics method
                boolean headerOn = false;
                if (i == 1) {
                    headerOn = true;
                    System.out.println("From " + rm[k]);
                    System.out.printf("%-" + COL_WIDTH + "s %-" + COL_WIDTH + "s %-" + COL_WIDTH + "s %-" + COL_WIDTH + "s %-" + COL_WIDTH + "s\n", "n", "mean", "stddev", "min", "max");
                }
                display(res, headerOn); // calls your display method
            }
        }
    }
}
