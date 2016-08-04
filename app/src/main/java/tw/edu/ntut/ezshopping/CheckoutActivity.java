package tw.edu.ntut.ezshopping;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        processViews();
        initializeField();
        initializeRecyclerView();
        updateUI();
    }

    private void initializeField()
    {
        _model = Model.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preferenceName), MODE_PRIVATE);
        _uid = sharedPreferences.getString("FirebaseId", null);
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
        _totalText.setText(_cart.getTotal() +"");
    }

    private void processViews()
    {
        _recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        _totalText = (TextView) findViewById(R.id.total_text);
    }

    public void revertOnClick(View view)
    {
        finish();
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
                hideProgressDialog();
                _model.setNewCart();
                finish();
            }
        });
    }
}
