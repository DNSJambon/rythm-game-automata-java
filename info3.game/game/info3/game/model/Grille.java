package info3.game.model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
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

    // Viewport
    BufferedImage[] m_images;
    Entity main_Entity;
    int viewport_size = 7;


    //Synchro
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
        for (int i = 0; i <350; i++) {
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
    

    /* -----------Paint et ticks---------------*/

    int x_main_old = 3;
    int y_main_old = 3;
    int mouvement = 8; //nombre de frame pour le decalage de la vue
    Color vide = new Color(223, 208, 184);
    Color obstacle = new Color(139, 127, 109);
    Color player = new Color(60, 91, 111);
    Color pickable = new Color(240, 194, 80);

   
    public void paint(Graphics g, int width, int height) {
        int x_main = main_Entity.getX();
        int y_main = main_Entity.getY();

        //on s'assure que la vue ne sorte pas de la grille
        if (x_main < viewport_size / 2)
            x_main = viewport_size / 2;
        if (x_main > rows - viewport_size / 2 - 1)
            x_main = rows - viewport_size / 2 - 1;
        if (y_main < viewport_size / 2)
            y_main = viewport_size / 2;
        if (y_main > cols - viewport_size / 2 - 1)
            y_main = cols - viewport_size / 2 - 1;

        //l'offset permet de faire bouger la vue de facçon fluide quand on se déplace
        int offset_x = (x_main - x_main_old) * width / viewport_size;
        int offset_y = (y_main - y_main_old) * height / viewport_size;

        //pour load la ligne d'image qui va disparaitre:
        int load_x_negatif, load_x_positif, load_y_negatif, load_y_positif;
        load_x_negatif = load_x_positif = load_y_negatif = load_y_positif = 0;
        if (offset_x > 0)
            load_x_negatif = 1;

        if (offset_x < 0)
            load_x_positif = 1;

        if (offset_y > 0)
            load_y_negatif = 1;

        if (offset_y < 0)
            load_y_positif = 1;

        //on dessine le sol en premier
        for (int j = y_main - viewport_size / 2 - load_y_negatif; j <= y_main + viewport_size / 2
                + load_y_positif; j++) {
            for (int i = x_main - viewport_size / 2 - load_x_negatif; i <= x_main + viewport_size / 2
                    + load_x_positif; i++) {
                if (i % 2 == 0 && j % 2 == 0 || i % 2 == 1 && j % 2 == 1)
                    g.drawImage(m_images[0],
                            (i - x_main + viewport_size / 2) * width / viewport_size + offset_x * mouvement / 8,
                            (j - y_main + viewport_size / 2) * height / viewport_size + offset_y * mouvement / 8,
                            width / viewport_size,
                            height / viewport_size,
                            null);
                else
                    g.drawImage(m_images[21],
                            (i - x_main + viewport_size / 2) * width / viewport_size + offset_x * mouvement / 8,
                            (j - y_main + viewport_size / 2) * height / viewport_size + offset_y * mouvement / 8,
                            width / viewport_size,
                            height / viewport_size,
                            null);
            }
        }
        //on dessine les entités
        for (int j = y_main - viewport_size / 2 - load_y_negatif; j <= y_main + viewport_size / 2
                + load_y_positif; j++) {
            for (int i = x_main - viewport_size / 2 - load_x_negatif; i <= x_main + viewport_size / 2
                    + load_x_positif; i++) {
                grille[j][i].paint(g,
                        (i - x_main + viewport_size / 2) * width / viewport_size + offset_x * mouvement / 8,
                        (j - y_main + viewport_size / 2) * height / viewport_size + offset_y * mouvement / 8,
                        width / viewport_size,
                        height / viewport_size);
            }
        }

        if (offset_x != 0 || offset_y != 0) {
            mouvement--;
            if (mouvement == -1)
                mouvement = 8;
        }
        if (mouvement == 8) {
            x_main_old = x_main;
            y_main_old = y_main;
        }

        //MiniMap
        drawMinimap(g, width, (height - 340) / 2, 340, 340);

        //TODO: 
        //ATH haut

        //TODO:
        //ATH bas

    }
    
    void drawMinimap(Graphics g, int x, int y, int width, int height) { 
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                switch (grille[j][i].getCategory()) {
                    case 'V':
                        g.setColor(vide);
                        break;
                    case 'O':
                        g.setColor(obstacle);
                        break;
                    case 'P':
                        g.setColor(pickable);
                        break;
                    case 'T':
                        g.setColor(player);
                        break;

                }
                g.fillRect(x + (i * width / cols), y + (j * height / rows), width / cols, height / rows);
            }
        }
    }
    

    
    void drawATH_haut(Graphics g, int x, int y, int width, int height) {
        //TODO

    }
    
    void drawATH_bas(Graphics g, int x, int y, int width, int height) {
        //TODO

    }
    

}
