package tw.edu.ntut.ezshopping.ModelField;

import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Owen on 08/02/2016.
 */
@IgnoreExtraProperties
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
