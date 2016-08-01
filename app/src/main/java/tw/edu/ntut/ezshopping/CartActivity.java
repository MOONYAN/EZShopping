package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class CartActivity extends AppCompatActivity
{
    private static final String TAG = "CartActivity";
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
        ItemAdapter adapter = new ItemAdapter(list)
        {
            @Override
            public void onBindViewHolder(ViewHolder holder, final int position)
            {
                super.onBindViewHolder(holder, position);
                holder.getRootView().setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Toast.makeText(CartActivity.this,position+"GG",Toast.LENGTH_SHORT);
                        Log.d(TAG, "onClick: " + position);
                        CartItem cartItem = Model.getInstance().getCart().ItemList.get(position);
                        Intent intent = new Intent(CartActivity.this,ItemActivity.class);
                        intent.putExtra("position",position);
                        intent.putExtra("item",cartItem);
                        startActivityForResult(intent,1);
                    }
                });
            }
        };
        _recyclerView.setAdapter(adapter);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
