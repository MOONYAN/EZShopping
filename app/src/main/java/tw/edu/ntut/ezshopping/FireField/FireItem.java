package tw.edu.ntut.ezshopping.FireField;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Owen on 08/03/2016.
 */
@IgnoreExtraProperties
public class FireItem
{
    public String ProductId;
    public String ImageURL;
    public String Name;
    public int UnitPrice;
    public int Count;
    public int Subtotal;

    public FireItem()
    {
// Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}
