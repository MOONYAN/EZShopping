package tw.edu.ntut.ezshopping.ModelField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owen on 07/30/2016.
 */
public class Cart implements java.io.Serializable
{
    private List<CartItem> _itemList;

    private int _total = 0;

    public Cart()
    {
        _itemList = new ArrayList<>();
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
        _total = 0;
        for (CartItem cartItem : _itemList)
        {
            _total += cartItem.getUnitPrice() * cartItem.getCount();
        }
    }

    public List<CartItem> getItemList()
    {
        return _itemList;
    }

    public void setItemList(List<CartItem> itemList)
    {
        _itemList = itemList;
    }

    public int getTotal()
    {
        return _total;
    }

    public void setTotal(int total)
    {
        _total = total;
    }
}
