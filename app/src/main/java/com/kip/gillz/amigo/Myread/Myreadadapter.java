package com.kip.gillz.amigo.Myread;

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
import android.widget.Toast;

import com.kip.gillz.amigo.Allclients.profilee.Profile;
import com.kip.gillz.amigo.R;

import java.util.List;


public class Myreadadapter extends RecyclerView.Adapter<Myreadadapter.ProductViewHolder> {
    private Context mCtx;
    private List<Myreadmodel> CList;
    private FragmentManager supportFragmentManager;
    private Dialog dialog;
    public ImageButton bt_expand;
    public View lyt_expand;
    public View lyt_parent;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;

    PreferenceManager prefManager;

    public Myreadadapter(Context mCtx, List<Myreadmodel> CList) {
        this.mCtx = mCtx;
        this.CList = CList;
        dialog = new Dialog(mCtx);
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_week, null);
        return new ProductViewHolder(view);

    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {


        String xyz="";
        final Myreadmodel client = CList.get(position);
        holder.tvdate.setText(client.getDate_Scanned());
        holder.tvscans.setText(String.valueOf(client.getScans()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mCtx, "Coming Soon", Toast.LENGTH_LONG).show();

                           }
        });


    }
    @Override
    public int getItemCount() {
        return CList.size();
    }
    public FragmentManager getSupportFragmentManager() {
        return supportFragmentManager;
    }
    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvdate,tvscans;
        CardView cardView;
        public ProductViewHolder(View itemView) {
            super(itemView);
            tvdate = itemView.findViewById(R.id.ddd);
            tvscans = itemView.findViewById(R.id.rrr);
            cardView= itemView.findViewById(R.id.card);
        }
    }


}