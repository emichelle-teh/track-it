package persistence;

import exceptions.LessThanZeroE;
import exceptions.StringLengthZero;
import model.Item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class Reader {
    public static final String DELIMITER = ",";

    //EFFECTS: returns a list of bought items parsed from a file;
    // throws IOException if an exception is raised when opening/reading from file
    public static List<Item> readItems(File file) throws IOException, LessThanZeroE, StringLengthZero {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    //EFFECTS: returns content of file as lists of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws  IOException {
        return Files.readAllLines(file.toPath());
    }

    //EFFECTS: returns a list of items parsed from a list of Strings
    // where each string contains data for one expense in the bought items list
    private static List<Item> parseContent(List<String> fileContent) throws LessThanZeroE, StringLengthZero {
        List<Item> items = new ArrayList<>();

        for (int i = 1; i < fileContent.size(); i++) {
            String line = fileContent.get(i);
            ArrayList<String> lineComponents = splitString(line);
            items.add(parseItem(lineComponents));
        }

        return items;
    }

    //EFFECTS: returns a budget parsed from a file
    // throws IOException if an exception is raised when opening/reading from file
    public static double readBudget(File file) throws IOException {
        List<String> budgetAmount = readFile(file);
        return parseBudget(budgetAmount);
    }

    //EFFECTS: returns a list of items parsed from a list of Strings
    // where each string contains data for one expense in the bought items list
    private static double parseBudget(List<String> fileContent) {
        String lineBudget = fileContent.get(0);
        double budgetAmount = Double.parseDouble(lineBudget);
        return budgetAmount;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }


    //EFFECTS: interprets list of stuff and makes it into an item
    private static Item parseItem(List<String> components) throws LessThanZeroE, StringLengthZero {
        String name = components.get(0);
        double price = Double.parseDouble(components.get(1));
        String category = components.get(2);
        return new Item(name, price, category);
    }



}
