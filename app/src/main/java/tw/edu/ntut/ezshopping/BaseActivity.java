package tw.edu.ntut.ezshopping;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Owen on 07/30/2016.
 */
public class BaseActivity extends AppCompatActivity
{
    private ProgressDialog _progressDialog;

    protected void showProgressDialog()
    {
        if (_progressDialog == null)
        {
            _progressDialog = new ProgressDialog(this);
            _progressDialog.setMessage("Loading...");
            _progressDialog.setIndeterminate(true);
        }
        _progressDialog.show();
    }

    protected void hideProgressDialog()
    {
        if (_progressDialog != null && _progressDialog.isShowing())
        {
            _progressDialog.dismiss();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        hideProgressDialog();
    }
}
