package tw.edu.ntut.ezshopping;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Map map = Model.getInstance().getCart().CartMap;
        List list = new ArrayList(map.values());
        ItemAdapter adapter = new ItemAdapter(list);
        _recyclerView.setAdapter(adapter);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
