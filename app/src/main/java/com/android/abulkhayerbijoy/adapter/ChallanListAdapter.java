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
import com.android.abulkhayerbijoy.model.SKU;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.startup.Startup;
import com.android.abulkhayerbijoy.utils.BanglaFontUtil;
import com.android.abulkhayerbijoy.utils.MemoHelper;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import es.dmoral.toasty.Toasty;

public class ChallanListAdapter extends RecyclerView.Adapter<ChallanListAdapter.DeliveryManChallanItemViewHolder> {

    private List<SKU> mDeliveryManChallanItems;
    private LayoutInflater layoutInflater;
    private Context context;
    private BanglaFontUtil banglaUtil = null;
    private MemoHelper mHelper;
    public android.os.Handler callback;
    public DatabaseCallRepository dbCallRepository;

    public ChallanListAdapter(List<SKU> mDeliveryManChallanItems, Handler callback) {
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

        View itemView = layoutInflater.inflate(R.layout.custom_challan_item_layout, parent, false);
        return new DeliveryManChallanItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DeliveryManChallanItemViewHolder holder, final int position) {

        banglaUtil = new BanglaFontUtil();
        SKU currentChallan = mDeliveryManChallanItems.get(position);
        EditText editTextNumberPicker = holder.itemView.findViewById(R.id.display);

        holder.tvSkuImage.setImageResource(R.drawable.sku_image);
        holder.tvSkuName.setText(currentChallan.getBanglaName());
        holder.tvSkuPrice.setText(banglaUtil.NumberToBangla(String.valueOf(currentChallan.getPcsRate())));

        String requiredStockQtyByUser = editTextNumberPicker.getText().toString();
        int challanQuantity = currentChallan.getStockQty();
        int requiredStockQtyInVal = Integer.parseInt(requiredStockQtyByUser);
        if (requiredStockQtyInVal == 0){
            holder.tvChallanQty.setText(challanQuantity <= 0 ? banglaUtil.NumberToBangla(String.valueOf(0)) : banglaUtil.NumberToBangla(String.valueOf(challanQuantity)));
            holder.tvStock.setText(banglaUtil.NumberToBangla(String.valueOf(currentChallan.remaining)));
        }

        holder.numberPicker.setValueChangedListener((value, action) -> {
            //double price = mDeliveryManChallanItems.get(position).getPcsRate();
            double price = currentChallan.getPcsRate();
            if (value < 0) {
                value = 0;

            }

            addSKUToChallan(currentChallan, value, price);
            holder.numberPicker.setValue(value);
            holder.tvChallanQty.setText(challanQuantity <= 0 ? banglaUtil.NumberToBangla(String.valueOf(0)) : banglaUtil.NumberToBangla(String.valueOf(challanQuantity)));
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
            buttonConfirm.setOnClickListener(view12 -> {

                if(editText.getText().toString().isEmpty()){

                    Toasty.error(context, "দয়া করে পরিমাণ অ্যাড করুন!").show();
                }
                else{
                    int stock = Integer.parseInt(editText.getText().toString());
                    addSKUToChallan(currentChallan, stock , mDeliveryManChallanItems.get(position).getPcsRate());

                    holder.numberPicker.setValue(Integer.parseInt(editText.getText().toString()));
                    holder.tvStock.setText(banglaUtil.NumberToBangla(String.valueOf(stock)));
                    holder.tvChallanQty.setText(challanQuantity <= 0 ? banglaUtil.NumberToBangla(String.valueOf(0)) : banglaUtil.NumberToBangla(String.valueOf(challanQuantity)));
                }
                alertDialog.cancel();
            });

            buttonCancel.setOnClickListener(view1 -> alertDialog.cancel());
        });
    }

    private void addSKUToChallan(SKU currentChallan, int value, double price) {
        Challan cItem = new Challan();
        cItem.skuId = currentChallan.skuId;
        cItem.stockQty = value;
        cItem.banglaName = currentChallan.banglaName;
        cItem.pcsRate = price;
        cItem.cartonPcsRatio = currentChallan.cartonPcsRatio;
        cItem.challanValue = value * price;

        ArrayList<Challan> items = mHelper.getChallanItems();
        ListIterator<Challan> iter = items.listIterator();
        while (iter.hasNext()) {
            if (iter.next().skuId == currentChallan.skuId) {
                iter.remove();
            }
        }

        if (value > 0) {
            items.add(cItem);
            mHelper.setChallanItems(items);
        }

        sendNotificationToUI();
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
        public final TextView tvStock;
        public final TextView tvChallanQty;
        public final NumberPicker numberPicker;


        private DeliveryManChallanItemViewHolder(View itemView) {
            super(itemView);
            tvSkuImage = itemView.findViewById(R.id.imageView_SkuImage);
            numberPicker = itemView.findViewById(R.id.numberPicker);
            tvSkuName = itemView.findViewById(R.id.textView_SkuName);
            tvSkuPrice = itemView.findViewById(R.id.textView_SkuPrice);
            tvStock = itemView.findViewById(R.id.textView_Stock);
            tvChallanQty = itemView.findViewById(R.id.textView_ChallanQuantity);
        }
    }

    private void sendNotificationToUI() {
        if (callback != null) {
            Message msg = Message.obtain();
            msg.obj = true;
            msg.setTarget(callback);
            callback.sendMessage(msg);
        }
    }
}

