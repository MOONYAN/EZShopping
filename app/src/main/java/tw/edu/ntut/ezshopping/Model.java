package tw.edu.ntut.ezshopping;

/**
 * Created by Owen on 07/30/2016.
 */
public class Model
{
    private static Model _model = new Model();
    private Cart _cart;

    private Model()
    {
        _cart = new Cart();
    }

    public static Model getInstance()
    {
        return _model;
    }

    public Cart getCart()
    {
        return _cart;
    }
}
