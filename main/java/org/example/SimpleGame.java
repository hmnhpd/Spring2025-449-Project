package org.example;


import javax.swing.*;

public class SimpleGame {
    public boolean simpleGameWin(){
        if (checkWinner(row,col,currentLetter)){
            String winner = playerTurn ? "Blue" : "Red";
            JOptionPane.showMessageDialog(null, winner +" Player Won");

            clearBoard(boardSize);

            return true;
        }
        return false; //PLACEHOLDER
    }
}
