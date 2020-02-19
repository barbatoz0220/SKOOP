package com.sk.game;

import com.sk.game.audio.MusicPlayer;

import javax.swing.*;

public class Window extends JFrame {
    public Window() {

        setTitle("SKOOP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Hide or close the window
        setContentPane(new GamePanel(1280, 720));
        pack(); // Pack() method creates a moderate space without using setSize or setBounds method
        setLocationRelativeTo(null); // Setting null will put the GUI at the center of the screen
        setVisible(true);
        setResizable(false);

        // Starting the background music thread
        MusicPlayer menuBGM = new MusicPlayer("Headphone Activist");
        menuBGM.run();
    }
}
