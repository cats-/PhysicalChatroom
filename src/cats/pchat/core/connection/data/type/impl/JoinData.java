package cats.pchat.core.connection.data.type.impl;

import cats.pchat.core.connection.data.type.ValueData;
import cats.pchat.core.profile.Profile;
import java.io.Serializable;

/**
 * Physical Chatroom
 * Josh
 * 27/07/13
 * 7:03 PM
 */
public class JoinData extends ValueData<Profile> implements Serializable {

    public JoinData(final Profile profile){
        super(JOIN, profile);
    }
}
