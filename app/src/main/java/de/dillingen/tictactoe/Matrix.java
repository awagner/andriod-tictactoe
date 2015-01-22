/*
 * (c) 2015 by Joachim Weishaupt;
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

/**
 * 12.01.2015
 * Class Matrix:
 * Implementiert das Spielfeld und die Programmlogik von TicTacToe
 *
 */

package de.dillingen.tictactoe;

public class Matrix {

    public final String[] symbol = {" - ", "X", "O"};
    private int[][] matrix;
    private int size = 3;
    // used to prevent double clicks.
    private boolean gameInProgress = false;
    private int activePlayer = 1;

    /**
     * Initialisieren des Spielfeldes
     *
     * @param newsize the size of the matrix
     */
    public Matrix(int newsize) {

        // set Attributes.
        size = newsize;
        matrix = new int[size][size];

        // reset Matrix and set Progress to true.
        newGame();
        setActivePlayer(1);
    }

    /**
     * Set all the values to 0 and gameInProgress to true to indicate that game has started.
     */
    public void newGame() {
        // set all null
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = 0;
            }
        }
        setGameInProgress(true);
    }

    /** getter method for the attribute gameInProgress
     *
     * @return boolean the current value of gameInProgress
     */
    public boolean getGameInProgress() {
        return (gameInProgress);
    }

    /** setter method for attribute gameInProgress
     *
     * @param b boolean the new value for gameInProgress
     */
    public void setGameInProgress(boolean b) {
        gameInProgress = b;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(int player) {
        activePlayer = player;
    }

    public void toggleActivePlayer() {
        if (gameInProgress) {
            if (activePlayer == 1) {
                activePlayer = 2;
            } else {
                activePlayer = 1;
            }
        }
    }

    /**
     * setSymbol wird bei jedem Spielzug aufgerufen und setzt in das gewählte Feld n, m
     * jeweils den aktiven Spieler ein.
     *
     * @param n Zeile
     * @param m Spalte
     */
    public void setSymbol(int n, int m) {
        if (matrix[n][m] == 0) {
            matrix[n][m] = getActivePlayer();
            toggleActivePlayer();
        }
    }

    /** get the Symbol on Position n, m of the playing field
     *
      * @param n the row of the matrix
     * @param m the column of the matrix
     * @return Strings the Symbol for the Player
     */
    public String getSymbol(int n, int m) {
        return symbol[matrix[n][m]];
    }

    public String getSymbol(int player) {
        return symbol[player];
    }

    /**
     * Prüft auf dem Spielfeld Matrix, ob eine "Gewinner"-Konstellation vorliegt.
     *
     * @return 0: kein Sieger bzw. GewinnerId
     */
    public int checkGame() {
        int playerWon;
        int j = 0;

        setGameInProgress(false);

        // columns
        for (int i = 0; i < size; i++) {
            j = 0;
            playerWon = matrix[i][j];
            while (playerWon != 0 && matrix[i][j] == playerWon) {
                j++;
                if (j == size)
                    return (playerWon);
            }
        }

        // rows
        for (int i = 0; i < size; i++) {
            j = 0;
            playerWon = matrix[j][i];
            while (playerWon != 0 && matrix[j][i] == playerWon) {
                j++;
                if (j == size)
                    return (playerWon);
            }
        }

        // diagonal
        j = 1;
        playerWon = matrix[0][0];
        while (playerWon != 0 && matrix[j][j] == playerWon) {
            j++;
            if (j == size)
                return (playerWon);
        }
        j = 1;
        playerWon = matrix[size - 1][0];
        while (playerWon != 0 && matrix[size - 1 - j][j] == playerWon) {
            j++;
            if (j == size)
                return (playerWon);
        }


        setGameInProgress(true);


        return 0;
    }

}