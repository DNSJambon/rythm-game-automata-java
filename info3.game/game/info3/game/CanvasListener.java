package info3.game;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import info3.game.graphics.GameCanvasListener;

public class CanvasListener implements GameCanvasListener {
  Game m_game;

  CanvasListener(Game game) {
    m_game = game;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.println("Mouse clicked: ("+e.getX()+","+e.getY()+")");
    System.out.println("   modifiers="+e.getModifiersEx());
    System.out.println("   buttons="+e.getButton());
  }

  @Override
  public void mousePressed(MouseEvent e) {
    System.out.println("Mouse pressed: ("+e.getX()+","+e.getY()+")");
    System.out.println("   modifiers="+e.getModifiersEx());
    System.out.println("   buttons="+e.getButton());
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    System.out.println("Mouse released: ("+e.getX()+","+e.getY()+")");
    System.out.println("   modifiers="+e.getModifiersEx());
    System.out.println("   buttons="+e.getButton());
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    System.out.println("Mouse entered: ("+e.getX()+","+e.getY()+")");
    System.out.println("   modifiers="+e.getModifiersEx());
    System.out.println("   buttons="+e.getButton());
  }

  @Override
  public void mouseExited(MouseEvent e) {
    System.out.println("Mouse exited: ("+e.getX()+","+e.getY()+")");
    System.out.println("   modifiers="+e.getModifiersEx());
    System.out.println("   buttons="+e.getButton());
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    System.out.println("Mouse dragged: ("+e.getX()+","+e.getY()+")");
    System.out.println("   modifiers="+e.getModifiersEx());
    System.out.println("   buttons="+e.getButton());
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    System.out.println("Mouse moved: ("+e.getX()+","+e.getY()+")");
    System.out.println("   modifiers="+e.getModifiersEx());
    System.out.println("   buttons="+e.getButton());
  }

  @Override
  public void keyTyped(KeyEvent e) {
    System.out.println("Key typed: "+e.getKeyChar()+" code="+e.getKeyCode());
  }

  @Override
  public void keyPressed(KeyEvent e) {
    System.out.println("Key pressed: "+e.getKeyChar()+" code="+e.getKeyCode());
  }

  @Override
  public void keyReleased(KeyEvent e) {
    System.out.println("Key released: "+e.getKeyChar()+" code="+e.getKeyCode());
  }

  @Override
  public void tick(long elapsed) {
    m_game.tick(elapsed);
  }

  @Override
  public void paint(Graphics g) {
    m_game.paint(g);
  }

  @Override
  public void windowOpened() {
    //m_game.loadMusic();
//    m_game.m_canvas.setTimer(6000);
  }

  @Override
  public void exit() {
  }

//  boolean m_expired;
  @Override
  public void endOfPlay(String name) {
//    if (!m_expired) // only reload if it was a forced reload by timer
     // m_game.loadMusic();
//    m_expired = false;
  }

  @Override
  public void expired() { 
    // will force a change of music, after 6s of play
//    System.out.println("Forcing an ealy change of music");
//    m_expired = true;
//    m_game.loadMusic();    
  }

}
