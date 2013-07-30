package cats.pchat.client.game;

import cats.pchat.client.Client;
import cats.pchat.core.connection.data.type.impl.ChangePosData;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 9:27 PM
 */
public class Game extends Canvas implements Runnable{

    private static final int SPEED = 3;

    public static final int LEFT = KeyEvent.VK_LEFT,
                            RIGHT = KeyEvent.VK_RIGHT,
                            UP = KeyEvent.VK_UP,
                            DOWN = KeyEvent.VK_DOWN;

    private static final long DELAY = 35L;

    private BufferStrategy strategy;

    private boolean left, right, up, down;

    public Game(){
        setPreferredSize(GameUtils.GAME_SIZE);
        addKeyListener(
                new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyCode()) {
                            case LEFT:
                                right = false;
                                left = true;
                                break;
                            case RIGHT:
                                left = false;
                                right = true;
                                break;
                            case UP:
                                down = false;
                                up = true;
                                break;
                            case DOWN:
                                up = false;
                                down = true;
                                break;
                            default:
                                Client.messageArea().processKey(e);
                                break;
                        }
                    }

                    public void keyReleased(final KeyEvent e) {
                        switch (e.getKeyCode()) {
                            case LEFT:
                                left = false;
                                break;
                            case RIGHT:
                                right = false;
                                break;
                            case UP:
                                up = false;
                                break;
                            case DOWN:
                                down = false;
                                break;
                        }
                    }
                }
        );
    }

    public void run(){
        while(true){
            update();
            draw();
            try{
                Thread.sleep(DELAY);
            }catch(Exception ex){}
        }
    }

    public void setup(){
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        final Thread t = new Thread(this);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    private void update(){
        if(!up && !down && !left && !right)
            return;
        final Point point = new Point(Client.profile().pos().get());
        point.x += left ? -SPEED : right ? SPEED : 0;
        point.y += up ? -SPEED : down ? SPEED : 0;
        Client.send(new ChangePosData(Client.uid(), point));
    }

    private void draw(){
        final Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
        final Dimension size = new Dimension(getSize().width + 100, getSize().height + 100);
        g.clearRect(0, 0, size.width, size.height);
        GameUtils.draw(g, Client.profile());
        Client.profiles().forEach(p -> GameUtils.draw(g, p));
        g.dispose();
        Toolkit.getDefaultToolkit().sync();
        strategy.show();
    }
}
