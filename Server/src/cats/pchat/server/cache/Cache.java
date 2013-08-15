package cats.pchat.server.cache;

import cats.pchat.server.Server;
import cats.pchat.server.profile.ServerProfile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Josh
 * 14/08/13
 * 1:49 PM
 */
public final class Cache {

    private static final String EXTENSION = ".ccphat";

    private static final File BASE_DIR = new File(System.getProperty("user.home"), ".Cats Physical Chat");

    private static final File SETTINGS_DIR = new File(BASE_DIR, ".settings");
    private static final File PROFILES_DIR = new File(BASE_DIR, ".profiles");

    private static final File PROFILES_FILE = new File(PROFILES_DIR, "profiles" + EXTENSION);
    private static final File LAST_UID_FILE = new File(SETTINGS_DIR, "last_uid" + EXTENSION);

    private static long uid = 0;

    static{
        dir(BASE_DIR);
        dir(SETTINGS_DIR);
        dir(PROFILES_DIR);
        loadUid();
    }

    private Cache(){}

    private static void loadUid(){
        if(!LAST_UID_FILE.exists())
            return;
        try{
            final DataInputStream input = new DataInputStream(new FileInputStream(LAST_UID_FILE));
            uid = input.readLong();
            input.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private static void writeUid(){
        try{
            final DataOutputStream output = new DataOutputStream(new FileOutputStream(LAST_UID_FILE, false));
            output.writeLong(uid);
            output.flush();
            output.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void loadProfiles(){
        if(!PROFILES_FILE.exists())
            return;
        try{
            final DataInputStream input = new DataInputStream(new FileInputStream(PROFILES_FILE));
            while(true){
                final int length = input.readInt();
                if(length == -1)
                    break;
                final byte[] bytes = new byte[length];
                input.readFully(bytes);
                final ByteBuffer buffer = ByteBuffer.wrap(bytes);
                final ServerProfile profile = ServerProfile.decode(buffer);
                Server.profiles().add(profile);
            }
            input.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static void writeProfiles(){
        try{
            final DataOutputStream output = new DataOutputStream(new FileOutputStream(PROFILES_FILE));
            Server.profiles().forEach(
                    profile -> {
                        try{
                            final ByteBuffer buffer = profile.encode();
                            final byte[] bytes = buffer.array();
                            output.writeInt(bytes.length);
                            output.write(bytes);
                            output.flush();
                        }catch(IOException ex){
                            ex.printStackTrace();
                        }
                    }
            );
            output.writeInt(-1);
            output.flush();
            output.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public static long uid(){
        try{
            return uid++;
        }finally{
            writeUid();
        }
    }


    private static void dir(final File f){
        if(!f.exists())
            f.mkdir();
    }
}
