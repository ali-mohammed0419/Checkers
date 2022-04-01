package Checkers;

public class Main {

    static final int BOARD_WIDTH = 8;

    public static void main(String[] args) {

        Board board = new Board();

        boolean prevIsMousePressed = false;
        boolean isPlayer1Turn = true;

        StdDraw.setScale(0, 80);
        StdDraw.enableDoubleBuffering(); //Calling this method stops things from being drawn immediately after a draw method is called. This allows you to call many different draw methods without anything being drawn on the screen; when you call StdDraw.show(), everything will be drawn at once.
        double timeElapsed = 0.017; //0.017 seconds-- this is how long each frame of our animation appears.
        while (true) {
            board.draw();

            //Clicking Logic
            boolean didClickOccur = false;
            if (prevIsMousePressed && !StdDraw.isMousePressed()) {
                didClickOccur = true;
            }
            prevIsMousePressed = StdDraw.isMousePressed();


            if (didClickOccur) {
                int clickedRow = (int) (StdDraw.mouseY() / 10);
                int clickedCol = (int) (StdDraw.mouseX() / 10);
                boolean[][] tempHighlightedSpaces = board.getHighlightedSpots();
                Checker[][] tempBoard = board.getBoard();


                boolean didClickOnHighlight = false;
                //if we click on a highlighted space, it moves the selected checker there

                //1. Cycle thru the board and find the selected checker
                //2. Remove the selected checker and place it on the highlighted square\
                if (tempHighlightedSpaces[clickedRow][clickedCol] == true) {
                    if(isPlayer1Turn){
                        isPlayer1Turn = false;
                    }else{
                        isPlayer1Turn = true;
                    }
                    System.out.println(isPlayer1Turn);
                    for (int row = 0; row < tempBoard.length; row++) {
                        for (int col = 0; col < tempBoard[0].length; col++) {
                            if (tempBoard[row][col] != null) {
                                if (tempBoard[row][col].getIsSelected()) {
                                    //System.out.println(row + "," + col);
                                    Checker selectedChecker = tempBoard[row][col];
                                    tempBoard[row][col] = null;
                                    tempBoard[clickedRow][clickedCol] = selectedChecker;
                                    board.unselectAllCheckers();
                                    board.unselectAllHighlights();
                                    if(selectedChecker.getPlayerNum() == 2 || selectedChecker.getIsKing()){
                                        if (row > clickedRow && col > clickedCol) { //down left
                                            tempBoard[clickedRow + 1][clickedCol + 1] = null;
                                        }
                                        if(row > clickedRow && col < clickedCol){ //down right
                                            tempBoard[clickedRow + 1][clickedCol - 1] = null;
                                        }
                                    }
                                    if(selectedChecker.getPlayerNum() == 1 || selectedChecker.getIsKing()){
                                        if(row < clickedRow && col > clickedCol){ //up left
                                            tempBoard[clickedRow - 1][clickedCol + 1] = null;
                                        }
                                        if(row < clickedRow && col < clickedCol){ //up right
                                            tempBoard[clickedRow - 1][clickedCol - 1] = null;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //System.out.println(board.toString());
                } else {

                    //Select Checker Logic
                    Checker selectedChecker = tempBoard[clickedRow][clickedCol];
                    board.unselectAllHighlights();
                    board.unselectAllCheckers();
                    if (selectedChecker != null) {
                        selectedChecker.selectChecker(); //Select clicked checker
                        //Creating highlights based on checker clicked
                        if (isPlayer1Turn == false && selectedChecker.getPlayerNum() == 2 || selectedChecker.getIsKing()) {
                            //System.out.println(selectedChecker.getIsKing());
                            if (clickedRow - 1 >= 0 && clickedCol + 1 < BOARD_WIDTH && tempBoard[clickedRow - 1][clickedCol + 1] == null) { //down-right 1
                                tempHighlightedSpaces[clickedRow - 1][clickedCol + 1] = true;
                            }
                            if (clickedRow - 1 >= 0 && clickedCol - 1 >= 0 && tempBoard[clickedRow - 1][clickedCol - 1] == null) { //down-left 1
                                tempHighlightedSpaces[clickedRow - 1][clickedCol - 1] = true;
                            }

                            if (clickedRow - 1 >= 0 && clickedCol + 1 < BOARD_WIDTH && tempBoard[clickedRow - 1][clickedCol + 1] != null) { //down-right 2 jumping
                                if(tempBoard[clickedRow-1][clickedCol+1].getPlayerNum() != selectedChecker.getPlayerNum()) {//hardcode
                                    if (clickedRow - 2 >= 0 && clickedCol + 2 < BOARD_WIDTH && tempBoard[clickedRow - 2][clickedCol + 2] == null) {
                                        tempHighlightedSpaces[clickedRow - 2][clickedCol + 2] = true;
                                    }
                                }
                            }
                            if (clickedRow - 1 >= 0 && clickedCol - 1 >= 0 && tempBoard[clickedRow - 1][clickedCol - 1] != null) { //down-left 2 jumping
                                if(tempBoard[clickedRow-1][clickedCol-1].getPlayerNum() != selectedChecker.getPlayerNum()) {
                                    if (clickedRow - 2 >= 0 && clickedCol - 2 >= 0 && tempBoard[clickedRow - 2][clickedCol - 2] == null) {
                                        tempHighlightedSpaces[clickedRow - 2][clickedCol - 2] = true;
                                    }
                                }
                            }
                            if(selectedChecker.getPlayerNum() == 2){
                                if(clickedRow == 0){
                                    selectedChecker.changeKingStatus();
                                }
                            }
                        }
                        if (isPlayer1Turn == true && selectedChecker.getPlayerNum() == 1 || selectedChecker.getIsKing()) {
                            if (clickedRow + 1 <= 7 && clickedCol + 1 < BOARD_WIDTH && tempBoard[clickedRow + 1][clickedCol + 1] == null) { //up-right 1
                                tempHighlightedSpaces[clickedRow + 1][clickedCol + 1] = true;
                            }
                            if (clickedRow + 1 <= 7 && clickedCol - 1 >= 0 && tempBoard[clickedRow + 1][clickedCol - 1] == null) { //up-left 1
                                tempHighlightedSpaces[clickedRow + 1][clickedCol - 1] = true;
                            }

                            if (clickedRow + 1 <= 7 && clickedCol - 1 >= 0 && tempBoard[clickedRow + 1][clickedCol - 1] != null) { //up-left 2 - jumping
                                if(tempBoard[clickedRow+1][clickedCol-1].getPlayerNum() != selectedChecker.getPlayerNum()) {
                                    if (clickedRow + 2 <= 7 && clickedCol - 2 >= 0 && tempBoard[clickedRow + 2][clickedCol - 2] == null) {
                                        tempHighlightedSpaces[clickedRow + 2][clickedCol - 2] = true;
                                    }
                                }
                            }
                            if (clickedRow + 1 <= 7 && clickedCol + 1 < BOARD_WIDTH && tempBoard[clickedRow + 1][clickedCol + 1] != null) { //up-right 2 - jumping
                                if (clickedRow + 2 <= 7 && clickedCol + 2 < BOARD_WIDTH && tempBoard[clickedRow + 2][clickedCol + 2] == null) {
                                    if (tempBoard[clickedRow + 1][clickedCol + 1].getPlayerNum() != selectedChecker.getPlayerNum()) {
                                        tempHighlightedSpaces[clickedRow + 2][clickedCol + 2] = true;
                                    }
                                }
                            }
                            if(selectedChecker.getPlayerNum() == 1){
                                if(clickedRow == 7){
                                    selectedChecker.changeKingStatus();
                                }
                            }
                        }

                        }
                    }

                    //System.out.println(board.toString());
                    //System.out.println(board.printHighlightedBoard());

                }


                StdDraw.show(); //Because we have called StdDraw.enableDoubleBuffering(), everything that you draw up until this point will be loaded into java's memory but not actually drawn. Calling StdDraw.draw() then draws everything at once that is loaded into java's memory.
                StdDraw.pause((int) (timeElapsed * 1000)); //You must pass to the pause method the number of milliseconds to pause for; so we multiply by 1000 because our timeElapsed variable is in seconds, not milliseconds.
                StdDraw.clear(); //This clears everything drawn on the screen. You must redraw the image you wish to display for each frame of an animation
            }
        }
    }
