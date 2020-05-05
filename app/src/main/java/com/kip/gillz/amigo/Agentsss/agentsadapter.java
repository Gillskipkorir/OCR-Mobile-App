package com.kip.gillz.amigo.Agentsss;

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

import com.kip.gillz.amigo.Allclients.profilee.Profile;
import com.kip.gillz.amigo.R;

import java.util.List;


public class agentsadapter extends RecyclerView.Adapter<agentsadapter.ProductViewHolder> {
    private Context mCtx;
    private List<agentsmodel> CList;
    private FragmentManager supportFragmentManager;
    private Dialog dialog;
    public ImageButton bt_expand;
    public View lyt_expand;
    public View lyt_parent;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;

    PreferenceManager prefManager;

    public agentsadapter(Context mCtx, List<agentsmodel> CList) {
        this.mCtx = mCtx;
        this.CList = CList;
        dialog = new Dialog(mCtx);
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_agents, null);
        return new ProductViewHolder(view);

    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {


        String xyz="";
        final agentsmodel client = CList.get(position);
        holder.tvname.setText(client.getUsername());
        holder.tvmeterno.setText(client.getUsername());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(mCtx,Profile.class);

                        //stringd
*//*
                intent.putExtra("email", client.getEmail() );
                intent.putExtra("krapin", client.getKrapin() );
                intent.putExtra("adress", client.getAdress() );
                intent.putExtra("postal", client.getPostal() );*//*
                intent.putExtra("reg_date", client.getReg_date() );


                mCtx.startActivity(intent);*/

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
        TextView tvname,tvmeterno;
        CardView cardView;
        public ProductViewHolder(View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.name);
            tvmeterno = itemView.findViewById(R.id.meterno);
            cardView= itemView.findViewById(R.id.card);
            bottom_sheet = itemView.findViewById(R.id.bottom_sheet);
        }
    }


}