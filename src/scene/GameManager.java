package scene;

import java.util.ArrayList;
import java.util.Random;

import application.Main;



public class GameManager {


    private final ArrayList<String> words = new ArrayList<>(); // Hold the list of words that the user still needs to find
    private final ArrayList<Character> wordSelect = new ArrayList<>(); // Holds a list of selected characters
    private char[][] map; // Becomes the matrix, and is initialized with a size from WordSearch.java
    private int matrixSize; // Holds the size of the board
    private String input = ""; // Stores the word that the user selects
    private int oldRow = -1; // Set to the row of the first letter of wordSelect
    private int oldCol = -1; // Set to the column of the first letter of wordSelect

    final static String[] wordLibrary = { "HAPPY", "MOTIVATED", "CONFIDENT", "POSITIVE", "ENERGETIC",
    		"PERSISTENT", "DISCIPLINED", "PROACTIVE", "GRACIOUS", "HONEST", "RELIABLE", "RESPECTFUL", 
    		"HUMBLE", "GRATEFUL", "ADAPTABLE", "EMPATHETIC", "DILIGENT", "VISIONARY", "CHARISMATIC", 
    		"MINDFUL", "RELIABLE", "BALANCED", "OPTIMISTIC", "KIND", "ENGAGED", "DRIVEN", "COURAGEOUS",
    		"GENUINE", "HUMBLE", "EMPOWERED"}; // Library of words that are possible to be hidden
    
    
    //Start the game  with the size depending on difficulty selected.

    public void initMap(Main.Level level) {
        int sizeEazy = 10;
        int sizeMedium = 13;
        int sizeHard = 16;
        int wordEazy = 2;
        int wordMedium = 3;
        int wordHard = 6;
        switch (level) {
        case LEVEL1:
            matrixSize = sizeEazy;
            generateWords(wordEazy); // Sets wordList to 4 random words
            startMatrix(matrixSize); // Starts matrix with size of 15x15
            break;
        case LEVEL2:
            matrixSize = sizeMedium;
            generateWords(wordMedium); // Sets wordList to 8 random words
            startMatrix(matrixSize); // Starts matrix with size of 20x20
            break;
        case LEVEL3:
            matrixSize = sizeHard;
            generateWords(wordHard); // Sets wordList to 12 random words
            startMatrix(matrixSize); // Starts matrix with size of 25x25
            break;
        }
        generateMatrix(); // Places the words on the board
        fillBlanks(); // Randomizes unfilled positions to a random Char
        // printmatrix();
    }
    
    
    //Initializes the matrix 
    public void startMatrix(int size) {
        map = new char[size][size];
    }
    
    
    /**
     * Places the words in wordList in the board. Runs checks so that all positions
     * are valid and not overwriting another word
     */
    public void generateMatrix() {
        Random rand = new Random(); // Random value generator
        int modifier; // Changes the location that is currently being written to
        int orientation, randCol, randRow; // These store the values of the orientaion, the column of the first letter,
                                           // and the row of the first letter respectively
        boolean added; // Stores whether ir not a given word has been added

        for (String word : words) {
            do {
                added = false;
                orientation = rand.nextInt(4);
                randRow = rand.nextInt(matrixSize);
                randCol = rand.nextInt(matrixSize);
                if (preventOutOfBound(orientation, randRow, randCol, word.length())) {
                    if (preventOverlap(map, orientation, randRow, randCol, word.length())) {
                        for (int j = 0; j < word.length(); j++) {
                            modifier = j; // Increments the position that is currently being written to
                            switch (orientation) {
                                case 0: // If orientation is verical up
                                    map[randRow - modifier][randCol] = word.charAt(modifier);
                                    break;
                                case 1: // If orientation is horizontal right
                                    map[randRow][randCol + modifier] = word.charAt(modifier);
                                    break;
                                case 2: // If orientation is verical down
                                    map[randRow + modifier][randCol] = word.charAt(modifier);
                                    break;
                                case 3: // If orientation is horizontal left
                                    map[randRow][randCol - modifier] = word.charAt(modifier);
                                    break;
                            }
                            added = true;
                        }
                    }
                }
            } while (!added); // Loop until word is added
        }
    }

    public char getCharPos(int row, int col) {
        return map[row][col];
    }
    
    public void generateWords(int count) {
        Random rand = new Random();
        words.clear(); // Clears the word list
        int wordsAdded = 0; // Tracks how many words have been added already
        while (wordsAdded < count) {
            int randomVal = rand.nextInt(wordLibrary.length);

            if (!words.contains(wordLibrary[randomVal])) { // If word isn't in wordList yet
                words.add(wordLibrary[randomVal]);
                wordsAdded++;
            }
        }
    }
    
    //prevent the word was positioned out of the matrix array
    public boolean preventOutOfBound(int orientation, int row, int col, int length) {
        boolean inbounds = false;

        switch (orientation) {
        case 0:
            if ((row + 1) - length >= 0) {
                inbounds = true;
            }
            break;
        case 1:
            if (col + length <= matrixSize) {
                inbounds = true;
            }
            break;
        case 2:
            if ((row - 1) + length < matrixSize) {
                inbounds = true;
            }
            break;
        case 3:
            if ((col + 1) - length >= 0) {
                inbounds = true;
            }
        }

        return inbounds;
    }

    //prevent word positioning in the already-written words
    public boolean preventOverlap(char[][] matrix, int orientation, int row, int col, int length) {
        boolean checker = true;
        int modifier;

        for (int i = 0; i < length; i++) {
            modifier = i;
            switch (orientation) {
            case 0:
                if (matrix[row - modifier][col] != 0) {
                    checker = false;
                }
                break;
            case 1:
                if (matrix[row][col + modifier] != 0) {
                    checker = false;
                }
                break;
            case 2:
                if (matrix[row + modifier][col] != 0) {
                    checker = false;
                }
                break;
            case 3:
                if (matrix[row][col - modifier] != 0) {
                    checker = false;
                }
                break;
            }
        }
        return checker;
    }
    
    
    //generate random letters for left cells
    public void fillBlanks() {
        Random randChar = new Random(); 
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (map[i][j] == 0) {
                    // Conatins all possible random letters
                    String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    map[i][j] = ALPHABET.charAt(randChar.nextInt(ALPHABET.length()));
                }
            }
        }
    }
    
    public String getInput() {
        return input;
    }
    
    public int getWordListSize() {
        return words.size();
    }
    
    public void deleteWordListVal(int pos) {
        words.remove(pos);
    }

    //get letter at certain position
    public String getWordListValue(int pos) {
        return words.get(pos);
    }
    
    public void checkSamePos(int rowSelection, int colSelection) {
        if (input.length() == 0) {
            oldCol = colSelection;
            oldRow = rowSelection;
        } else if (!(oldRow == rowSelection || oldCol == colSelection)) {
            clearWordSelect();
        }
    }

    public void clearWordSelect() {
        wordSelect.clear();
    }
    
    //Convert wordSelect ArrayList to a string 
    public void wordSelectedToString() {
        input = "";
        for (int i = 0; i < getWordSelectSize(); i++) {
        	input = input.concat(String.valueOf(getWordSelectValue(i)));
        }
    }

    
    public void deleteLastLetter() {
        if (wordSelect.size() > 0) {
            wordSelect.remove(wordSelect.size()-1);
        }
    }

    
    public void addLetter(char val) {
        wordSelect.add(val);
    }

    
    public int getWordSelectSize() {
        return wordSelect.size();
    }

    
    public char getWordSelectValue(int pos) {
        return wordSelect.get(pos);
    }


    public int getMatrixSize() {
        return matrixSize;
    }


    
    //add letter to the list and check if it matches
    public void checkCurrentSelect(int rowSelection, int colSelection) {
        addLetter(getCharPos(rowSelection, colSelection));
        checkForFinishedWord(rowSelection, colSelection);
    }
    
    //convert the wordSelect ArrayList into string, checks if input matches
    //reset after match words
    public void checkForFinishedWord(int rowSelection, int colSelection) {
        checkSamePos(rowSelection, colSelection);
        wordSelectedToString();
        for (int i = 0; i < getWordListSize(); i++) {
            if (input.equals(getWordListValue(i))) {
                deleteWordListVal(i);
                clearWordSelect();
                input = "";
            }
        }
    }

}
