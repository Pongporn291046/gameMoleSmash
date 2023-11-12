package main;

public class GameLauncher {
    public static void main(String[] args) {
        GameBase game = new Game(); // Polymorphism: GameBase เป็นประเภทของ game
        game.initGUI(); // เรียกใช้ initGUI() จาก Game class
        game.clearBoard(); // เรียกใช้ clearBoard() จาก Game class

        // ตัวอย่างการใช้ Polymorphism ในการเรียก pressedButton()
        int buttonId = 2; // ตัวแปร buttonId ประเภท int
        game.pressedButton(buttonId); // เรียกใช้ pressedButton() จาก Game class

        // ตัวอย่างการใช้ Polymorphism ในการเรียก gameOver()
        game.gameOver(); // เรียกใช้ gameOver() จาก Game class
    }
}
