package com.android.abulkhayerbijoy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.abulkhayerbijoy.R;
import com.android.abulkhayerbijoy.model.SKU;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.startup.Startup;
import com.android.abulkhayerbijoy.utils.BanglaFontUtil;
import com.android.abulkhayerbijoy.utils.MemoHelper;
import java.util.List;

public class DepositListAdapter extends RecyclerView.Adapter<DepositListAdapter.ChallanReturnItemsViewHolder> {

    private List<SKU> mChallanReturnItems;
    private LayoutInflater layoutInflater;
    private BanglaFontUtil banglaUtil = null;
    public DatabaseCallRepository dbCallRepository;
    public MemoHelper mHelper;
    public Context context;

    public DepositListAdapter(List<SKU> mChallanReturnItems) {
        this.mChallanReturnItems = mChallanReturnItems;
        notifyDataSetChanged();
    }

    @Override
    public ChallanReturnItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            context = parent.getContext();
            layoutInflater = LayoutInflater.from(parent.getContext());
            dbCallRepository = new DatabaseCallRepository(Startup.getInstance());
            mHelper = MemoHelper.instance();
        }

        View itemView = layoutInflater.inflate(R.layout.custom_deposit_item_layout, parent, false);
        return new ChallanReturnItemsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChallanReturnItemsViewHolder holder, final int position) {

        banglaUtil = new BanglaFontUtil();
        SKU currentChallan = mChallanReturnItems.get(position);

        holder.tvSkuImage.setImageResource(R.drawable.sku_image);
        holder.tvSkuName.setText(currentChallan.getBanglaName());
        holder.tvSkuPrice.setText(banglaUtil.NumberToBangla(String.valueOf(currentChallan.getPcsRate())));
        holder.tvChallanQty.setText(currentChallan.getStockQty() <= 0 ? banglaUtil.NumberToBangla(String.valueOf(0)) : banglaUtil.NumberToBangla(String.valueOf(currentChallan.getStockQty())));
        holder.tvStock.setText(banglaUtil.NumberToBangla(String.valueOf(currentChallan.remaining)));

    }

    @Override
    public int getItemCount() {
        return this.mChallanReturnItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ChallanReturnItemsViewHolder extends RecyclerView.ViewHolder {

        public final ImageView tvSkuImage;
        public final TextView tvSkuName;
        public final TextView tvSkuPrice;
        public final TextView tvStock;
        public final TextView tvChallanQty;


        private ChallanReturnItemsViewHolder(View itemView) {
            super(itemView);
            tvSkuImage = itemView.findViewById(R.id.imageView_SkuImage);
            tvSkuName = itemView.findViewById(R.id.textView_SkuName);
            tvSkuPrice = itemView.findViewById(R.id.textView_SkuPrice);
            tvStock = itemView.findViewById(R.id.textView_Stock);
            tvChallanQty = itemView.findViewById(R.id.textView_ChallanQuantity);
        }
    }
}

