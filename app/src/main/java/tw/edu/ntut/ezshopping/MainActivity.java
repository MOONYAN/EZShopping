package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preferenceName), MODE_PRIVATE);
        if (sharedPreferences.getString("FirebaseId", null) == null)
        {
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        }
        else
        {
//            Cart cart = new Cart();
//            Product item = new Product();
//            item.Name="testName";
//            item.ImageURL = "testImageUrl";
//            item.UnitPrice = 999;
//            cart.CartMap.put("testId",item);
//            Map<String,Cart> map = new HashMap<>();
//            map.put("test",cart);
//            FirebaseDatabase.getInstance().getReference().child("testMap").setValue(map);

//            FirebaseDatabase.getInstance().getReference("testMap/test").addListenerForSingleValueEvent(new ValueEventListener()
//            {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot)
//                {
//                    Cart cart = dataSnapshot.getValue(Cart.class);
//
//                    Log.d(TAG, "onDataChange: " + cart.CartMap.values().size());
//                    Log.d(TAG, "onDataChange: " + cart.CartMap.get("testGGId").Name);
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError)
//                {
//                    Log.w(TAG, "getItem:onCancelled", databaseError.toException());
//                }
//            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scanOnClick(View view)
    {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    public void cartOnClick(View view)
    {
    }

    public void logOnClick(View view)
    {
    }

    public void checkoutOnClick(View view)
    {
    }

    public void accountOnClick(View view)
    {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }
}
