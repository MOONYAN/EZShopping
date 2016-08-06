package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tw.edu.ntut.ezshopping.ModelField.CartItem;

public class ItemActivity extends AppCompatActivity
{
    private static final String TAG = "ItemActivity";
    private TextView _productIdText;
    private TextView _nameText;
    private TextView _unitPriceText;
    private TextView _countText;
    private TextView _subtotalText;
    private TextView _imageURLText;
    private ImageView _urlImageView;
    private View _plusButton;
    private View _minusButton;
    private CartItem _item;
    private int _position;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Log.d(TAG, "onCreate: ");
        processViews();
        initializeItem();
        initializeUI();
    }

    private void initializeItem()
    {
        Intent intent = getIntent();
        _position = intent.getIntExtra("position", -1);
        _item = (CartItem) intent.getExtras().getSerializable("item");
    }

    private void processViews()
    {
        _productIdText = (TextView) findViewById(R.id.product_id_text);
        _nameText = (TextView) findViewById(R.id.name_text);
        _unitPriceText = (TextView) findViewById(R.id.unit_price_text);
        _countText = (TextView) findViewById(R.id.count_text);
        _subtotalText = (TextView) findViewById(R.id.subtotal_text);
        _imageURLText = (TextView) findViewById(R.id.image_URL_text);
        _plusButton = findViewById(R.id.plus_button);
        _minusButton = findViewById(R.id.minus_button);
        _urlImageView = (ImageView) findViewById(R.id.url_image_view);
    }

    private void initializeUI()
    {
        _productIdText.setText(_item.getProductId());
        _nameText.setText(_item.getName());
        _imageURLText.setText(_item.getImageURL());
        _unitPriceText.setText(_item.getUnitPrice() + "");
        new LoadImageTask(_urlImageView).execute(_item.getImageURL());
        updateUI();
    }

    private void updateUI()
    {
        _countText.setText(_item.getCount() + "");
        _subtotalText.setText(_item.getSubtotal() + "");
        _minusButton.setEnabled(_item.getCount() > 1);
    }

    public void minusOnClick(View view)
    {
        _item.addCount(-1);
        updateUI();
    }

    public void plusOnClick(View view)
    {
        _item.addCount(1);
        updateUI();
    }

    public void setOnClick(View view)
    {
        Log.d(TAG, "setOnClick: ");
        Intent intent = getIntent();
        intent.putExtra("item",_item);
        setResult(RESULT_OK,intent);
        finish();
    }

    public void deleteOnClick(View view)
    {
        Log.d(TAG, "deleteOnClick: ");
        Intent intent = getIntent();
        intent.removeExtra("item");
        setResult(RESULT_OK,intent);
        finish();
    }
}
