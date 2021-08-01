package com.android.abulkhayerbijoy.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.abulkhayerbijoy.R;
import com.android.abulkhayerbijoy.databinding.CustomOutletListItemLayoutBinding;
import com.android.abulkhayerbijoy.model.OutletDetail;
import com.android.abulkhayerbijoy.utils.BanglaFontUtil;
import com.android.abulkhayerbijoy.utils.SystemConstants;
import com.android.abulkhayerbijoy.utils.SystemHelper;

import java.util.List;


public class SalesOutletListAdapter extends RecyclerView.Adapter<SalesOutletListAdapter.MyViewHolder> {

    private List<OutletDetail> itemList;
    private LayoutInflater layoutInflater;
    private OrderItemListener listener;
    private BanglaFontUtil banglaUtil = null;
    private int status;
    public Context context;

    public SalesOutletListAdapter(List<OutletDetail> itemList, OrderItemListener listener, int status) {
        this.itemList = itemList;
        this.listener = listener;
        this.status = status;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            context = parent.getContext();
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CustomOutletListItemLayoutBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.custom_outlet_list_item_layout, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        banglaUtil = new BanglaFontUtil();
        OutletDetail item = itemList.get(position);
        holder.binding.setObj(itemList.get(position));

        if (status == SystemConstants.STATUS_VISITED) {
            holder.binding.outletHeader.setBackgroundResource(R.drawable.curved_button_completed);

        } else if (status == SystemConstants.STATUS_VISITED_AND_PENDING) {
            holder.binding.outletHeader.setBackgroundResource(R.drawable.curved_button_not_completed);

        }

        holder.binding.textViewOutletName.setText(TextUtils.isEmpty(item.banglaName) ? item.getName() : item.getBanglaName());
        holder.binding.textViewOwnerName.setText(item.getOwnerName());
        holder.binding.textViewPhoneNumber.setText(item.getContactNo());
        holder.binding.textViewDueValue.setText(banglaUtil.NumberToBangla(SystemHelper.formatDoubleAsString(item.outletDue)));

        holder.binding.outletHeader.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOrderItemClicked(itemList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public interface OrderItemListener {
        void onOrderItemClicked(OutletDetail DeliveryManOrder);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final CustomOutletListItemLayoutBinding binding;

        public MyViewHolder(final CustomOutletListItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }


}

