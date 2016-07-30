package tw.edu.ntut.ezshopping;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Owen on 07/29/2016.
 */
public class User
{
    public String displayName;
    public String email;
    public String uid;

    public User()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(FirebaseUser user)
    {
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();
        this.uid = user.getUid();
    }
}
