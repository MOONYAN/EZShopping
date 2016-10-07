package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import tw.edu.ntut.ezshopping.FireField.FireRecord;
import tw.edu.ntut.ezshopping.ModelField.Cart;
import tw.edu.ntut.ezshopping.ModelField.CartItem;
import tw.edu.ntut.ezshopping.ModelField.FireFactory;
import tw.edu.ntut.ezshopping.ModelField.Model;
import tw.edu.ntut.ezshopping.ModelField.Record;

public class CheckoutActivity extends BaseActivity
{
    private static final String TAG = "CheckoutActivity";
    private View _uploadButton;
    private TextView _hostIpText;
    private TextView _totalText;
    private RecyclerView _recyclerView;
    private Model _model;
    private Cart _cart;
    private String _uid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        processViews();
        initializeField();
        initializeRecyclerView();
        updateUI();
    }

    private void initializeField()
    {
        _model = Model.getInstance();
        _uid = _model.getFirebaseId();
        _cart = _model.getCart();
    }

    private void initializeRecyclerView()
    {
        final List<CartItem> list = _cart.getItemList();
        ItemAdapter _adapter = new ItemAdapter(list);
        _recyclerView.setAdapter(_adapter);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateUI()
    {
        _totalText.setText(_cart.getTotal() + "");
        _uploadButton.setEnabled(_cart.getItemList().size() > 0);
    }

    private void processViews()
    {
        _recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        _totalText = (TextView) findViewById(R.id.total_text);
        _uploadButton = findViewById(R.id.transaction_button);
        _hostIpText = (TextView) findViewById(R.id.host_ip_text);
    }

    public void uploadOnClick(View view)
    {
        showProgressDialog();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("record").push();
        Log.d(TAG, "uploadOnClick: " + reference.getKey());

        Record record = new Record(_uid, _model.getCart());
        FireRecord fireRecord = FireFactory.ParseFireRecord(record);
        reference.setValue(fireRecord, new DatabaseReference.CompletionListener()
        {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
            {
                Toast.makeText(CheckoutActivity.this, "checkout success!", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
                _model.setNewCart();
                finish();
            }
        });
    }

    private void uploadFireRecord(FireRecord fireRecord)
    {
//        showProgressDialog();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("record").push();
        Log.d(TAG, "uploadOnClick: " + reference.getKey());
        reference.setValue(fireRecord, new DatabaseReference.CompletionListener()
        {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
            {
                Toast.makeText(CheckoutActivity.this, "checkout success!", Toast.LENGTH_SHORT).show();
                hideProgressDialog();
                _model.setNewCart();
                finish();
            }
        });
    }

    public void transactionOnClick(View view)
    {
        showProgressDialog();

        Record record = new Record(_uid, _model.getCart());
        final FireRecord fireRecord = FireFactory.ParseFireRecord(record);

        new DealAsyncTask(_hostIpText.getText().toString(), new DealAsyncTask.DealResponse()
        {
            @Override
            public void onResponse(String responseMessage)
            {
                hideProgressDialog();
                Toast.makeText(CheckoutActivity.this, responseMessage, Toast.LENGTH_SHORT).show();
                if (responseMessage.equals("Success"))
                {
                    uploadFireRecord(fireRecord);
//                    _model.setNewCart();
//                    finish();
                }
            }
        }).execute(fireRecord);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data)
    {
        if (data != null)
        {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            String hostIp = scanningResult.getContents();
            _hostIpText.setText(hostIp);
            Toast.makeText(this, hostIp, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "nothing", Toast.LENGTH_SHORT).show();
            Log.d("----------", "onActivityResult: has nothing");
            finish();
        }
    }

    public void startScan(View view)
    {
        IntentIntegrator integrator = new IntentIntegrator(CheckoutActivity.this);
        integrator.initiateScan();
    }
}
