package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity
{
    private TextView _scanContent;
    private TextView _scanFormat;

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
        _scanContent = (TextView) findViewById(R.id.scan_content);
        _scanFormat = (TextView) findViewById(R.id.scan_format);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data != null)
        {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            _scanContent.setText(scanContent);
            _scanFormat.setText(scanFormat);
            Log.d("----------", "onActivityResult: has something");
            Toast.makeText(this, "test" + scanContent, Toast.LENGTH_SHORT).show();
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
}
