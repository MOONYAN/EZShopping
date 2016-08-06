package tw.edu.ntut.ezshopping;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Owen on 08/06/2016.
 */
@SuppressLint("NewApi")
public class LoadImageTask extends AsyncTask<String,Void,Bitmap>
{
    ImageView _imageView;

    public LoadImageTask(ImageView imageView)
    {
        _imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings)
    {
        String url = strings[0];
        Bitmap bitmap = null;
        try
        {
            InputStream inputStream = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        catch (IOException e)
        {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap)
    {
        _imageView.setImageBitmap(bitmap);
        super.onPostExecute(bitmap);
    }
}
