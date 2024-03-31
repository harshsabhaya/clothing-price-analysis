package org.clothing;

import org.clothing.scraper.StoreDataInFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEndOfWord;

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}

public class WordCompletion {
    private static final String[] CSV_FILE_PATHS = {
            StoreDataInFile.getFilePath("Ajio.csv"),
            StoreDataInFile.getFilePath("Myntra.csv"),
            StoreDataInFile.getFilePath("Flipkart.csv")
    };
    private TrieNode root;
    private List<Map<String, Integer>> wordFrequencyMaps;


    public WordCompletion() {
        root = new TrieNode();
        wordFrequencyMaps = new ArrayList<>();
    }

    public static void wordCompletion(Scanner scanner) {
        WordCompletion wordCompletion = new WordCompletion();
        wordCompletion.buildWordFrequencyMaps();
        wordCompletion.handleUserInput(scanner);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        wordCompletion(scanner);
        scanner.close();
    }

    public void buildWordFrequencyMaps() {
        for (String csvFileName : CSV_FILE_PATHS) {
            Map<String, Integer> wordFrequencyMap = new HashMap<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(","); // Assuming CSV structure: productId,productName,productPrice,...

                    for (String item : data) {
                        String[] words = item.trim().split("\\s+");

                        for (String word : words) {
                            if (!word.isEmpty()) { // Ignore empty words
                                wordFrequencyMap.put(word.toLowerCase(), wordFrequencyMap.getOrDefault(word.toLowerCase(), 0) + 1);
                                insertWord(word.toLowerCase()); // Build Trie as well
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading CSV file: " + e.getMessage());
            }
            wordFrequencyMaps.add(wordFrequencyMap);
        }
    }

    private void insertWord(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
    }

    public List<String> findWordCompletions(String partialWord) {
        List<String> completions = new ArrayList<>();
        TrieNode current = root;

        // Traverse to the node corresponding to the last character of partialWord
        for (char c : partialWord.toCharArray()) {
            if (current.children.containsKey(c)) {
                current = current.children.get(c);
            } else {
                return completions; // No completions found
            }
        }

        findAllCompletions(current, partialWord, completions);
        Collections.sort(completions); // Sort completions alphabetically
        return completions;
    }

    private void findAllCompletions(TrieNode node, String prefix, List<String> completions) {
        if (node.isEndOfWord) {
            completions.add(prefix);
        }

        for (char c : node.children.keySet()) {
            findAllCompletions(node.children.get(c), prefix + c, completions);
        }
    }

    public void handleUserInput(Scanner scanner) {

        // Define a regular expression pattern to match word characters
        String wordPattern = "^[a-zA-Z]+$";

        System.out.print("Enter partial word for completion: ");
        String partialWord = scanner.nextLine().trim().toLowerCase(); // Convert to lowercase for case-insensitive matching

        // Validate user input against the word pattern
        if (!partialWord.matches(wordPattern)) {
            System.out.println("Invalid input! Only word characters are allowed.");
        } else {
            List<String> completions = findWordCompletions(partialWord);
            if (completions.isEmpty()) {
                System.out.println("No completions found.");
            } else {
                System.out.println("Word Completions:");
                for (String completion : completions) {
                    System.out.println(completion);
                }
            }
        }

    }
}
