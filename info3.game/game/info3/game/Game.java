package info3.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jcraft.jogg.Buffer;

import javax.swing.JFrame;
import javax.swing.JLabel;

import gal.ast.AST;
import gal.parser.Parser;
import info3.game.graphics.GameCanvas;
import info3.game.sound.RandomFileInputStream;

import info3.game.model.*;
import info3.game.controller.*;


public class Game {

	static Game game;
	boolean Jump = false;

	public int bpm;

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
	JLabel m_calib;
	GameCanvas m_canvas;
    CanvasListener m_listener;
	Grille m_grille;
	Control m_control;
	

	//Sound m_music;

	Game() throws Exception {
		
		// creating a cowboy, that would be a model
		// in an Model-View-Controller pattern (MVC)
		m_control = new Control();

		
		m_grille = config("game/info3/game/config.json");
		// creating a listener for all the events
		// from the game canvas, that would be
		// the controller in the MVC pattern
		m_listener = new CanvasListener(this);
		// creating the game canvas to render the game,
		// that would be a part of the view in the MVC pattern
		m_canvas = new GameCanvas(m_listener);

		System.out.println("  - creating frame...");
		Dimension d = new Dimension(1103, 803);
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

	//parse the json file to get the configuration of the game
	Grille config(String config_file) {
		//automates = loadAutomate("game/info3/game/model/Automates/"+automate_file);
		int seed;
		String automate_file;
		List<Automate> automates;
		HashMap<String, Automate> entities_automates = new HashMap<>();

		try {
			Path path = Path.of(config_file);
			String reader =  Files.readString(path);
			JSONObject config = new JSONObject(reader);

			int rythm = config.getInt("rythm");
			if (rythm == 0) {
				Jump = true;
				decision = 100000;
				freeze = 500;				//on freeze seulement le temps d'animation
			}
			else {
				//jeu basé sur le rythme
				bpm = config.getInt("bpm");
				Jump = false;
				decision = 200;
				freeze = 60000/bpm - 200;
			}

			seed = config.getInt("seed");
			int difficulty = config.getInt("difficulty");
			automate_file = config.getString("automate_file");
			automates = loadAutomate("game/info3/game/model/Automates/" + automate_file);

			//go through the list "entities" in the json file
			JSONArray entities = config.getJSONArray("entities");
			for (int i = 0; i < entities.length(); i++) {
				JSONObject entity = entities.getJSONObject(i);
				String name = entity.getString("name");
				String auto = entity.getString("automate");
				entities_automates.put(name, getAutomate(auto, automates));
			}

			//create the grid
			Grille grille = new Grille(34, 34, m_control, seed, entities_automates);
			return grille;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	List<Automate> loadAutomate(String filename) {
        List<Automate> automates= new ArrayList<>();

        AST ast = null;
        try {
            ast = (AST) Parser.from_file(filename);
        } catch (Exception e) {e.printStackTrace();}
        Ast2Automaton visitor = new Ast2Automaton(automates);
        automates = (List<Automate>) ast.accept(visitor);
        return automates;

    }

	public Automate getAutomate(String name, List<Automate> automates) {
		for (Automate a : automates) {
			if (a.name.equals(name)) {
				return a;
			}
		}
		return null;
	}
	

	
	

	/*
	 * ================================================================ All the
	 * methods below are invoked from the GameCanvas listener, once the window is
	 * visible on the screen.
	 * ==============================================================
	 */

	
	boolean calib_done = false;
	
	private long decision;
	private long freeze;
	private long m_textElapsed;
	private long m_rythmElapsed;
	private long m_timekey;
	private long m_freeze;
	private boolean authorised = true;
	
	/*
	 * This method is invoked almost periodically, given the number of milli-seconds
	 * that elapsed since the last time this method was invoked.
	 */
	void tick(long elapsed) {
		
		m_timekey += elapsed;
		m_freeze += elapsed;

		//calibration (moche mais pas trouvé mieux pour synchroniser la musique car le temps de chargement tres variable)
		if (!calib_done && !Jump) {
			m_timekey = 0;
			m_freeze = 0;
			if (!m_grille.IsAuthorised()) {
				m_grille.Authorised_True();
				calib_done = true;
				m_rythmElapsed = 0;
				m_timekey = 100; //100 car la marge d'erreur pur un click est de 200ms,
				m_freeze = 100; // donc on lance le jeu à 100ms afin d'etre au milieu de la fenetre d'erreur (+-100ms)
			}
		}
			
		
	    if (m_grille.game_over == 0) {
			
		if (Jump) { 
			if (this.authorised) {
				if (m_grille.IsAuthorised() == false) {
					m_timekey = 0;
					m_control.step();
					m_grille.resetTouche();
					this.authorised = false;
					m_freeze=0;					
				}
				if (m_timekey > decision) {
					m_timekey = 0;
					m_control.step();
					m_grille.resetTouche();
					m_grille.Authorised_False();
					this.authorised = false;
					m_freeze=0;		
				}
			}
			else{
				if (m_freeze > freeze) {
					m_freeze = 0;
					m_grille.Authorised_True();
					authorised = true;
					m_timekey = 0;
				}
			}
		}
		else {
			if (this.authorised) {

				if (m_timekey > decision) {
					m_timekey = m_timekey - decision;
					m_control.step();
					m_grille.resetTouche();
					m_grille.Authorised_False();
					this.authorised = false;
					m_freeze= m_freeze - decision;
				}
			}
			else{
				if (m_freeze > freeze) {
					m_freeze = m_freeze - freeze;
					m_grille.Authorised_True();
					authorised = true;
					m_timekey = m_timekey - freeze;
				}
			}
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
		
		//le cycle des 10 images de rythme doit durer 1 bpm
		m_rythmElapsed += elapsed;
		if (!Jump)
		if (m_rythmElapsed > (60000 / bpm / 10) & calib_done) {
			m_rythmElapsed = m_rythmElapsed - 60000 / bpm / 10;
			rythm_index = (rythm_index + 1) % 10;
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
		//System.out.println(width + " " + height);
		m_grille.paint(g, width - 340, height);
		if (!calib_done && !Jump) {
			g.setColor(Color.black);
			g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 27));
			g.fillRect(0, height / 7, width - 340, height / 7);
			g.setColor(Color.white);
			g.drawString("Calibration...", (width - 340) / 7 * 3, height / 5);
			g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
			g.drawString("Appuyez 2 fois en rythme pour commencer", (width - 340) / 7 * 2, height / 5 + 30);
			
		}
		
		

		if (m_grille.game_over == 1) {//victoire joueur 1
			g.setColor(Color.black);
			g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 27));
			g.fillRect(0, height / 7, width - 340, height / 7);
			g.setColor(Color.white);
			g.drawString("Victoire du Joueur 1", (width - 340) / 7 * 3, height / 5+30);
			

		}

		if (m_grille.game_over == 2) {//victoire joueur 2
			g.setColor(Color.black);
			g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 27));
			g.fillRect(0, height / 7, width - 340, height / 7);
			g.setColor(Color.white);
			g.drawString("Victoire du Joueur 2", (width - 340) / 7 * 3, height / 5+25);
			
		}

		if (!Jump)
			paint_rythm(g, 0, height/7*6, width-340, height/7);

	}

	BufferedImage[] rythm = Grille.loadSprite("resources/rythm.png", 10, 1);
	int rythm_index = 0;
	void paint_rythm(Graphics g, int x, int y, int width, int height){
		g.drawImage(rythm[rythm_index], x, y, width, height, null);
	}
	
	

	//====================MUSIC====================
	/*
	 * Called from the GameCanvas listener when the frame
	 */
	String m_musicName;

	void loadMusic() {
		m_musicName = m_musicNames[m_musicIndex];
		String filename = "resources/" + m_musicName + ".ogg";
		m_musicIndex = (m_musicIndex + 1) % m_musicNames.length;
		try {
			RandomAccessFile file = new RandomAccessFile(filename, "r");
			RandomFileInputStream fis = new RandomFileInputStream(file);
			m_canvas.playMusic(fis, 0, 1.0F);
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(-1);
		}
	}
	
	void playBeat() {
		try {
			RandomAccessFile file = new RandomAccessFile("resources/beat.ogg", "r");
			RandomFileInputStream fis = new RandomFileInputStream(file);
			m_canvas.playSound("beat",fis, 0, 1.0F);
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	private int m_musicIndex = 0;
	private String[] m_musicNames = new String[] { "beat100" };

}
