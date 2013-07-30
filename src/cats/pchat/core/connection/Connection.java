package cats.pchat.core.connection;

import cats.pchat.core.connection.data.Data;
import cats.pchat.core.connection.event.ConnectionEvent;
import cats.pchat.core.connection.event.ConnectionListener;
import cats.pchat.core.connection.event.DataEvent;
import cats.pchat.core.connection.event.DataListener;
import cats.pchat.core.connection.event.Listener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 5:59 PM
 */
public class Connection extends Thread implements Runnable{

    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;

    private final List<Listener> listeners;

    public Connection(final Socket socket) throws IOException {
        this.socket = socket;

        listeners = new LinkedList<>();

        output = new ObjectOutputStream(socket.getOutputStream());
        output.flush();

        input = new ObjectInputStream(socket.getInputStream());

        setPriority(MAX_PRIORITY);
        start();
    }

    public void addListener(final Listener listener){
        listeners.add(listener);
    }

    public void removeListener(final Listener listener){
        listeners.remove(listener);
    }

    private void fireConnectionClosed(){
        final ConnectionEvent e = new ConnectionEvent(this);
        listeners.stream().filter(l -> l instanceof ConnectionListener).forEach(l -> ((ConnectionListener)l).onConnectionClosed(e));
    }

    private void fireDataReceived(final Data d){
        final DataEvent e = new DataEvent(this, d);
        listeners.stream().filter(l -> l instanceof DataListener).forEach(l -> ((DataListener)l).onDataReceived(e));
    }

    public void run(){
        while(true){
            try{
                final Data d = (Data)input.readObject();
                fireDataReceived(d);
            }catch(Exception ex){
                ex.printStackTrace();
                close();
                break;
            }
        }
        fireConnectionClosed();
    }

    public boolean send(final Data data){
        try{
            output.writeObject(data);
            output.flush();
            return true;
        }catch(IOException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean close(){
        try{
            output.close();
            input.close();
            socket.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }finally{
            return socket.isClosed();
        }
    }
}
