package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
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
