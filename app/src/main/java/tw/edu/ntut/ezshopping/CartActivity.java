package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tw.edu.ntut.ezshopping.ModelField.Cart;
import tw.edu.ntut.ezshopping.ModelField.CartItem;
import tw.edu.ntut.ezshopping.ModelField.Model;

public class CartActivity extends AppCompatActivity
{
    private static final String TAG = "CartActivity";
    private RecyclerView _recyclerView;
    private TextView _totalText;
    private Cart _cart;
    private ItemAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initializeCart();
        processViews();
        initializeRecyclerView();
        updateUI();
    }

    private void initializeCart()
    {
        Intent intent = getIntent();
        String actionName = intent.getAction();
        if (actionName.equals("ez.ViewCart"))
        {
            _cart = (Cart) intent.getSerializableExtra("cart");
        }
        else if (actionName.equals("ez.EditCart"))
        {
            _cart = Model.getInstance().getCart();
        }
    }

    private void processViews()
    {
        _recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        _totalText = (TextView) findViewById(R.id.total_text);
    }

    private void initializeRecyclerView()
    {
        final List<CartItem> list = _cart.getItemList();
        _adapter = new ItemAdapter(list)
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
                        Toast.makeText(CartActivity.this, position + "GG", Toast.LENGTH_SHORT);
                        Log.d(TAG, "onClick: " + position);
                        CartItem cartItem = list.get(position);
                        Intent intent = new Intent(CartActivity.this, ItemActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("item", cartItem);
                        startActivityForResult(intent, 1);
                    }
                });
            }
        };
        _recyclerView.setAdapter(_adapter);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        if (resultCode == RESULT_OK)
        {
            int position = data.getIntExtra("position", -1);
            CartItem cartItem = (CartItem) data.getExtras().getSerializable("item");
            if (cartItem == null)
            {
                Log.d(TAG, "onActivityResult: null");
                _cart.removeCartItem(position);
                _adapter.notifyItemRemoved(position);
                _adapter.notifyItemRangeChanged(0, _cart.getItemList().size());
            }
            else
            {
                Log.d(TAG, "onActivityResult: nonNull");
                _cart.setCartItem(position, cartItem);
                _adapter.notifyItemChanged(position, cartItem);
            }
            updateUI();
        }
    }

    private void updateUI()
    {
        _totalText.setText(_cart.getTotal() + "");
    }
}
