package tw.edu.ntut.ezshopping.ModelField;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    public static CartItem ParseCartItem(FireItem fireItem)
    {
        CartItem cartItem = new CartItem();
        cartItem.setProductId(fireItem.ProductId);
        cartItem.setName(fireItem.Name);
        cartItem.setImageURL(fireItem.ImageURL);
        cartItem.setUnitPrice(fireItem.UnitPrice);
        cartItem.setSubtotal(fireItem.Subtotal);
        cartItem.setCount(fireItem.Count);
        return cartItem;
    }

    public static FireCart ParseFireCat(Cart cart)
    {
        FireCart fireCart = new FireCart();
        fireCart.Total = cart.getTotal();
        fireCart.ItemList = new ArrayList<>();
        for (CartItem cartItem : cart.getItemList())
        {
            FireItem fireItem = ParseFireItem(cartItem);
            fireCart.ItemList.add(fireItem);
        }
        return fireCart;
    }

    public static Cart ParseFireCat(FireCart fireCart)
    {
        Cart cart = new Cart();
        cart.setTotal(fireCart.Total);
        List<CartItem> itemList = new ArrayList<>();
        cart.setItemList(itemList);
        for (FireItem fireItem : fireCart.ItemList)
        {
            CartItem cartItem = ParseCartItem(fireItem);
            itemList.add(cartItem);
        }
        return cart;
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

    public static Record ParseFireRecord(FireRecord fireRecord)
    {
        Record record = new Record();
        record.setCart(ParseFireCat(fireRecord.Cart));
        record.setTransactionTime(fireRecord.TransactionTime);
        record.setUid(fireRecord.Uid);
        return record;
    }

    public static List<Record> ParseRecordList(Map<String, FireRecord> fireRecordMap)
    {
        List<Record> recordList = new ArrayList<>();
        for (FireRecord fireRecord : fireRecordMap.values())
        {
            Record record = ParseFireRecord(fireRecord);
            recordList.add(record);
        }
        Collections.sort(recordList);
        return recordList;
    }
}
