package org.example;

public class Player {
    private String name;
    private String letterChoice;
    private int sosCount;

    public Player(String name, String letterChoice) {
        this.name = name;
        setLetterChoice(letterChoice);
        this.sosCount = 0;
    }

    //GETTERS
    public String getName() { return name; }
    public String getLetterChoice() { return letterChoice; }
    public int getSOSCount() { return sosCount; }
    public int getScore() { return sosCount; } // Alias for getSOSCount

    //SETTERS
    public void setLetterChoice(String letterChoice) { 
        if (!letterChoice.equals("S") && !letterChoice.equals("O")) {
            throw new IllegalArgumentException("Letter choice must be 'S' or 'O'");
        }
        this.letterChoice = letterChoice;
    }

    public void incrementSOS() { sosCount++; }
    public void resetSOS() { sosCount = 0; }
}
