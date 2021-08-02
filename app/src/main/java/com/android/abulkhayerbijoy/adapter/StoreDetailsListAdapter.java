package com.android.abulkhayerbijoy.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.abulkhayerbijoy.R;
import com.android.abulkhayerbijoy.model.Challan;
import com.android.abulkhayerbijoy.model.OrderItem;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.startup.Startup;
import com.android.abulkhayerbijoy.utils.BanglaFontUtil;
import com.android.abulkhayerbijoy.utils.MemoHelper;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class StoreDetailsListAdapter extends RecyclerView.Adapter<StoreDetailsListAdapter.DeliveryManChallanItemViewHolder> {

    private List<Challan> mDeliveryManChallanItems;
    private LayoutInflater layoutInflater;
    private Context context;
    private BanglaFontUtil banglaUtil = null;
    private MemoHelper mHelper;
    public DatabaseCallRepository dbCallRepository;
    public android.os.Handler callback;

    public StoreDetailsListAdapter(List<Challan> mDeliveryManChallanItems, Handler callback) {
        this.mDeliveryManChallanItems = mDeliveryManChallanItems;
        this.callback = callback;
        notifyDataSetChanged();
    }

    @Override
    public DeliveryManChallanItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            context = parent.getContext();
            layoutInflater = LayoutInflater.from(parent.getContext());
            dbCallRepository = new DatabaseCallRepository(Startup.getInstance());
            mHelper = MemoHelper.instance();
        }

        View itemView = layoutInflater.inflate(R.layout.custom_store_details_item_layout, parent, false);
        return new DeliveryManChallanItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DeliveryManChallanItemViewHolder holder, final int position) {

        banglaUtil = new BanglaFontUtil();
        Challan currentChallan = mDeliveryManChallanItems.get(position);
        EditText editTextNumberPicker = holder.itemView.findViewById(R.id.display);


        holder.tvSkuImage.setImageResource(R.drawable.sku_image);
        holder.tvSkuName.setText(currentChallan.getBanglaName());
        holder.tvSkuPrice.setText(banglaUtil.NumberToBangla(String.valueOf(currentChallan.getPcsRate())));

        int remaining = currentChallan.getRemainingQty();

        if (currentChallan.orderQty != 0)
            holder.numberPicker.setValue(currentChallan.orderQty); // From Order History else 0

        holder.tvNetQuantity.setText(banglaUtil.NumberToBangla(String.valueOf(remaining)));  //STOCK -- ALWAYS SHOW IN HAND
        holder.tvNetPrice.setText(banglaUtil.NumberToBangla(String.format(Locale.getDefault(), "%.1f", currentChallan.orderQty * currentChallan.pcsRate)));
        int maxAllowedQty = remaining + currentChallan.orderQty;
        holder.numberPicker.setMax(maxAllowedQty);

        holder.numberPicker.setValueChangedListener((value, action) -> {
            double price = mDeliveryManChallanItems.get(position).getPcsRate();
            if (value < 0) {
                value = 0;

            }
            addItemsToOrder(currentChallan, value, price);
            holder.numberPicker.setValue(value);
            holder.tvNetPrice.setText(banglaUtil.NumberToBangla(String.format(Locale.getDefault(), "%.1f", value * price)));
        });

        editTextNumberPicker.setOnClickListener(view -> {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View dialogView = inflater.inflate(R.layout.custom_user_input_picker_dialog, null);
            dialogBuilder.setView(dialogView);

            EditText editText = dialogView.findViewById(R.id.editText_NumberPicker);
            Button buttonConfirm = dialogView.findViewById(R.id.button_Confirm);
            Button buttonCancel = dialogView.findViewById(R.id.button_Cancel);
            AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
            buttonConfirm.setOnClickListener(view1 -> {

                if (editText.getText().toString().isEmpty()) {
                    Toasty.error(context, "দয়া করে পরিমাণ অ্যাড করুন!").show();
                } else if (Integer.parseInt(editText.getText().toString()) <= maxAllowedQty) {

                    addItemsToOrder(currentChallan, Integer.parseInt(editText.getText().toString()), mDeliveryManChallanItems.get(position).getPcsRate());

                    holder.numberPicker.setValue(Integer.parseInt(editText.getText().toString()));
                    holder.tvNetPrice.setText(banglaUtil.NumberToBangla(String.format(Locale.getDefault(), "%.1f", Integer.parseInt(editText.getText().toString()) * mDeliveryManChallanItems.get(position).getPcsRate())));
                } else {
                    Toasty.error(context, "পর্যাপ্ত স্টক নেই!").show();
                }
                alertDialog.cancel();
            });

            buttonCancel.setOnClickListener(view12 -> alertDialog.cancel());
        });
    }

    private void addItemsToOrder(Challan currentChallan, int value, double price) {

        OrderItem cItem = new OrderItem();
        cItem.skuId = currentChallan.skuId;
        cItem.orderQty = value;
        cItem.banglaName = currentChallan.banglaName;
        cItem.pcsRate = price;
        cItem.cartonPcsRatio = currentChallan.cartonPcsRatio;

        ArrayList<OrderItem> items = mHelper.getOrderItems();
        ListIterator<OrderItem> iter = items.listIterator();
        while (iter.hasNext()) {
            if (iter.next().skuId == currentChallan.skuId) {
                iter.remove();
            }
        }
        if (value > 0) {
            items.add(cItem);
            mHelper.setOrderItems(items);
        }

        sendNotificationToUI();
    }

    private void sendNotificationToUI() {
        if (callback != null) {
            Message msg = Message.obtain();
            msg.obj = true;
            msg.setTarget(callback);
            callback.sendMessage(msg);
        }
    }

    @Override
    public int getItemCount() {
        return this.mDeliveryManChallanItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class DeliveryManChallanItemViewHolder extends RecyclerView.ViewHolder {

        public final ImageView tvSkuImage;
        public final TextView tvSkuName;
        public final TextView tvSkuPrice;
        public final TextView tvNetQuantity;
        public final TextView tvNetPrice;
        public final NumberPicker numberPicker;


        private DeliveryManChallanItemViewHolder(View itemView) {
            super(itemView);
            tvSkuImage = itemView.findViewById(R.id.imageView_SkuImage);
            numberPicker = itemView.findViewById(R.id.numberPicker);
            tvSkuName = itemView.findViewById(R.id.textView_SkuName);
            tvSkuPrice = itemView.findViewById(R.id.textView_SkuPrice);
            tvNetQuantity = itemView.findViewById(R.id.textView_NetQuantity);
            tvNetPrice = itemView.findViewById(R.id.textView_NetPrice);
        }
    }
}

