package tw.edu.ntut.ezshopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tw.edu.ntut.ezshopping.ModelField.Record;

/**
 * Created by Owen on 08/03/2016.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>
{
    private List<Record> _recordList;

    public RecordAdapter(List<Record> recordList)
    {
        _recordList = recordList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.single_record,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Record record = _recordList.get(position);
        holder._transactionTimeText.setText(record.getTransactionTime());
    }

    @Override
    public int getItemCount()
    {
        return _recordList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView _transactionTimeText;
        private View _rootView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            _rootView = itemView;
            _transactionTimeText = (TextView) itemView.findViewById(R.id.transaction_time_text);
        }

        public View getRootView()
        {
            return _rootView;
        }
    }
}
