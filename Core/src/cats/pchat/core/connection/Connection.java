package cats.pchat.core.connection;

import cats.pchat.core.connection.event.ConnectionListener;
import cats.pchat.core.connection.event.PacketListener;
import cats.pchat.core.connection.packet.Packet;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * Josh
 * 14/08/13
 * 10:00 AM
 */
public class Connection extends Thread implements Runnable{

    private final Socket socket;
    private final DataOutputStream output;
    private final DataInputStream input;

    private final List<ConnectionListener> cListeners;
    private final List<PacketListener> pListeners;

    public Connection(final Socket socket) throws IOException {
        this.socket = socket;

        cListeners = new LinkedList<>();
        pListeners = new LinkedList<>();

        output = new DataOutputStream(socket.getOutputStream());
        output.flush();

        input = new DataInputStream(socket.getInputStream());

        setPriority(MAX_PRIORITY);
        start();
    }

    public void addConnectionListener(final ConnectionListener l){
        cListeners.add(l);
    }

    public void addPacketListener(final PacketListener l){
        pListeners.add(l);
    }

    private void fireConnectionClosed(){
        cListeners.forEach(l -> l.connectionClosed(this));
    }

    private void firePacketReceived(final Packet packet){
        pListeners.forEach(l -> l.packetReceived(this, packet));
    }

    public void run(){
        while(true){
            try{
                final int length = input.readInt();
                final byte[] array = new byte[length];
                input.readFully(array);
                final ByteBuffer buffer = ByteBuffer.wrap(array);
                final Packet packet = Packet.decode(buffer);
                firePacketReceived(packet);
            }catch(IOException ex){
                ex.printStackTrace();
                close();
                break;
            }
        }
        fireConnectionClosed();
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

    public boolean send(final Packet packet){
        try{
            final ByteBuffer buffer = packet.encode();
            final byte[] bytes = buffer.array();
            output.writeInt(bytes.length);
            output.write(bytes);
            output.flush();
            return true;
        }catch(IOException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
