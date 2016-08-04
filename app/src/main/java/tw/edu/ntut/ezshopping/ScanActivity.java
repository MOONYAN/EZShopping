package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
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
    private TextView _scanContentText;
    private TextView _scanFormatText;
    private TableLayout _resultLayout;
    private TextView _imageURLText;
    private TextView _nameText;
    private TextView _unitPriceText;
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
    }

    private void processViews()
    {
        _scanContentText = (TextView) findViewById(R.id.scan_content_text);
        _scanFormatText = (TextView) findViewById(R.id.scan_format_text);
        _resultLayout = (TableLayout) findViewById(R.id.result_layout);
        _imageURLText = (TextView) findViewById(R.id.image_URL_text);
        _nameText = (TextView) findViewById(R.id.name_text);
        _unitPriceText = (TextView) findViewById(R.id.unit_price_text);
    }

    private void processControllers()
    {

    }

    public void runOnClick(View view)
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
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            _scanContentText.setText(scanContent);
            _scanFormatText.setText(scanFormat);
            Log.d("----------", "onActivityResult: has something");
            Toast.makeText(this, "test" + scanContent, Toast.LENGTH_SHORT).show();
            _productId = scanContent;
            FirebaseDatabase.getInstance().getReference("product/" + _productId).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    _fireProduct = dataSnapshot.getValue(FireProduct.class);
                    if (_fireProduct == null) setDefaultState();
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
                    setDefaultState();
                }
            });
        }
        else
        {
            Toast.makeText(this, "nothing", Toast.LENGTH_SHORT).show();
            Log.d("----------", "onActivityResult: has nothing");
        }
    }

    private void updateUI()
    {
        hideProgressDialog();
        _resultLayout.setVisibility(View.VISIBLE);
        _imageURLText.setText(_fireProduct.ImageURL);
        _nameText.setText(_fireProduct.Name);
        _unitPriceText.setText(_fireProduct.UnitPrice + "");
    }

    private void setDefaultState()
    {
        hideProgressDialog();
        _scanFormatText.setText(null);
        _scanContentText.setText(null);
        _productId = null;
        _fireProduct = null;
        _resultLayout.setVisibility(View.GONE);
        _imageURLText.setText(null);
        _nameText.setText(null);
        _unitPriceText.setText(null);
    }

    public void addOnClick(View view)
    {
        Cart cart = Model.getInstance().getCart();
//        cart.addToCart(_productId, _fireProduct);
        cart.addToCart(new CartItem(_productId, _fireProduct));
        Toast.makeText(this, "complete", Toast.LENGTH_SHORT);
        setDefaultState();
    }
}
