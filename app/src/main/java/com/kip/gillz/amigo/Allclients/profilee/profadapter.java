package com.kip.gillz.amigo.Allclients.profilee;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kip.gillz.amigo.R;

import java.util.List;


public class profadapter extends RecyclerView.Adapter<profadapter.ProductViewHolder> {
    private Context mCtx;
    private List<profmodel> CList;
    private FragmentManager supportFragmentManager;
    private Dialog dialog;
    public ImageButton bt_expand;
    public View lyt_expand;
    public View lyt_parent;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;

    PreferenceManager prefManager;

    public profadapter(Context mCtx, List<profmodel> CList) {
        this.mCtx = mCtx;
        this.CList = CList;
        dialog = new Dialog(mCtx);
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_readings, null);
        return new ProductViewHolder(view);

    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {


        String xyz="";
        final profmodel client = CList.get(position);
        holder.tv1.setText(String.valueOf(client.getMeterno()));
        holder.tv2.setText(client.getDate_Scanned());
        holder.tv3.setText(String.valueOf(client.getConsumption()));
        holder.tv4.setText(String.valueOf(client.getAmount()));
        holder.tv5.setText(String.valueOf(client.getAmount()));
        //holder.tv6.setText(String.valueOf(client.getMeterno()));

    }
    @Override
    public int getItemCount() {
        return CList.size();
    }
    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }
    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3,tv4,tv5,tv6;
        CardView cardView;
        public ProductViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.mn);
            tv2 = itemView.findViewById(R.id.ds);
            tv3 = itemView.findViewById(R.id.uc);
            tv4 = itemView.findViewById(R.id.ac);
            tv5 = itemView.findViewById(R.id.td);
            tv6 = itemView.findViewById(R.id.stat);

            bottom_sheet = itemView.findViewById(R.id.bottom_sheet);
        }
    }


}