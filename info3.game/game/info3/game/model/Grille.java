package info3.game.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import javax.imageio.ImageIO;

import gal.ast.AST;
import gal.ast.Automaton;
import gal.parser.Parser;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.controller.*;
import info3.game.controller.Conditions.Cell;
import info3.game.model.Entities.Entity;
import info3.game.model.Entities.MazeSolver;
import info3.game.model.Entities.Obstacle;
import info3.game.model.Entities.Player1;
import info3.game.model.Entities.Suiveur;
import info3.game.model.Entities.Player2;
import info3.game.model.Entities.Slime;
import info3.game.model.Entities.Squelette;


public class Grille implements IGrille {
    cell[][] grille;
    int rows;
    int cols;
    long m_imageElapsed;

    Control m_control;
    public HashMap<String, Automate> automates;

    // Viewport
    BufferedImage[] m_images;
    Entity main_Entity; //(joueur 1)
    Entity joueur2; //(joueur 2)
    int viewport_size = 7;

    //Synchro
    boolean authorised;
    boolean authorised2;
    char touche;
    char touche2;

    public Grille(int rows, int cols, Control m_control, int seed, HashMap<String, Automate> automates)
            throws IOException, ClassNotFoundException {
        m_images = loadSprite("resources/tiles.png", 24, 21);
        this.rows = rows;
        this.cols = cols;
        this.m_control = m_control;
        this.automates = automates;
        this.authorised = true;
        this.authorised2 = true;

        // Création de la grille
        grille = new cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grille[i][j] = new cell(this, j, i);
            }
        }

        //======generation du labyrinthe======
        //pourcentage_aleatoire_obstacle(this, 90, 666, debut_entre_X, debut_entre_Y, fin_X, fin_Y); 
        remplir_obstacle();
        cree_des_salles(seed, 5, 5, 10, 3);
        cell c;
        //======generation des entités======

        


        //======placer le joueur 1 dans le labyrinthe======
        c = randomCell_libre();
        Player1 p1 = new Player1(this, c.getCol(), c.getRow(), automates.get("Joueur1"));

        //======placer le joueur 2 dans le labyrinthe====== 

        c = randomCell_libre();
        Player2 p2 = new Player2(this, c.getCol(), c.getRow(), automates.get("Joueur2"));
        joueur2 = p2;

        place_monstre(10);
        c = randomCell_libre();

     

        //main_Entity;
        main_Entity = p1;
        x_main_old = main_Entity.getX();
        y_main_old = main_Entity.getY();
        //on s'assure que la vue ne sorte pas de la grille
        if (x_main_old < viewport_size / 2)
            x_main_old = viewport_size / 2;
        if (x_main_old > rows - viewport_size / 2 - 1)
            x_main_old = rows - viewport_size / 2 - 1;
        if (y_main_old < viewport_size / 2)
            y_main_old = viewport_size / 2;
        if (y_main_old > cols - viewport_size / 2 - 1)
            y_main_old = cols - viewport_size / 2 - 1;
    }

    public void addEntity(Entity e) {
        m_control.addEntity(e);
    }

    
    
    //le but est de créer des salles dans le labyrinthe rempli de monstres
    //met des rectangelles de vide dans la grille
    private void cree_des_salles(long seed, int largeur,int longueur, int nb_salle, int variance) {
        Random random = new Random(seed);
        int largeur_salle = random.nextInt(variance) + largeur;
        int longueur_salle = random.nextInt(variance) + longueur;
        // tableau coordonnees des salles
        int[][] coordonnees_salle = new int[nb_salle][2];
    
        for (int i = 0; i < nb_salle; i++) {
            largeur_salle = random.nextInt(variance) + largeur;
            longueur_salle = random.nextInt(variance) + longueur;
            int x = random.nextInt(rows - largeur_salle);
            int y = random.nextInt(cols - longueur_salle);
            while (grille[x][y].getType() == cellType.Vide) {
                x = random.nextInt(rows - largeur_salle);
                y = random.nextInt(cols - longueur_salle);
            }
            coordonnees_salle[i][0] = x+largeur_salle/2;
            coordonnees_salle[i][1] = y+longueur_salle/2;
            for (int j = 0; j < largeur_salle; j++) {
                for (int k = 0; k < longueur_salle; k++) {
                    grille[x + j][y + k].resetEntity();
                }
            }
        }
    
        //creer les chemin entre les salles
    
        for (int i = 0; i < nb_salle; i++) {
            int x = coordonnees_salle[i][0];
            int y = coordonnees_salle[i][1];
            int x2 = coordonnees_salle[(i + 1) % nb_salle][0];
            int y2 = coordonnees_salle[(i + 1) % nb_salle][1];
            while (x != x2 || y != y2) {
                if (x < x2) {
                    x++;
                } else if (x > x2) {
                    x--;
                } else if (y < y2) {
                    y++;
                } else if (y > y2) {
                    y--;
                }
                grille[x][y].resetEntity();
            }
        }
    
    }
    
    //remplir la grille d'obstacle
    public void remplir_obstacle() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Obstacle o = new Obstacle(this, j, i , automates.get("MurIncassable"));
            }
        }
    }
    
    //place les monstres dans la grille a des endroit vide et aléatoire
    public void place_monstre(int nb_monstre) {
        
        for (int i = 0; i < nb_monstre; i++) {
            cell c = randomCell_libre();
            new MazeSolver(this, c.getCol(), c.getRow(), automates.get("MazeSolver"));
        }
    }
    
    
    
    /* ======================Partie Synchro========================== */
    public char getTouche() {
        return touche;
    }

    public char getTouche2() {
        return touche2;
    }

    public void setAuthorised(boolean auto) {
        if (this.authorised == auto) {
            this.authorised2 = auto;
        } else {
            this.authorised = auto;
        }

    }

    public boolean IsAuthorised() {
        if (this.authorised2 == this.authorised) {
            return this.authorised;
        } else {
            return this.authorised2;
        }

    }

    public void Authorised_True() {
        this.authorised = true;
        this.authorised2 = true;
    }

    public void Authorised_False() {
        this.authorised = false;
        this.authorised2 = false;
    }

    public void resetTouche() {
        this.touche = ' ';
        this.touche2 = ' ';
    }

    public void key(char touche) {
        if (IsAuthorised()) {
            if (this.touche != ' ') {
                this.touche2 = touche;
            } else {
                this.touche = touche;
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

    public Entity getMainEntity() {
        return main_Entity;
    }

    public cell randomCell_libre() {
        int x = (int) (Math.random() * rows);
        int y = (int) (Math.random() * cols);
        while (grille[x][y].getCategory() != 'V') {
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

    

    /*=========================Paint et ticks=============================*/

    public void tick(long elapsed) {
        m_imageElapsed += elapsed;

        for (Entity e : m_control.getEntities()) {
            e.tick(elapsed);
        }

    }

    int x_main_old;
    int y_main_old;
    int frames_anim = 10;
    int mouvement = frames_anim; //nombre de frame pour le decalage de la vue
    float[] slide = {1.0f, 0.7f, 0.36f, 0.22f, 0.13f, 0.07f, 0.05f, 0.03f, 0.015f, 0.007f, 0.0f};
   

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
        int r;
        for (int j = y_main - viewport_size / 2 - load_y_negatif; j <= y_main + viewport_size / 2
                + load_y_positif; j++) {
            for (int i = x_main - viewport_size / 2 - load_x_negatif; i <= x_main + viewport_size / 2
                    + load_x_positif; i++) {
                if (i % 2 == 0 && j % 2 == 0 || i % 2 == 1 && j % 2 == 1)
                    r = 0;
                else
                    r = 21;
                g.drawImage(m_images[r],
                           (int) ((i - x_main + viewport_size / 2) * width / viewport_size
                                    + offset_x  * slide[frames_anim - mouvement]),
                           (int) ((j - y_main + viewport_size / 2) * height / viewport_size
                                    + offset_y * slide[frames_anim - mouvement]),
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
                        (int)((i - x_main + viewport_size / 2) * width / viewport_size + offset_x * slide[frames_anim - mouvement]),
                        (int)((j - y_main + viewport_size / 2) * height / viewport_size + offset_y * slide[frames_anim - mouvement]),
                        width / viewport_size,
                        height / viewport_size);
            }
        }

        if (offset_x != 0 || offset_y != 0) {
            mouvement--;
            if (mouvement == -1) {
                mouvement = frames_anim;
                x_main_old = x_main;
                y_main_old = y_main;
            }
        }

        //MiniMap
        drawMinimap(g, width, (height - 340) / 2, 340, 340);

        //ATH huta
        drawATH_haut(g, width, 0, 340, (height - 340) / 2);

        //ATH bas
        drawATH_bas(g, width, (height + 340) / 2, 340, (height - 340) / 2);

    }
    
    Color vide = new Color(223, 208, 184);
    Color obstacle = new Color(139, 127, 109);
    Color player = new Color(60, 91, 111);
    Color pickable = new Color(240, 194, 80);

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
                    case 'E':
                        g.setColor(Color.YELLOW);
                        break;
                    case '#':
                        g.setColor(Color.BLUE);
                        break;

                }
                g.fillRect(x + (i * width / cols), y + (j * height / rows), width / cols, height / rows);
            }
        }

        //on place la cible (jouer 2) (une croix rouge)
        g.setColor(Color.RED);
        g.fillRect(x + (joueur2.getX() * width / cols) + 2, y + (joueur2.getY() * height / rows) + 2, width / cols - 4,
                height / rows - 4);
    }

    BufferedImage[] coeur = loadSprite("resources/coeur.png", 2, 3);
    int coeur_index = 0;
    int indice = 0;
    boolean petit_coeur = false;
    int c1 = 0;
    int c2 = 0;
    int c3 = 0;

    void drawATH_haut(Graphics g, int x, int y, int width, int height) {
        //TODO:
        indice++;
        if (petit_coeur) {
            indice += 5;
        }
        if (indice >= 20) {
            coeur_index = (coeur_index + 3) % 6;
            indice = 0;
            petit_coeur = !petit_coeur;
        }

        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);

        if (IsAuthorised()) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.RED);
        }

        g.fillRect(x, y + height / 2, width, height / 2);
        //coeur
        int life = ((Player1) main_Entity).getLife();

        switch (life) {
            case 5:
                c3 = 1;
                break;

            case 4:
                c3 = 2;
                break;
            case 3:
                c2 = 1;
                c3 = 2;
                break;
            case 2:
                c2 = 2;
                c3 = 2;
                break;
            case 1:
                c1 = 1;
                c2 = 2;
                c3 = 2;
                break;
            case 0:
                c1 = 2;
                c2 = 2;
                c3 = 2;
                break;
        }
        g.drawImage(coeur[coeur_index + c1], x + 20, y + 5, 100, 100, null);
        g.drawImage(coeur[coeur_index + c2], x + 120, y + 5, 100, 100, null);
        g.drawImage(coeur[coeur_index + c3], x + 220, y + 5, 100, 100, null);

    }

    void drawATH_bas(Graphics g, int x, int y, int width, int height) {

        g.setColor(Color.BLACK);

        g.fillRect(x, y, width, height);

        //affichage cooldown
        int cooldown = ((Player2) joueur2).getCooldown_egg();
        g.setColor(Color.PINK);
        g.fillRect(x, y, width / 3, height / 3 * (3 - cooldown));

    }




    /* 
    private int pourcentage_aleatoire_obstacle(Grille grille, int pourcentage, long seed, int startX, int startY,
            int endX, int endY) {
        if (pourcentage < 0 || pourcentage > 100) {
            return -1;
        }
    
        int totalCells = grille.rows * grille.cols;
        int numObstacles = (totalCells * pourcentage) / 100;
    
        Random random = new Random(seed);
        List<cell> emptyCells = new ArrayList<>();
    
        for (int i = 0; i < grille.rows; i++) {
            for (int j = 0; j < grille.cols; j++) {
                if (grille.grille[i][j].pas_obstacle() && !(i == 0 && j == 0)) {
                    emptyCells.add(grille.grille[i][j]);
                }
            }
        }
        
        Collections.shuffle(emptyCells, random);
        // System.out.println("print cells : "+emptyCells);
        boolean[][] tempObstacles = new boolean[grille.rows][grille.cols];
        for (int i = 0; i < grille.rows; i++) {
            for (int j = 0; j < grille.cols; j++) {
                tempObstacles[i][j] = !grille.grille[i][j].pas_obstacle();
            }
        }
    
        int obstaclesPlaced = 0;
        for (int i = 0; i < emptyCells.size() && obstaclesPlaced < numObstacles; i++) {
            cell c = emptyCells.get(i);
            tempObstacles[c.getRow()][c.getCol()] = true;
    
            if (chemin_existe(tempObstacles, startX, startY, endX, endY) ) {
                Obstacle o = new Obstacle(this, c.getCol(), c.getRow(), automates.get("MurIncassable"));
                obstaclesPlaced++;
            } else {
                tempObstacles[c.getRow()][c.getCol()] = false;
            }
        }
        return 0;
    }
    
    // L'idéale est d'utiliser un algo de parcours en largeur afin de vérifier
    // qu'une grille est faisable
    // pour l'instant on fait que pour 2 cases // 3 cases à faire après( porte, clée
    // , case de départ )
    private boolean chemin_existe(boolean[][] obstacles, int startX, int startY, int endX, int endY) {
        if (startX == endX && startY == endY) {
            return true;
        }
        boolean[][] visitedFromStart = new boolean[rows][cols];
        boolean[][] visitedFromEnd = new boolean[rows][cols];
        Queue<int[]> queueStart = new LinkedList<>();
        Queue<int[]> queueEnd = new LinkedList<>();
    
        queueStart.add(new int[] { startX, startY });
        queueEnd.add(new int[] { endX, endY });
    
        visitedFromStart[startX][startY] = true;
        visitedFromEnd[endX][endY] = true;
    
        int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }; 
    
        while (!queueStart.isEmpty() && !queueEnd.isEmpty()) {
            if (avancer_largeur(queueStart, visitedFromStart, visitedFromEnd, obstacles, directions)) {
                return true;
            }
            if (avancer_largeur(queueEnd, visitedFromEnd, visitedFromStart, obstacles, directions)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean avancer_largeur(Queue<int[]> queue, boolean[][] visited, boolean[][] visitedOther,
            boolean[][] obstacles, int[][] directions) {
        int[] current = queue.poll();
        int x = current[0];
        int y = current[1];
    
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
    
            if (Valid(newX, newY) && !obstacles[newX][newY] && !visited[newX][newY]) {
                if (visitedOther[newX][newY]) {
                    return true;
                }
    
                visited[newX][newY] = true;
                queue.add(new int[] { newX, newY });
            }
        }
    
        return false;
    }
    private boolean Valid(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }
        */

}