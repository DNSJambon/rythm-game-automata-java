package info3.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.RandomAccessFile;

import javax.swing.JFrame;
import javax.swing.JLabel;

import info3.game.graphics.GameCanvas;
import info3.game.sound.RandomFileInputStream;

public class Game {

    static Game game;

    public static void main(String args[]) throws Exception {
        try {
            System.out.println("Game starting...");
            game = new Game();
            System.out.println("Game started.");
        } catch (Throwable th) {
            th.printStackTrace(System.err);
        }
    }

    JFrame m_frame;
    JLabel m_text;
    GameCanvas m_canvas;
    CanvasListener m_listener;
    //Sound m_music;

    Game() throws Exception {
        // creating a cowboy, that would be a model
		// in an Model-View-Controller pattern (MVC)
		//Cowboy cowboy = new Cowboy();
		// creating a listener for all the events
		// from the game canvas, that would be
		// the controller in the MVC pattern
		m_listener = new CanvasListener(this);
		// creating the game canvas to render the game,
		// that would be a part of the view in the MVC pattern
		m_canvas = new GameCanvas(m_listener);

		System.out.println("  - creating frame...");
		Dimension d = new Dimension(1024, 768);
		m_frame = m_canvas.createFrame(d);

		System.out.println("  - setting up the frame...");
		//setupFrame();
    }

}