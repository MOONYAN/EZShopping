package tw.edu.ntut.ezshopping;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tw.edu.ntut.ezshopping.ModelField.CartItem;

/**
 * Created by Owen on 07/30/2016.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>
{
    private List<CartItem> _itemList;

    public ItemAdapter(List list)
    {
        _itemList = list;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.single_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, final int position)
    {
        CartItem item = _itemList.get(position);
        holder._nameText.setText(item.getName());
        holder._countText.setText(item.getCount()+"");
        holder._subtotalText.setText(item.getSubtotal()+"");
    }

    @Override
    public int getItemCount()
    {
        return _itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView _nameText;
        private TextView _countText;
        private TextView _subtotalText;
        private View _rootView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            _rootView = itemView;
            _nameText = (TextView) itemView.findViewById(R.id.name_text);
            _countText = (TextView) itemView.findViewById(R.id.count_text);
            _subtotalText = (TextView) itemView.findViewById(R.id.subtotal_text);
        }

        public View getRootView()
        {
            return _rootView;
        }
    }
}
