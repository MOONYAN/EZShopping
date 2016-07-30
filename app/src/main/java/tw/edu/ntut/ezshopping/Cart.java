package tw.edu.ntut.ezshopping;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by Owen on 07/30/2016.
 */
@IgnoreExtraProperties

public class Cart
{
    public HashMap<String, CartItem> CartMap;

    public int TotalCost = 0;

    public Cart()
    {
        CartMap = new HashMap<>();
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public void setCartItem(String productId, CartItem cartItem)
    {
        CartMap.put(productId, cartItem);
        updateTotalCost();
    }

    public void addToCart(String productId, Product product)
    {
        CartItem cartItem = CartMap.get(productId);
        if (cartItem == null)
        {
            cartItem = new CartItem(product);
        }
        int oldCount = cartItem.Count;
        cartItem.setCount(oldCount + 1);
        CartMap.put(productId, cartItem);
        updateTotalCost();
    }

    private void updateTotalCost()
    {
        TotalCost = 0;
        for (CartItem cartItem : CartMap.values())
        {
            TotalCost += cartItem.UnitPrice * cartItem.Count;
        }
    }
}
