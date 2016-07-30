package tw.edu.ntut.ezshopping;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Owen on 07/30/2016.
 */
@IgnoreExtraProperties
public class Item
{
    public String ImageURL;
    public String Name;
    public int UnitPrice;

    public Item()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}
