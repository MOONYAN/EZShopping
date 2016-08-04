package tw.edu.ntut.ezshopping.ModelField;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Owen on 08/02/2016.
 */
public class Record
{
    private String _uid;
    private String _transactionTime;
    private Cart _cart;

    public Record()
    {

    }

    public Record(String uid, Cart cart)
    {
        _uid = uid;
        _cart = cart;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        _transactionTime = dateFormat.format(new Date(System.currentTimeMillis()));
    }

    public void setCart(Cart cart)
    {
        _cart = cart;
    }

    public void setTransactionTime(String transactionTime)
    {
        _transactionTime = transactionTime;
    }

    public void setUid(String uid)
    {
        _uid = uid;
    }

    public Cart getCart()
    {
        return _cart;
    }

    public String getTransactionTime()
    {
        return _transactionTime;
    }

    public String getUid()
    {
        return _uid;
    }
}
