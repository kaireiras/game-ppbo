package main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Dino-adv");

        GamePanel gamePanel = new GamePanel();
        gamePanel.requestFocusInWindow();
        gamePanel.setGame();
        gamePanel.startGameThread();

        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}
