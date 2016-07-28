package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scanOnClick(View view)
    {
        Intent intent = new Intent(this,ScanActivity.class);
        startActivity(intent);
//        IntentIntegrator scanIntegrator = new IntentIntegrator(MainActivity.this);
////                scanIntegrator.setCaptureLayout(R.layout.custom_layout);
//        scanIntegrator.initiateScan();
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
}
