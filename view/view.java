package view;

import model.IGrille;
import model.cellType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.Timer;

public class view extends JPanel {

    IGrille grille;
    BufferedImage backgroundImage;

    public view(IGrille grille) {
        this.grille = grille;
        try {
            String userHome = System.getProperty("user.home");
            backgroundImage = ImageIO.read(new File("bg_green.jpeg"));// jsp comment adapter le path  !
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Configure the timer to call repaint periodically
        Timer timer = new Timer(250, e -> repaint());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        } else {
            g.setColor(Color.GRAY); // Utiliser un fond vert si l'image n'est pas chargée
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        // Determine the cell size
        int cellWidth = this.getWidth() / grille.getCols();
        int cellHeight = this.getHeight() / grille.getRows();

        // Draw the grid
        for (int i = 0; i < grille.getRows(); i++) {
            for (int j = 0; j < grille.getCols(); j++) {
                int x = j * cellWidth;
                int y = i * cellHeight;
                switch (grille.getCell(j, i).getType()) {
                    case Apple:
                        g.setColor(Color.RED);
                        g.fillOval(x, y, cellWidth, cellHeight);
                        break;
                    case Snake:
                        g.setColor(Color.GREEN);
                        g.fillRect(x, y, cellWidth, cellHeight);
                        break;
                    case Obstacle:
                        g.setColor(Color.GRAY);
                        g.fillRect(x, y, cellWidth, cellHeight);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public static void createAndShowGUI(IGrille grille) {
        JFrame frame = new JFrame("Grille Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set the frame size as needed

        view view = new view(grille);
        frame.add(view);
        frame.setVisible(true);
    }
}
