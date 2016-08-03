package tw.edu.ntut.ezshopping.FireField;

/**
 * Created by Owen on 08/03/2016.
 */
public class FireProduct implements java.io.Serializable
{
    public String ImageURL;
    public String Name;
    public int UnitPrice;
    public int Count;
    public int Subtotal;

    public FireProduct()
    {
// Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
}
