package main;

// GameBase.java
public interface GameBase {

    abstract void initGUI();

    abstract void clearBoard();

    abstract void pressedButton(int id);

    abstract void gameOver();
}
