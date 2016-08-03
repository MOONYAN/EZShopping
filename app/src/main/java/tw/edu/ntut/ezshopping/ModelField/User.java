package tw.edu.ntut.ezshopping.ModelField;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Owen on 07/29/2016.
 */
@IgnoreExtraProperties
public class User
{
    public String DisplayName;
    public String Email;
    public String Uid;

    public User()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(FirebaseUser user)
    {
        DisplayName = user.getDisplayName();
        Email = user.getEmail();
        Uid = user.getUid();
    }
}
