package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class ScanActivity extends BaseActivity
{
    private static final String TAG = "ScanActivity";
    private TextView _scanContentText;
    private TextView _scanFormatText;
    private TableLayout _resulLayout;
    private TextView _imageURLText;
    private TextView _nameText;
    private TextView _unitPriceText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        processViews();
        processControllers();
    }

    private void processViews()
    {
        _scanContentText = (TextView) findViewById(R.id.scan_content_text);
        _scanFormatText = (TextView) findViewById(R.id.scan_format_text);
        _resulLayout = (TableLayout) findViewById(R.id.result_layout);
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
            final String itemId = scanContent;
            FirebaseDatabase.getInstance().getReference("item/" + itemId).addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    Item item = dataSnapshot.getValue(Item.class);
                    updateUI(item);
                    Log.d(TAG, "onDataChange: " + item.Name);
                    Log.d(TAG, "onDataChange: " + item.ImageURL);
                    Log.d(TAG, "onDataChange: " + item.UnitPrice);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    Log.w(TAG, "getItem:onCancelled", databaseError.toException());
                    updateUI(null);
                }
            });
        }
        else
        {
            Toast.makeText(this, "nothing", Toast.LENGTH_SHORT).show();
            Log.d("----------", "onActivityResult: has nothing");
        }
    }

    public void backOnClick(View view)
    {
        finish();
    }

    private void updateUI(@Nullable Item item)
    {
        hideProgressDialog();
        if (item == null)
        {
            _resulLayout.setVisibility(View.GONE);
            _imageURLText.setText(null);
            _nameText.setText(null);
            _unitPriceText.setText(null);
        }
        else
        {
            _resulLayout.setVisibility(View.VISIBLE);
            _imageURLText.setText(item.ImageURL);
            _nameText.setText(item.Name);
            _unitPriceText.setText(item.UnitPrice+"");
        }
    }
}
