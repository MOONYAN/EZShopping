package tw.edu.ntut.ezshopping;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity
{
    private RecyclerView _recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        processViews();
        initializeRecyclerView();
    }

    private void processViews()
    {
        _recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initializeRecyclerView()
    {
        List list =  Model.getInstance().getCart().ItemList;
        ItemAdapter adapter = new ItemAdapter(list);
        _recyclerView.setAdapter(adapter);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
