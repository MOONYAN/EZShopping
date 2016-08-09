package tw.edu.ntut.ezshopping.ModelField;

/**
 * Created by Owen on 07/30/2016.
 */
public class Model
{
    private static Model _model = new Model();
    private Cart _cart;
    private String _firebaseId;

    private Model()
    {
        _cart = new Cart();
    }

    public static Model getInstance()
    {
        return _model;
    }

    public String getFirebaseId()
    {
        return _firebaseId;
    }

    public void setFirebaseId(String firebaseId)
    {
        _firebaseId = firebaseId;
    }

    public Cart getCart()
    {
        return _cart;
    }

    public void setNewCart()
    {
        _cart = new Cart();
    }
}
