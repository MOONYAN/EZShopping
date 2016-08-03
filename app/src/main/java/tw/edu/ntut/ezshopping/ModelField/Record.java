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
    public String Uid;
    public String TransactionTime;
    public Cart Cart;

    public Record()
    {

    }

    public Record(String uid,Cart cart)
    {
        Uid = uid;
        Cart = cart;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        TransactionTime = dateFormat.format(new Date(System.currentTimeMillis()));
    }
}
