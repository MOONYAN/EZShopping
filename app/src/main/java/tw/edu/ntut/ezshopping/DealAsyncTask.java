package tw.edu.ntut.ezshopping;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import tw.edu.ntut.ezshopping.FireField.FireRecord;

/**
 * Created by Owen on 09/11/2016.
 */
public class DealAsyncTask extends AsyncTask<FireRecord, Void, String>
{
    private final static int PORT = 11000;
    private final static String TAG = "DealAsyncTask";
    private Socket _socket;
    private String _hostIp;
    private DealResponse _responseListener;



    public DealAsyncTask(String hostIp, DealResponse responseListener)
    {
        _hostIp = hostIp;
        _responseListener = responseListener;
    }

    @Override
    protected String doInBackground(FireRecord... records)
    {
        FireRecord record = records[0];
        String passString = new Gson().toJson(record);
        String responseString;
        try
        {
            Log.d(TAG, "-------------------------------run: begin");
            _socket = new Socket(_hostIp, PORT);
            OutputStream outputStream = _socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(passString);
            bufferedWriter.flush();

            InputStream inputStream = _socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            responseString = bufferedReader.readLine();
            bufferedWriter.close();
            bufferedReader.close();
            _socket.close();
            Log.d(TAG, "-------------------------------run: end");
        }
        catch (IOException e)
        {
            Log.d(TAG, "-------------------------------run: exception");
            e.printStackTrace();
            responseString = "exception occurs";
        }

        return responseString;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
        _responseListener.onResponse(s);
    }

    public interface DealResponse
    {
        void onResponse(String responseMessage);
    }
}