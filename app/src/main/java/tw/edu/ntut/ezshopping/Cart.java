package tw.edu.ntut.ezshopping;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owen on 07/30/2016.
 */
@IgnoreExtraProperties

public class Cart
{
    public List<CartItem> ItemList;

    public int TotalCost = 0;

    public Cart()
    {
        ItemList = new ArrayList<>();
    }

    public void setCartItem(int position, CartItem cartItem)
    {
        ItemList.set(position, cartItem);
        updateTotalCost();
    }

    public void removeCartItem(int position)
    {
        ItemList.remove(position);
        updateTotalCost();
    }

    public void addToCart(CartItem newItem)
    {
        for (CartItem cartItem : ItemList)
        {
            if (cartItem.ProductId.equals(newItem.ProductId) )
            {
                cartItem.addCount(newItem.Count);
                updateTotalCost();
                return;
            }
        }
        ItemList.add(newItem);
        updateTotalCost();
    }

    private void updateTotalCost()
    {
        TotalCost = 0;
        for (CartItem cartItem : ItemList)
        {
            TotalCost += cartItem.UnitPrice * cartItem.Count;
        }
    }
}
