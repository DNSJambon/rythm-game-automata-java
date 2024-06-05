package view;
import java.awt.Graphics;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTicker {
    private Timer m_timer;
    private long m_elapsed;
    private long m_lastRepaint;
    private long m_lastTick;
    private long m_lastSec;
    private int m_nTicks;
    private int m_fps;
    private int m_npaints;
    private float m_tickPeriod;

    private static final int TICK_PERIOD = 1; // tick every milli-second.
    private static final double FPS = 30.0;
    private static final int REPAINT_DELAY = (int) (1000.0 / FPS);

    private GameCanvasListener m_listener;
    private Graphics m_drawBuffer;

    public GameTicker(GameCanvasListener listener, Graphics drawBuffer) {
        this.m_listener = listener;
        this.m_drawBuffer = drawBuffer;
        createTimer();
    }

    private void createTimer() {
        int tick = TICK_PERIOD;
        m_lastTick = System.currentTimeMillis();
        m_timer = new Timer(tick, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                tick();
            }
        });
        m_timer.start();
    }

    public void tick() {
        long now = System.currentTimeMillis();
        long elapsed = (now - m_lastTick);
        m_elapsed += elapsed;
        m_lastTick = now;
        m_nTicks++;

        // compute the number of frame paints
        // during the last second
        if (now - m_lastSec > 1000L) {
            m_fps = m_npaints;
            m_lastSec = now;
            m_npaints = 0;
        }

        if (m_listener != null)
            try {
                m_listener.tick(elapsed);
            } catch (Throwable th) {
                th.printStackTrace(System.err);
            }
        elapsed = now - m_lastRepaint;
        if (elapsed > REPAINT_DELAY) {
            m_tickPeriod = (float) m_elapsed / (float) m_nTicks;
            m_tickPeriod = ((int) (m_tickPeriod * 10.0F)) / 10.0F;
            m_elapsed = 0;
            m_nTicks = 0;
            m_lastRepaint = now;

            // repainting off-screen and asking for a swap
            if (m_drawBuffer != null) {
                m_listener.paint(m_drawBuffer);
                m_npaints++;
            }
        }
    }

    public int getFPS() {
        return m_fps;
    }

    public float getTickPeriod() {
        return m_tickPeriod;
    }
}
