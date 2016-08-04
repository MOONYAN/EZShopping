package tw.edu.ntut.ezshopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import tw.edu.ntut.ezshopping.FireField.FireRecord;
import tw.edu.ntut.ezshopping.ModelField.FireFactory;
import tw.edu.ntut.ezshopping.ModelField.Record;

public class LogActivity extends BaseActivity
{
    private static final String TAG = "CartActivity";
    private RecyclerView _recyclerView;
    private List<Record> _recordList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        processViews();
        initializeRecordList();
    }

    private void initializeRecordList()
    {
        showProgressDialog();
        FirebaseDatabase.getInstance().getReference("record").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                GenericTypeIndicator<HashMap<String, FireRecord>> genericTypeIndicator = new GenericTypeIndicator<HashMap<String, FireRecord>>() {};
//                Map<String, FireRecord> map = dataSnapshot.getValue(genericTypeIndicator );

                HashMap<String, FireRecord> fireRecordMap = dataSnapshot.getValue(genericTypeIndicator);
                _recordList = FireFactory.ParseRecordList(fireRecordMap);
                Log.d(TAG, "onDataChange: end");
                hideProgressDialog();
                initializeRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w(TAG, "getItem:onCancelled", databaseError.toException());
            }
        });
    }

    private void processViews()
    {
        _recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initializeRecyclerView()
    {
        RecordAdapter adapter = new RecordAdapter(_recordList)
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
                        Toast.makeText(LogActivity.this, position + "GG", Toast.LENGTH_SHORT);
                        Log.d(TAG, "onClick: " + position);
                        Record record = _recordList.get(position);
                        Intent intent = new Intent(LogActivity.this, ItemActivity.class);
                        intent.putExtra("cart", record.getCart());
                        startActivity(intent);
                    }
                });
            }
        };
        _recyclerView.setAdapter(adapter);
        _recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
