package tw.edu.ntut.ezshopping.ModelField;

import tw.edu.ntut.ezshopping.FireField.FireProduct;

/**
 * Created by Owen on 07/30/2016.
 */
public class CartItem implements java.io.Serializable
{
    private String _productId;
    private String _name;
    private int _unitPrice;
    private String _imageURL;
    private int _count;
    private int _subtotal;

    public CartItem()
    {
    }

    public CartItem(String productId, FireProduct fireProduct)
    {
        _name = fireProduct.Name;
        _imageURL = fireProduct.ImageURL;
        _unitPrice = fireProduct.UnitPrice;
        _productId = productId;
        setCount(1);
    }

    public void addCount(int count)
    {
        _count += count;
        _subtotal = _count * _unitPrice;
    }

    public int getCount()
    {
        return _count;
    }

    public void setCount(int count)
    {
        _count = count;
        _subtotal = _count * _unitPrice;
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

    public String getImageURL()
    {
        return _imageURL;
    }

    public void setImageURL(String imageURL)
    {
        _imageURL = imageURL;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public int getUnitPrice()
    {
        return _unitPrice;
    }

    public void setUnitPrice(int unitPrice)
    {
        _unitPrice = unitPrice;
    }
}
