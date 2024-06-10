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

import info3.game.model.*;
import info3.game.controller.*;


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
	Grille m_grille;
	Control m_control;

	//Sound m_music;

	Game() throws Exception {
		// creating a cowboy, that would be a model
		// in an Model-View-Controller pattern (MVC)
		m_control = new Control();
		m_grille = new Grille(7, 7, m_control);
		// creating a listener for all the events
		// from the game canvas, that would be
		// the controller in the MVC pattern
		m_listener = new CanvasListener(this);
		// creating the game canvas to render the game,
		// that would be a part of the view in the MVC pattern
		m_canvas = new GameCanvas(m_listener);

		
		

		System.out.println("  - creating frame...");
		Dimension d = new Dimension(800, 800);
		m_frame = m_canvas.createFrame(d);

		System.out.println("  - setting up the frame...");
		setupFrame();
	}

	/*
	 * Then it lays out the frame, with a border layout, adding a label to the north
	 * and the game canvas to the center.
	 */
	private void setupFrame() {

		m_frame.setTitle("Game");
		m_frame.setLayout(new BorderLayout());

		m_frame.add(m_canvas, BorderLayout.CENTER);

		m_text = new JLabel();
		m_text.setText("Tick: 0ms FPS=0");
		m_frame.add(m_text, BorderLayout.NORTH);

		// center the window on the screen
		m_frame.setLocationRelativeTo(null);

		// make the vindow visible
		m_frame.setVisible(true);
	}

	/*
	 * ================================================================ All the
	 * methods below are invoked from the GameCanvas listener, once the window is
	 * visible on the screen.
	 * ==============================================================
	 */

	/*
	 * Called from the GameCanvas listener when the frame
	 */
	String m_musicName;

	void loadMusic() {
		m_musicName = m_musicNames[m_musicIndex];
		String filename = "resources/" + m_musicName + ".ogg";
		m_musicIndex = (m_musicIndex + 1) % m_musicNames.length;
		try { 
			RandomAccessFile file = new RandomAccessFile(filename,"r");
			RandomFileInputStream fis = new RandomFileInputStream(file);
			m_canvas.playMusic(fis, 0, 1.0F);
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	private int m_musicIndex = 0;
	private String[] m_musicNames = new String[] { "Runaway-Food-Truck" }; 

	private long Rythme=5000;
    private long m_textElapsed;
	private long m_timekey;
	private boolean authorised = false;

	/*
	 * This method is invoked almost periodically, given the number of milli-seconds
	 * that elapsed since the last time this method was invoked.
	 */
	void tick(long elapsed) {
		
			
			// Check if the game is authorized to proceed
			if (m_grille.IsAuthorised()) {
				authorised = true;
			}
			
			// If the game is authorized, check if it becomes unauthorized
			if (authorised == true) {
   	 				if (m_grille.IsAuthorised() == false) {
					m_timekey = 0;
					m_control.step();
					authorised=false;
					m_grille.switchAuthorised();
					
				}
			}
			
			// Update the game grid
			m_grille.tick(elapsed);

			// Update every second
			// the text on top of the frame: tick and fps
			m_textElapsed += elapsed;
			if (m_textElapsed > 1000) {
				m_textElapsed = 0;
				float period = m_canvas.getTickPeriod();
				int fps = m_canvas.getFPS();

				String txt = "Tick=" + period + "ms";
				while (txt.length() < 15)
					txt += " ";
				txt = txt + fps + " fps   ";
				m_text.setText(txt);
			}

			// Update the time key and check if it exceeds the rhythm
			m_timekey += elapsed;
			if (m_timekey > Rythme) {
				m_timekey = 0;
				m_grille.switchAuthorised();
				
			}
		}
	
		
  
      
	


	/*
	 * This request is to paint the Game Canvas, using the given graphics. This is
	 * called from the GameCanvasListener, called from the GameCanvas.
	 */
	void paint(Graphics g) {

		// get the size of the canvas
		int width = m_canvas.getWidth();
		int height = m_canvas.getHeight();

		// erase background
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);

		// paint
		m_grille.paint(g, width, height);
	}

}
