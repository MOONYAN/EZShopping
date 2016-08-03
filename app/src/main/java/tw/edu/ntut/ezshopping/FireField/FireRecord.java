package tw.edu.ntut.ezshopping.FireField;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Owen on 08/03/2016.
 */
@IgnoreExtraProperties
public class FireRecord
{
    public String Uid;
    public String TransactionTime;
    public FireCart Cart;

    public FireRecord()
    {

    }
}
