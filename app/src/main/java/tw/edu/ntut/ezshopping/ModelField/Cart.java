package tw.edu.ntut.ezshopping.ModelField;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owen on 07/30/2016.
 */
@IgnoreExtraProperties

public class Cart
{
    private List<CartItem> _itemList;

    private int _totalCost = 0;

    public Cart()
    {
        _itemList = new ArrayList<>();
    }

    public List<CartItem> getItemList()
    {
        return _itemList;
    }

    public int getTotalCost()
    {
        return _totalCost;
    }

    public void setCartItem(int position, CartItem cartItem)
    {
        _itemList.set(position, cartItem);
        updateTotalCost();
    }

    public void removeCartItem(int position)
    {
        _itemList.remove(position);
        updateTotalCost();
    }

    public void addToCart(CartItem newItem)
    {
        for (CartItem cartItem : _itemList)
        {
            if (cartItem.getProductId().equals(newItem.getProductId()))
            {
                cartItem.addCount(newItem.getCount());
                updateTotalCost();
                return;
            }
        }
        _itemList.add(newItem);
        updateTotalCost();
    }

    private void updateTotalCost()
    {
        _totalCost = 0;
        for (CartItem cartItem : _itemList)
        {
            _totalCost += cartItem.getUnitPrice() * cartItem.getCount();
        }
    }
}
