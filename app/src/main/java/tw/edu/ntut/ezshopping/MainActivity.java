package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import tw.edu.ntut.ezshopping.ModelField.Model;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    @Override
    protected void onResume()
    {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preferenceName), MODE_PRIVATE);

        String firebaseId = sharedPreferences.getString("FirebaseId", null);
        Model.getInstance().setFirebaseId(firebaseId);
        if (firebaseId == null)
        {
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void scanOnClick(View view)
    {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    public void cartOnClick(View view)
    {
        Intent intent = new Intent("ez.EditCart");
        startActivity(intent);
    }

    public void logOnClick(View view)
    {
        Intent intent = new Intent(this, LogActivity.class);
        startActivity(intent);
    }

    public void checkoutOnClick(View view)
    {
        Intent intent = new Intent(this,CheckoutActivity.class);
        startActivity(intent);
    }

    public void accountOnClick(View view)
    {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    public void mailOnClick(View view)
    {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("plain/text");
//        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"t102590043@ntut.org.tw"});
        Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:t102590043@ntut.org.tw"));
        intent.putExtra(Intent.EXTRA_TEXT,"test email body");
        intent.putExtra(Intent.EXTRA_SUBJECT,"contact to ez-service");
//        startActivity(Intent.createChooser(intent,"sending email"));
        startActivity(intent);
    }

    public void dmOnClick(View view)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://owen-ez-store.herokuapp.com/ListItem.html"));
        startActivity(intent);
    }

    public void callOnClick(View view)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:117"));
        startActivity(intent);
    }
}
