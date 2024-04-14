package PegGameGUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This class creates a GUI version of PegGame
 */
public class PegGameGUI extends Application {
    
    private static final int ROWS = Board.readingFile(getFile()); // constant indicating the number of Rows for the Peg Board
                                                         // which is obtained by reading the file
    private static final int COLUMNS = ROWS; // constant indicating the number of Clumns for the Peg
                                                            // Board which is obtained by reading the file
    private static final String PEG = "o"; // constant value to represent a Peg
    private static final String HOLE = "-"; // constant value to represent a Hole

    private Button[][] board = new Button[ROWS][COLUMNS]; // a 2D array of buttons to make the Peg Game playable
    private Button selectedPeg = null; // field reprsenting the current peg selected by the user - initially is null
    private Label gameState; // a Label displaying the current State of the game
    private Label pegsLabel; // a Label displaying the number of Pegs remaining in the game
    private Label holesLabel; // a Label displaying the number of Holes remaining in the game
    private Label possibleMoves; // a Label displaying the Possible Moves the User can make in the Game

    private int pegs = ROWS * COLUMNS - 1; // declaring a field to keep track of the number of Pegs in the Game;
                                           // initially it is equal to the size of the board minus 1
    private int holes = 1; // declaring a field to keep track of the number of Holes in the Game; initially
                           // it is equal to 1

    /**
     * Method from the Application class through which the Game will be displayed on
     * the Screen
     */
    @Override
    public void start(Stage stage) {

        stage.setTitle("Peg Game using JavaFX"); // Title of the Window

        Label header = new Label("PEG GAME"); // a Header for the Game
        header.setFont(Font.font("Times New Roman", 40)); // Font and Font size of the header
        header.setTextFill(Color.BLACK); // Font color of the header

        GridPane pane = new GridPane(); // creating the board of Buttons in the form of a Grid using GridPane
        pane.setAlignment(Pos.CENTER); // Centre the Grid on the Screen
        pane.setHgap(0); // the space between subsequent Buttons horizontally
        pane.setVgap(0); // the space between subsequent Buttons vertically

        // Loop to create a Grid of Buttons
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Button button; // declare a button variable
                if (row == 0 && col == 0) {
                    button = new Button(HOLE); // the first grid element ie board[0][0] will initially be set to a Hole
                } else {
                    button = new Button(PEG); // the rest of the grid elements will be set to Pegs
                }
                button.setPrefSize(100, 100); // size of each button
                button.setBackground(
                        new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY))); // Background
                                                                                                               // Color
                                                                                                               // of
                                                                                                               // each
                                                                                                               // button
                button.setTextFill(Color.DARKGREY); // Font color of each button
                button.setFont(new Font("Arial", 40)); // Font and Font size of each button
                button.setOnAction(e -> buttonHandle(button)); // Event Handler for the Buttons
                board[row][col] = button; // create a Button on each row and column with the specified properties
                pane.add(button, col, row); // add the Buttons to the Grid
            }
        }

        gameState = new Label("Game Status: Not Started"); // Label displaying the Status of the Game
        gameState.setFont(Font.font("Times New Roman", 20)); // Font and Font size of the label
        gameState.setTextFill(Color.BLACK); // Font color of the Label
        gameState.setAlignment(Pos.CENTER); // centre the label on the Screen

        pegsLabel = new Label("PEGS: " + pegs); // Label displaying the number of Remaining Pegs in the Game
        pegsLabel.setFont(Font.font("Arial", 15)); // Font and Font size of the label
        pegsLabel.setTextFill(Color.BLACK); // Font color of the Label

        holesLabel = new Label("HOLES: " + holes); // Label displaying the number of Remaining Holes in the Game
        holesLabel.setFont(Font.font("Arial", 15)); // Font and Font size of the label
        holesLabel.setTextFill(Color.BLACK); // Font color of the Label

        possibleMoves = new Label("Possible Moves: "); // Label displaying the Possible Moves the User can make
        possibleMoves.setFont(Font.font("Times New Roman", 16)); // Font and Font size of the label
        possibleMoves.setTextFill(Color.BLACK); // Font color of the Label

        Button saveExit = new Button("SAVE & EXIT"); // Button to Save and Exit the Game
        saveExit.setOnAction(event -> stage.close());

        Button exit = new Button("EXIT"); // Button to Exit the Game
        exit.setOnAction(event -> stage.close()); // Event Handler to close the Game Window

        VBox vbox = new VBox(20); // Vertical layout
        vbox.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY))); // setting
                                                                                                                 // up
                                                                                                                 // the
                                                                                                                 // Background
        vbox.setAlignment(Pos.CENTER); // Centre all the components on the screen
        vbox.getChildren().addAll(header, pane, gameState, pegsLabel, holesLabel); // adding components to the screen

        VBox buttons = new VBox(20); // Vertical layout of the save and exit & exit buttons
        buttons.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY))); // setting
                                                                                                                    // up
                                                                                                                    // the
                                                                                                                    // Background
        buttons.setAlignment(Pos.CENTER); // Centre all the components on the screen
        buttons.getChildren().addAll(saveExit, exit); // adding components to the screen

        HBox hbox = new HBox(10); // Horizontal layout
        hbox.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY))); // setting
                                                                                                                 // up
                                                                                                                 // the
                                                                                                                 // Background
        hbox.setAlignment(Pos.CENTER); // Centre all the components on the screen
        hbox.getChildren().addAll(vbox, possibleMoves, buttons); // adding components to the screen

        Scene scene = new Scene(hbox, 800, 700); // adding the hbox to the scene and setting the size of the Window
        stage.setScene(scene); // add the scene to display
        stage.show(); // display the Game
    }

    /**
     * Method to recieve the filename from the user and read it
     * @return the filename
     */
    private static String getFile(){
        Scanner s = new Scanner(System.in); // Scanner to scan the input
        System.out.println("Enter the file name: ");  // Enter full path, For eg: C:/Users/Fazila Syed/Documents/.../gamefile.txt
        String file = s.nextLine(); // reads the path
        Board.setFilename(file); // set the filename
        return Board.getFilename(); // return the filename
    }

    /**
     * This method is used to determine if a move is allowed by the Peg or not based
     * on specified conditions
     * 
     * @param r1 the row of the location where the Peg is located initially
     * @param c1 the column of the location where the Peg is located initially
     * @param r2 the row of the location to where the Peg is going to Jump
     * @param c2 the column of the location to where the Peg is going to Jump
     * @return true if the move is valid by the Peg and false otherwise
     */
    private boolean canMakeMove(int r1, int c1, int r2, int c2) {
        // if the given values for r2 and c2 fit within the range of the board
        if (r2 >= 0 && r2 < ROWS && c2 >= 0 && c2 < COLUMNS) {

            Button start = board[r1][c1]; // the initial location of the Peg's button
            Button mid = board[(r1 + r2) / 2][(c1 + c2) / 2]; // the button in the middle over which the Peg is trying
                                                              // to jump
            Button end = board[r2][c2]; // the location to where the Peg is jumping

            if (start.getText().equals(PEG) &&
                    mid.getText().equals(PEG) &&
                    end.getText().equals(HOLE) && ((c1 == c2 + 2 || r1 == r2 + 2) || (c1 == c2 - 2 || r1 == r2 - 2))) {
                // if the start button is a PEG
                // and the middle button is a PEG
                // and the end button is a HOLE
                // and the moves are either vertical: column-wise or horizontal: row-wise
                return true; // then the method returns true and rest of the code will not run
            }
        }
        gameState.setText("Invalid Move!"); // if the conditions are not satisfied, then an Error message is displayed
                                            // on the Screen as the status
        return false; // the method returns false
    }

    /**
     * Event Handler for the Buttons
     * 
     * @param button the button on which the event occurs
     */
    private void buttonHandle(Button button) {
        if (selectedPeg == null) {
            // button not selected
            if (button.getText().equals(PEG)) {
                // select a button which is a PEG (FROM location)
                selectedPeg = button; // set that button as the current selected button
                selectedPeg.setBackground(
                        new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY))); // highlight
                                                                                                            // the
                                                                                                            // currently
                                                                                                            // selected
                                                                                                            // button
                selectedPeg.setTextFill(Color.DARKGREY); // Font color of the currently selected button
            }
        } else {
            // attempting to make a move using the button
            if (button.getText().equals(HOLE)) {
                // if the selected new location is a Hole (TO location)
                int r1 = -1; // declare variable for the FROM row and initialize it with a value
                int c1 = -1; // declare variable for the FROM column and initialize it with a value
                int r2 = GridPane.getRowIndex(button); // declare variable for the TO row and access the TO locations
                                                       // index
                int c2 = GridPane.getColumnIndex(button); // declare variable for the TO column and access the TO
                                                          // locations index

                for (int row = 0; row < ROWS; row++) {
                    for (int col = 0; col < COLUMNS; col++) {
                        if (board[row][col] == selectedPeg) {
                            r1 = row; // set the row of the selected peg into FROM row
                            c1 = col; // set the column of the selected peg into TO row
                            break; // terminate the loop once the desired button is found
                        }
                    }
                }

                if (canMakeMove(r1, c1, r2, c2)) {
                    // if the PEG can make a valid move
                    int midR = (r1 + r2) / 2; // the middle button's row value
                    int midC = (c1 + c2) / 2; // the middle button's column value
                    board[midR][midC].setText(HOLE); // the middle button becomes a HOLE after the move is made
                    button.setText(PEG); // the TO locations button becomes a PEG
                    selectedPeg.setText(HOLE); // the initially selected peg becomes a HOLE
                    selectedPeg.setBackground(
                            new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY))); // the
                                                                                                                   // background
                                                                                                                   // is
                                                                                                                   // set
                                                                                                                   // back
                                                                                                                   // to
                                                                                                                   // normal
                                                                                                                   // and
                                                                                                                   // is
                                                                                                                   // no
                                                                                                                   // longer
                                                                                                                   // highlighted
                    selectedPeg.setTextFill(Color.DARKGREY); // Font color of the button
                    selectedPeg = null; // the selected button is set back to null for the next move
                    pegs--; // decrement the number of pegs
                    holes++; // increment the number of holes

                    // updateMoves(); // update the possible moves
                    Collection<String> moves = getPossibleMoves(); // Collection of Possible moves to be displayed for
                                                                   // the User
                    StringBuilder movesText = new StringBuilder("Possible Moves:\n"); // Formatting the Possible Moves
                                                                                      // from the String List
                    // Iterate over each String element from the List
                    for (String move : moves) {
                        movesText.append(move).append("\n"); // display the Possible Moves one below the other
                    }

                    possibleMoves.setText(movesText.toString()); // update the text on the Possible moves Label
                    setGameStatus(button); // set the game status
                } else {
                    // if an invalid move is attempted
                    selectedPeg.setBackground(
                            new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY))); // background
                                                                                                                   // color
                                                                                                                   // goes
                                                                                                                   // back
                                                                                                                   // to
                                                                                                                   // normal
                                                                                                                   // and
                                                                                                                   // is
                                                                                                                   // no
                                                                                                                   // longer
                                                                                                                   // highlighted
                    selectedPeg.setTextFill(Color.DARKGREY); // Font olor of the button
                    selectedPeg = null; // the selected button is set back to null for the next move
                }
            }
        }
    }

    /**
     * THis method returns a List of Possible Moves the User can make
     * 
     * @return List of Possible Moves as a String
     */
    private Collection<String> getPossibleMoves() {
        List<String> possibleMoves = new ArrayList<>(); // initialize a String list of Possible moves

        // iterates through the whole board to check for possibilities
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Button button = board[row][col]; // assign the board index to a button for checking possible moves

                if (button.getText().equals(PEG)) {
                    // if the selected button is a PEG
                    // Check for possible moves in all four directions

                    if (row - 2 >= 0 && canMakeMove(row, col, row - 2, col)) {
                        possibleMoves.add("(" + row + ", " + col + ") -> (" + (row - 2) + ", " + col + ")"); // TOP
                    }

                    if (row + 2 < ROWS && canMakeMove(row, col, row + 2, col)) {
                        possibleMoves.add("(" + row + ", " + col + ") -> (" + (row + 2) + ", " + col + ")"); // BOTTOM
                    }

                    if (col + 2 < COLUMNS && canMakeMove(row, col, row, col + 2)) {
                        possibleMoves.add("(" + row + ", " + col + ") -> (" + row + ", " + (col + 2) + ")"); // RIGHT
                    }

                    if (col - 2 >= 0 && canMakeMove(row, col, row, col - 2)) {
                        possibleMoves.add("(" + row + ", " + col + ") -> (" + row + ", " + (col - 2) + ")"); // LEFT
                    }
                }
            }
        }

        return possibleMoves; // return the String List of Possible moves
    }

    /**
     * This method is used to check the Status of the Game
     * 
     * @return the current status of the game
     */
    private String getGameStatus() {
        int pegCount = 0; // variable to keep track of the number of pegs on the Board
        int holeCount = 0; // variable to keep track of the number of holes on the Board

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                // Iterate over the Board to check for the number of Button with the text PEG
                // and HOLE
                Button button = board[row][col]; // set each board index to a button for checking
                if (button.getText().equals(PEG)) {
                    pegCount++; // if the text on the button is a PEG, then the pegCount is incremented
                } else if (button.getText().equals(HOLE)) {
                    holeCount++; // if the text on the button is a HOLE, then the holeCount is incremented
                }
            }
        }

        pegsLabel.setText("Pegs: " + pegCount); // update the pegLabel with the number of Pegs remaining
        holesLabel.setText("Holes: " + holeCount); // update the holeLabel with the number of Holes remaining

        if (pegCount == ROWS * COLUMNS - 1) {
            return "Game Not Started"; // if the number of pegs is equal to the initial number of pegs
        } else if (pegCount == 1) {
            return "You Won!"; // if only one peg remains
        } else if (pegCount > 1 && holeCount != ROWS * COLUMNS - 1 && !getPossibleMoves().isEmpty()) {
            return "In Progress"; // if more than one peg remains and number of holes is not equal to the the size
                                  // of the board minus 1 and Moves are possible
        } else if (holeCount < ROWS * COLUMNS - 1 && pegCount > 1 && getPossibleMoves().isEmpty()) {
            return "Stalemate"; // if more than one peg remains and number of holes is not equal to the the size
                                // of the board minus 1 and no more Moves are Possible
        } else {
            return "Loading..."; // the game is Loading
        }
    }

    /**
     * This method is used to update the Game Status
     * 
     * @param button to update the Game Status after a move is made by the Button
     */
    private void setGameStatus(Button button) {
        button.setTextFill(Color.DARKGREY); // Font color of the Button
        button.setBackground(
                new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY))); // Background of
                                                                                                       // the Button
        gameState.setText("Game State: " + getGameStatus()); // update the status of the Game
    }

    /**
     * Main method to run the GUI Game
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
