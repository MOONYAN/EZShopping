package tw.edu.ntut.ezshopping.ModelField;

import com.google.firebase.database.IgnoreExtraProperties;

import tw.edu.ntut.ezshopping.FireField.FireProduct;

/**
 * Created by Owen on 07/30/2016.
 */
@IgnoreExtraProperties
public class CartItem implements java.io.Serializable
{
    private String _productId;
    private FireProduct _fireProduct;
    private int _count;
    private int _subtotal;

    public CartItem(String productId, FireProduct fireProduct)
    {
        _fireProduct = fireProduct;
        _productId = productId;
        setCount(1);
    }

    public void addCount(int count)
    {
        _count += count;
        _subtotal = _count * _fireProduct.UnitPrice;
    }

    public int getCount()
    {
        return _count;
    }

    public void setCount(int count)
    {
        _count = count;
        _subtotal = _count * _fireProduct.UnitPrice;
    }

    public String getProductId()
    {
        return _productId;
    }

    public void setProductId(String productId)
    {
        _productId = productId;
    }

    public int getSubtotal()
    {
        return _subtotal;
    }

    public void setSubtotal(int subtotal)
    {
        _subtotal = subtotal;
    }

    public String getName()
    {
        return _fireProduct.Name;
    }

    public int getUnitPrice()
    {
        return _fireProduct.UnitPrice;
    }

    public String getImageURL()
    {
        return _fireProduct.ImageURL;
    }
}
