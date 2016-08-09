package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import tw.edu.ntut.ezshopping.FireField.FireProduct;
import tw.edu.ntut.ezshopping.ModelField.Cart;
import tw.edu.ntut.ezshopping.ModelField.CartItem;
import tw.edu.ntut.ezshopping.ModelField.Model;

public class ScanActivity extends BaseActivity
{
    private static final String TAG = "ScanActivity";
    private TextView _productIdText;
    private TextView _nameText;
    private TextView _unitPriceText;
    private ImageView _urlImageView;
    private FireProduct _fireProduct;
    private String _productId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        processViews();
        processControllers();
        startScan();
    }

    private void processViews()
    {
        _productIdText = (TextView) findViewById(R.id.product_id_text);
        _nameText = (TextView) findViewById(R.id.name_text);
        _unitPriceText = (TextView) findViewById(R.id.unit_price_text);
        _urlImageView = (ImageView) findViewById(R.id.url_image_view);
    }

    private void processControllers()
    {

    }

    private void startScan()
    {
        IntentIntegrator integrator = new IntentIntegrator(ScanActivity.this);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data)
    {
        if (data != null)
        {
            showProgressDialog();
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            _productId = scanningResult.getContents();
            _productIdText.setText(_productId);
            Toast.makeText(this, _productId, Toast.LENGTH_SHORT).show();
            FirebaseDatabase.getInstance().getReference("product/" + _productId).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    _fireProduct = dataSnapshot.getValue(FireProduct.class);
                    if (_fireProduct == null) finish();
                    else
                    {
                        Log.d(TAG, "onDataChange: " + _fireProduct.Name);
                        Log.d(TAG, "onDataChange: " + _fireProduct.ImageURL);
                        Log.d(TAG, "onDataChange: " + _fireProduct.UnitPrice);
                        updateUI();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    Log.w(TAG, "getProduct:onCancelled", databaseError.toException());
                    _fireProduct = null;
                    finish();
                }
            });
        }
        else
        {
            Toast.makeText(this, "nothing", Toast.LENGTH_SHORT).show();
            Log.d("----------", "onActivityResult: has nothing");
            finish();
        }
    }

    private void updateUI()
    {
        hideProgressDialog();
        _nameText.setText(_fireProduct.Name);
        _unitPriceText.setText(_fireProduct.UnitPrice + "");
        new LoadImageTask(_urlImageView).execute(_fireProduct.ImageURL);
    }

    public void addOnClick(View view)
    {
        Cart cart = Model.getInstance().getCart();
        cart.addToCart(new CartItem(_productId, _fireProduct));
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        finish();
    }
}
