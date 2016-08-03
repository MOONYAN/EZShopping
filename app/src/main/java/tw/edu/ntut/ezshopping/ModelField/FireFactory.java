package tw.edu.ntut.ezshopping.ModelField;

import java.util.ArrayList;

import tw.edu.ntut.ezshopping.FireField.FireCart;
import tw.edu.ntut.ezshopping.FireField.FireItem;
import tw.edu.ntut.ezshopping.FireField.FireRecord;

/**
 * Created by Owen on 08/03/2016.
 */
public class FireFactory
{
    public static FireItem ParseFireItem(CartItem cartItem)
    {
        FireItem fireItem = new FireItem();
        fireItem.ProductId = cartItem.getProductId();
        fireItem.Subtotal = cartItem.getSubtotal();
        fireItem.UnitPrice = cartItem.getUnitPrice();
        fireItem.Count = cartItem.getCount();
        fireItem.ImageURL = cartItem.getImageURL();
        fireItem.Name = cartItem.getName();
        return fireItem;
    }

    public static FireCart ParseFireCat(Cart cart)
    {
        FireCart fireCart = new FireCart();
        fireCart.Total = cart.getTotalCost();
        fireCart.ItemList = new ArrayList<>();
        for(CartItem cartItem :cart.getItemList())
        {
            FireItem fireItem = ParseFireItem(cartItem);
            fireCart.ItemList.add(fireItem);
        }
        return fireCart;
    }

    public static FireRecord ParseFireRecord(Record record)
    {
        FireRecord fireRecord = new FireRecord();
        fireRecord.TransactionTime = record.getTransactionTime();
        fireRecord.Uid = record.getUid();
        Cart cart = record.getCart();
        fireRecord.Cart = ParseFireCat(cart);
        return fireRecord;
    }
}
