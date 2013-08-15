package cats.pchat.client.game;

import cats.pchat.client.Client;
import cats.pchat.core.GameConstants;
import cats.pchat.core.connection.packet.Opcodes;
import cats.pchat.core.connection.packet.Packet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

/**
 * Josh
 * 14/08/13
 * 10:32 PM
 */
public class Game extends Canvas implements GameConstants, KeyListener, Runnable, Opcodes{

    private static final Paint BACKGROUND = new GradientPaint(0, 0, Color.WHITE, GAME_SIZE.width, GAME_SIZE.height, Color.GRAY);

    private static final long DELAY = 35L;

    private static final int LEFT = KeyEvent.VK_LEFT;
    private static final int RIGHT = KeyEvent.VK_RIGHT;
    private static final int UP = KeyEvent.VK_UP;
    private static final int DOWN = KeyEvent.VK_DOWN;

    private BufferStrategy strategy;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    public Game(){
        setPreferredSize(GAME_SIZE);
        setMaximumSize(GAME_SIZE);
        setMinimumSize(GAME_SIZE);
        addKeyListener(this);
    }

    public void run(){
        while(true){
            if(Client.connected)
                update();
            draw();
            try{
                Thread.sleep(DELAY);
            }catch(Exception ex){}
        }
    }

    private void update(){
        final Point location = new Point(Client.self.location);
        location.y += up ? -SPEED : down ? SPEED : 0;
        location.x += left ? -SPEED : right ? SPEED : 0;
        if(!location.equals(Client.self.location))
            Client.send(new Packet(CHANGE_LOCATION, Client.self.uid(), location));
    }

    private void draw(){
        final Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
        g.clearRect(0, 0, GAME_SIZE.width, GAME_SIZE.height);
        g.setPaint(BACKGROUND);
        g.fillRect(0, 0, GAME_SIZE.width, GAME_SIZE.height);
        if(!Client.connected){
            g.setColor(Color.RED);
            g.drawString("NOT CONNECTED", 10, 10);
            return;
        }
        Client.profiles().forEach(p -> p.draw(g));
        Client.self.draw(g);
        g.dispose();
        Toolkit.getDefaultToolkit().sync();
        strategy.show();
    }

    public void start(){
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        final Thread t = new Thread(this);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    public void keyPressed(final KeyEvent e){
        Client.inputArea.process(e);
        final int keycode = e.getKeyCode();
        if(keycode == LEFT)
            right = !(left = true);
        if(keycode == RIGHT)
            left = !(right = true);
        if(keycode == DOWN)
            up = !(down = true);
        if(keycode == UP)
            down = !(up = true);
    }

    public void keyReleased(final KeyEvent e){
        final int keycode = e.getKeyCode();
        if(keycode == LEFT)
            left = false;
        if(keycode == RIGHT)
            right = false;
        if(keycode == UP)
            up = false;
        if(keycode == DOWN)
            down = false;
    }

    public void keyTyped(final KeyEvent e){

    }
}
