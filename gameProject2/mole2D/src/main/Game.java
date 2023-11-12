package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Game extends JFrame implements GameBase   { // Inheritance JFrame

    public Game() {
        initGUI();
        clearBoard();
        initEvents();

    }

    private JPanel panel;
    private JLabel[] holes = new JLabel[16]; // Array เก็บ holes
    private int[] board = new int[16];

    private int score = 0;
    private int time = 20;
    private int rounds = 3;
    private int currentRound = 1;
    private int totalScore = 0;

    private Timer moleTimer;
    private JLabel lblScore;
    private JLabel Show_time;
    private JLabel totalScoreLabel;
    private JLabel roundScoreLabel;
    private JButton btnStart;
    private Timer timer;

// Import Image
    private ImageIcon loadImage(String path) {
        Image image = new ImageIcon(this.getClass().getResource(path)).getImage();
        Image scaledImage = image.getScaledInstance(125, 125, java.awt.Image.SCALE_SMOOTH);

        return new ImageIcon(scaledImage);
    }

// Random Mole
    private void genRandMole() {
        Random rnd = new Random(System.currentTimeMillis());  
        int mole_id = rnd.nextInt(16); // 

        board[mole_id] = 1; 
        holes[mole_id].setIcon(loadImage("/image/MoleChar2.png"));
    }

// ล้างหลุม ทุกรอบให้เป็น 0 
    @Override
    public void clearBoard() {
        for (int i = 0; i < 16; i++) {
            holes[i].setIcon(loadImage("/image/PitsofMole2 256.png"));
            board[i] = 0;
        }
    }

// เก็บคะแนน
    @Override
    public void pressedButton(int id) {
        int val = board[id];

        if (val == 1) {
            score++;
        } 
        else {
            score--;
        }

        lblScore.setText("Your score : " + score);
        clearBoard();
        genRandMole();
    }

    @Override
    public void gameOver() { 

        btnStart.setEnabled(true);

        totalScore += score; 
        totalScoreLabel.setText("Total Score: " + totalScore); 
        roundScoreLabel.setText("Round Score: " + score); 
        score = 0;
        time = 20;
        lblScore.setText("Score : 20 ");
        Show_time.setText("20");

        if (currentRound < rounds) {
            currentRound++;
            moleTimer.setDelay(moleTimer.getDelay() - 200);
            resetGame();
            moleTimer.start();
            timer.start();
        } 

        else {
            clearBoard();
        }
    }

    private void resetGame() {
        clearBoard();
        genRandMole();
        time = 20;
        Show_time.setText("20");
        score = 0;
        lblScore.setText("Score : 0");
        totalScoreLabel.setText("Total Score: " + totalScore);
        roundScoreLabel.setText("Round Score: 0"); // กำหนดค่าเริ่มต้น
    }

    private void initEvents() {
        for (int i = 0; i < holes.length; i++) {
            holes[i].addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    JLabel lbl = (JLabel) e.getSource();
                    int id = Integer.parseInt(lbl.getName());
                    pressedButton(id);
                }
            });
            
        }

        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnStart.setEnabled(false);
                totalScore = 0; 
                currentRound = 1; 
                resetGame();
                moleTimer.start(); 
                timer.start(); 
            }
        });

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (time == 0) {
                    Show_time.setText(" " + time);
                    timer.stop();
                    moleTimer.stop();
                    if (currentRound < rounds) {
                        currentRound++;
                        totalScore += score; // เพิ่มคะแนนทั้งหมด
                        moleTimer.setDelay(moleTimer.getDelay() - 200); // เพิ่มความเร็วขึ้นทีละรอบ
                        resetGame(); // เริ่มรอบใหม่
                        moleTimer.start();
                        timer.start();
                    } else {
                        gameOver();
                    }
                }
                Show_time.setText(" " + time);
                time--;
            }
        });

        moleTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                genRandMole();
            }
        });
    }

    @Override
    public void initGUI() {

    // Window game
        setTitle("MoleSmash !!");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 720);

    // Set Background
        JPanel contentPanel = new JPanel();
        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(0, 51, 0));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(null);

    // Set Ttile
        JLabel lblTitle = new JLabel(" Mole Smash");
        lblTitle.setForeground(new Color(153, 204, 0));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Century Gothic", Font.BOLD, 20));
        lblTitle.setBounds(0, 0, 602, 47);
        contentPanel.add(lblTitle);
        setContentPane(contentPanel);

        panel = new JPanel();
        panel.setBackground(new Color(0, 102, 0));
        panel.setBounds(30, 105, 535, 546);
        panel.setLayout(null);
        panel.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                loadImage("/image/Hammer 16.png").getImage(),
                new Point(0, 0), "custom cursor1"));
        contentPanel.add(panel);

        lblScore = new JLabel("Score : 0"); // Set score
        lblScore.setHorizontalAlignment(SwingConstants.TRAILING);
        lblScore.setFont(new Font("Cambria", Font.BOLD, 14));
        lblScore.setForeground(new Color(135, 206, 250));
        lblScore.setBounds(423, 54, 144, 33);
        contentPanel.add(lblScore);

        Show_time = new JLabel("20"); // set Show time
        Show_time.setHorizontalAlignment(SwingConstants.CENTER);
        Show_time.setForeground(new Color(240, 128, 128));
        Show_time.setFont(new Font("Cambria Math", Font.BOLD, 24));
        Show_time.setBounds(232, 54, 144, 33);
        contentPanel.add(Show_time);

        roundScoreLabel = new JLabel("Round Score: 0");
        roundScoreLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        roundScoreLabel.setForeground(new Color(255, 255, 0));
        roundScoreLabel.setFont(new Font("Cambria Math", Font.BOLD, 14));
        roundScoreLabel.setBounds(433, 118, 134, 33);
        contentPanel.add(roundScoreLabel);

        totalScoreLabel = new JLabel("Total Score: 0");
        totalScoreLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        totalScoreLabel.setForeground(new Color(255, 255, 0));
        totalScoreLabel.setFont(new Font("Cambria Math", Font.BOLD, 14));
        totalScoreLabel.setBounds(433, 82, 134, 33);
        contentPanel.add(totalScoreLabel);

        btnStart = new JButton("Start"); // Button
        btnStart.setBackground(Color.WHITE);
        btnStart.setBounds(32, 60, 110, 33);
        contentPanel.add(btnStart);

    // Set Holes 0-15 หลุม

        holes[0] = new JLabel("0");
        holes[0].setName("0");
        holes[0].setBounds(0, 396, 132, 132);
        panel.add(holes[0]);

        holes[1] = new JLabel("1");
        holes[1].setName("1");
        holes[1].setBounds(132, 396, 132, 132);
        panel.add(holes[1]);

        holes[2] = new JLabel("2");
        holes[2].setName("2");
        holes[2].setBounds(264, 396, 132, 132);
        panel.add(holes[2]);

        holes[3] = new JLabel("3");
        holes[3].setName("3");
        holes[3].setBounds(396, 396, 132, 132);
        panel.add(holes[3]);

        holes[4] = new JLabel("4");
        holes[4].setName("4");
        holes[4].setBounds(0, 264, 132, 132);
        panel.add(holes[4]);

        holes[5] = new JLabel("5");
        holes[5].setName("5");
        holes[5].setBounds(132, 264, 132, 132);
        panel.add(holes[5]);

            holes[6] = new JLabel("6");
            holes[6].setName("6");
            holes[6].setBounds(264, 264, 132, 132);
            panel.add(holes[6]);

            holes[7] = new JLabel("7");
            holes[7].setName("7");
            holes[7].setBounds(396, 264, 132, 132);
            panel.add(holes[7]);

            holes[8] = new JLabel("8");
            holes[8].setName("8");
            holes[8].setBounds(0, 132, 132, 132);
            panel.add(holes[8]);

            holes[9] = new JLabel("9");
            holes[9].setName("9");
            holes[9].setBounds(132, 132, 132, 132);
            panel.add(holes[9]);

            holes[10] = new JLabel("10");
            holes[10].setName("10");
            holes[10].setBounds(264, 132, 132, 132);
            panel.add(holes[10]);

            holes[11] = new JLabel("11");
            holes[11].setName("11");
            holes[11].setBounds(396, 132, 132, 132);
            panel.add(holes[11]);

            holes[12] = new JLabel("12");
            holes[12].setName("12");
            holes[12].setBounds(0, 0, 132, 132);
            panel.add(holes[12]);

            holes[13] = new JLabel("13");
            holes[13].setName("13");
            holes[13].setBounds(132, 0, 132, 132);
            panel.add(holes[13]);

            holes[14] = new JLabel("14");
            holes[14].setName("14");
            holes[14].setBounds(264, 0, 132, 132);
            panel.add(holes[14]);

            holes[15] = new JLabel("15");
            holes[15].setName("15");
            holes[15].setBounds(396, 0, 132, 132);
            panel.add(holes[15]);

    }

    public static void main(String[] args) {

        Game frame = new Game(); 
        frame.setVisible(true);

    }

}

