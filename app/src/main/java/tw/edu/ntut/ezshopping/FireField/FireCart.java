package tw.edu.ntut.ezshopping.FireField;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

/**
 * Created by Owen on 08/03/2016.
 */
@IgnoreExtraProperties
public class FireCart
{
    public List<FireItem> ItemList;
    public int Total;

    public FireCart()
    {
// Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}
