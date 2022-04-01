package Checkers;

import java.awt.*;

public class Checker {

    private int playerNum;
    private Color color;
    private boolean isKing;
    private boolean isSelected;

    //Setting int to 0 means it can't move and/or isn't selected.
    //               1 means it can slide in that direction.
    //               2 means it can jump in that direction.

    public Checker(int playerNum){
        this.playerNum = playerNum;
        if(this.playerNum == 1){
            color = Color.WHITE;
        }else{
            color = new Color(207,156,99);
        }
        this.isKing = false;
        this.isSelected = false;
    }

    public void draw(int x, int y){
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(x, y, 3.8);

        //Outline
        StdDraw.setPenColor(StdDraw.BLACK);
        if(this.isSelected){
            StdDraw.setPenColor(StdDraw.GREEN);
        }
        StdDraw.setPenRadius(0.007);
        StdDraw.circle(x, y, 3.8);
        if(this.isKing){
            if(getPlayerNum() == 1){
                StdDraw.picture(x,y,"Checkers/unnamed.jpg");
            }else{
                StdDraw.picture(x,y,"Checkers/Demo.png",8,8,270);
            }
        }
    }
    //row and col of selected checker
    /*public void highlightMovementOptions(Checker[][] board, int row, int col){
    }*/

    public boolean getIsSelected(){
        return this.isSelected;
    }

    public void selectChecker(){
        this.isSelected = true;
    }

    public void unselectChecker(){
        this.isSelected = false;
    }


    public boolean getIsKing(){
        return isKing;
    }
    public void changeKingStatus(){
        this.isKing = true;
    }
    public int getPlayerNum(){
        return playerNum;
    }





}