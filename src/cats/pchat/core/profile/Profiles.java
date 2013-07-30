package cats.pchat.core.profile;

import cats.pchat.core.connection.Connection;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 7:10 PM
 */
public class Profiles extends CopyOnWriteArrayList<Profile> implements Serializable{

    public Profile profile(final byte uid){
        return stream().filter(p -> p.equals(uid)).findFirst().orElse(null);
    }

    public Profile profile(final Profile profile){
        return profile(profile.uid().get());
    }

    public Profile profile(final Connection connection){
        return stream().filter(p -> p.connection.equals(connection)).findFirst().orElse(null);
    }

    public Profile[] array(){
        return toArray(new Profile[size()]);
    }
}
