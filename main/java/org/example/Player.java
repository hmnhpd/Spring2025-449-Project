package org.example;

public class Player {
    private String name;
    private String letterChoice;
    private int sosCount;

    public Player(String name, String letterChoice){
        this.name = name;
        this.letterChoice = letterChoice;
        this.sosCount = 0;
    }

    //GETTERS
    public String getName() { return name; }
    public String getLetterChoice() { return letterChoice; }
    public int getSOSCount() { return sosCount; }

    //SETTERS
    public void setLetterChoice(String letterChoice) { this.letterChoice = letterChoice; }
    public void setSosCount(int sosCount) { this.sosCount = sosCount; }

    public void incrementSOS() { sosCount++; }

    public void resetSOS() { sosCount = 0; }

}
