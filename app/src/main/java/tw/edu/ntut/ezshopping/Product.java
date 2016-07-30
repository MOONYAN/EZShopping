package tw.edu.ntut.ezshopping;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Owen on 07/30/2016.
 */
@IgnoreExtraProperties
public class Product
{
    public String ImageURL;
    public String Name;
    public int UnitPrice;

    public Product()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Product(Product product)
    {
        ImageURL = product.ImageURL;
        Name = product.Name;
        UnitPrice = product.UnitPrice;
    }
}
