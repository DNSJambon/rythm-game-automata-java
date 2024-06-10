package info3.game.model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.controller.*;
import info3.game.model.Entities.Entity;
import info3.game.model.Entities.MazeSolver;
import info3.game.model.Entities.Obstacle;
import info3.game.model.Entities.Player1;
import info3.game.model.Entities.Snake;


public class Grille implements IGrille{
    cell[][] grille;
    int rows;
    int cols;
    long m_imageElapsed;

    Control m_control;


    BufferedImage[] m_images;
    Entity main_Entity;
    int viewport_size = 7;

    boolean authorised;
    char touche;
    

    public Grille(int rows, int cols, Control m_control) throws IOException {
        m_images = loadSprite("resources/tiles.png", 24, 21);
        this.rows = rows;
        this.cols = cols;
        this.m_control = m_control;
        this.authorised = true;

        // Création de la grille
        grille = new cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grille[i][j] = new cell(this, j, i);
            }
        }
        //ajoute player1
        Player1 p = new Player1(this);
        m_control.addEntity(p);
        MazeSolver m = new MazeSolver(this, 0, 0);
        main_Entity = m;
        m_control.addEntity(m);
        // ajout des obstacles aléatoirements
        Obstacle o;
        for (int i = 0; i <150; i++) {
            cell c = randomCell_libre();
            o = new Obstacle(this, c.getCol(), c.getRow());
            m_control.addEntity(o);
            //c.setEntity(o);
        }

    }
   
    public char getTouche() {
        return touche;
    }

    public void setAuthorised(boolean authorised) {
        this.authorised = authorised;
    }
    
    public boolean IsAuthorised(){
        return this.authorised;
    }

    public void switchAuthorised(){
        this.authorised = !this.authorised;
    }

    public void resetTouche() {
        this.touche = ' ';
    }

    public void key(char touche) {
        if (this.authorised==true){
            this.touche = touche;
            this.authorised = false;
        }
    }   
    
    public Grille(int rows, int cols) throws IOException {
        m_images = loadSprite("resources/tiles.png", 24, 21);
        this.rows = rows;
        this.cols = cols;

        // Création de la grille
        grille = new cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grille[i][j] = new cell(this, i, j);
            }
        }
    }

    public cell getCell(int col, int row) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null;
        }
        return grille[row][col];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public BufferedImage getImage(int index) {
        return m_images[index];
    }
    

    public cell randomCell_libre() {
        int x = (int) (Math.random() * rows);
        int y = (int) (Math.random() * cols);
        while (grille[x][y].getType() != cellType.Vide) {
            x = (int) (Math.random() * rows);
            y = (int) (Math.random() * cols);
        }
        return grille[x][y];

    }

    public static BufferedImage[] loadSprite(String filename, int nrows, int ncols) throws IOException {
        File imageFile = new File(filename);
        if (imageFile.exists()) {
            BufferedImage image = ImageIO.read(imageFile);
            int width = image.getWidth(null) / ncols;
            int height = image.getHeight(null) / nrows;

            BufferedImage[] images = new BufferedImage[nrows * ncols];
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    int x = j * width;
                    int y = i * height;
                    images[(i * ncols) + j] = image.getSubimage(x, y, width, height);
                }
            }
            return images;
        }
        return null;
    }
    
    public void tick(long elapsed) {
        m_imageElapsed += elapsed;

        for (Entity e : m_control.getEntities()) {
            e.tick(elapsed);
        }
        
    }

    public void paint(Graphics g, int width, int height) {
        int x_main = main_Entity.getX();
        int y_main = main_Entity.getY();

        if (x_main < viewport_size / 2) {
            x_main = viewport_size / 2;
        }     
        if (x_main > rows - viewport_size / 2- 1) {
            x_main = rows - viewport_size / 2 - 1;
        }
        if (y_main < viewport_size / 2) {
            y_main = viewport_size / 2;
        }
        if (y_main > cols - viewport_size / 2 - 1) {
            y_main = cols - viewport_size / 2 - 1;
        }
        


        //on dessine le sol en premier
        for (int j= y_main - viewport_size/2; j <= y_main + viewport_size/2; j++) {
            for (int i = x_main - viewport_size/2; i <= x_main + viewport_size/2; i++) {
                if (i % 2 == 0 && j % 2 == 0 || i % 2 == 1 && j % 2 == 1)
                    g.drawImage(m_images[0], (i - x_main + viewport_size/2) * width / viewport_size, (j - y_main + viewport_size/2) * height / viewport_size, width / viewport_size, height / viewport_size, null);
                else
                    g.drawImage(m_images[21], (i - x_main + viewport_size/2) * width / viewport_size, (j - y_main + viewport_size/2) * height / viewport_size, width / viewport_size, height / viewport_size, null);
            }
        }
        //on dessine les entités
        for (int j = y_main - viewport_size/2; j <= y_main + viewport_size/2; j++) {
            for (int i = x_main - viewport_size/2; i <= x_main + viewport_size/2; i++) {
                grille[j][i].paint(g, (i - x_main + viewport_size/2) * width / viewport_size, (j - y_main + viewport_size/2) * height / viewport_size, width / viewport_size, height / viewport_size);
            }
        }
    }
    

}
