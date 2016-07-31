package tw.edu.ntut.ezshopping;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Owen on 07/30/2016.
 */
@IgnoreExtraProperties
public class CartItem
{
    public String ProductId;
    public String ImageURL;
    public String Name;
    public int UnitPrice = 0;
    public int Count = 0;
    public int Subtotal = 0;

    public CartItem()
    {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public CartItem(String productId, Product product)
    {
        ImageURL = product.ImageURL;
        Name = product.Name;
        UnitPrice = product.UnitPrice;
        ProductId = productId;
        setCount(1);
    }

    public void setCount(int count)
    {
        Count = count;
        Subtotal = Count * UnitPrice;
    }

    public void addCount(int count)
    {
        Count += count;
        Subtotal = Count * UnitPrice;
    }
}
