package tw.edu.ntut.ezshopping;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tw.edu.ntut.ezshopping.FireField.FireRecord;
import tw.edu.ntut.ezshopping.ModelField.FireFactory;
import tw.edu.ntut.ezshopping.ModelField.Model;
import tw.edu.ntut.ezshopping.ModelField.Record;

public class CheckoutActivity extends BaseActivity
{
    private static final String TAG = "CheckoutActivity";
    private TextView _uidText;
    private TextView _totalText;
    private Model _model;
    private int _total;
    private String _uid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        processViews();
        initializeField();
        updateUI();
    }

    private void initializeField()
    {
        _model = Model.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preferenceName), MODE_PRIVATE);
        _uid = sharedPreferences.getString("FirebaseId", null);
        _total = _model.getCart().getTotalCost();
    }

    private void updateUI()
    {
        _uidText.setText(_uid);
        _totalText.setText(_total + "");
    }

    private void processViews()
    {
        _uidText = (TextView) findViewById(R.id.uid_text);
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

//        CartItem cartItem = new CartItem();
//        cartItem.ProductId = "TestProductId";
//        cartItem.Name = "testProduct";
//        cartItem.ImageURL = "testImage";
//        cartItem.UnitPrice = 100;
//        cartItem.Count = 8;
//        cartItem.Subtotal = 800;

//        List<Product> list = new ArrayList<>();
//        Product product = new Product();
//        product.Name = "testName";
//        product.UnitPrice = 10;
//        product.ImageURL = "testURL";
//        list.add(product);
//        list.add(product);
//        reference.setValue(list, new DatabaseReference.CompletionListener()
//        {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
//            {
//                Log.d(TAG, "onComplete: GGGGGGGGGGGG" );
//            }
//        });

//        hideProgressDialog();


//        reference.runTransaction(new Transaction.Handler()
//        {
//            @Override
//            public Transaction.Result doTransaction(MutableData mutableData)
//            {
//                Product product = mutableData.getValue(Product.class);
//                product.Name = "testName";
//                product.UnitPrice = 10;
//                product.ImageURL = "testURL";
//                product.Subtotal = 100;
//                product.Count = 10;
//                mutableData.setValue(product);
//                return null;
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot)
//            {
////                hideProgressDialog();
////                _model.setNewCart();
////                finish();
//            }
//        });
    }
}
