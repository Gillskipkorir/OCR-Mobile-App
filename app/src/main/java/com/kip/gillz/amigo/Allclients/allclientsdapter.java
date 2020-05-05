package com.kip.gillz.amigo.Allclients;

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


public class allclientsdapter extends RecyclerView.Adapter<allclientsdapter.ProductViewHolder> {
    private Context mCtx;
    private List<Allclientlist> CList;
    private FragmentManager supportFragmentManager;
    private Dialog dialog;
    public ImageButton bt_expand;
    public View lyt_expand;
    public View lyt_parent;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;

    PreferenceManager prefManager;

    public allclientsdapter(Context mCtx, List<Allclientlist> CList) {
        this.mCtx = mCtx;
        this.CList = CList;
        dialog = new Dialog(mCtx);
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_people_chat, null);
        return new ProductViewHolder(view);

    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {


        String xyz="";
        final Allclientlist client = CList.get(position);
        holder.tvname.setText(client.getFname()+"  "+client.getLname());
        holder.tvmeterno.setText(String.valueOf(client.getMeterno()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx,Profile.class);

                        //stringd
                intent.putExtra("fname", client.getFname() );
                intent.putExtra("lname", client.getLname() );
                intent.putExtra("oname", client.getOname() );
                intent.putExtra("gender", client.getGender() );
                intent.putExtra("email", client.getEmail() );
                intent.putExtra("krapin", client.getKrapin() );
                intent.putExtra("adress", client.getAdress() );
                intent.putExtra("postal", client.getPostal() );
                intent.putExtra("reg_date", client.getReg_date() );
                    // Intergers
                intent.putExtra("meterno", String.valueOf(client.getMeterno()) );
                intent.putExtra("phone", String.valueOf(client.getPhone()) );
                intent.putExtra("idno", String.valueOf(client.getIdno()) );

                mCtx.startActivity(intent);

                /*mBehavior = BottomSheetBehavior.from(bottom_sheet);

                    if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }

                    final View view = new LayoutInflater(mCtx) {
                        @Override
                        public LayoutInflater cloneInContext(Context newContext) {
                            return null;
                        }
                    }.inflate(R.layout.sheet_floating, null);
                    ((TextView) view.findViewById(R.id.name)).setText(client.getFname()+"  "+client.getLname());
                    ((TextView) view.findViewById(R.id.brief)).setText(String.valueOf(client.getMeterno()));
                    ((TextView) view.findViewById(R.id.description)).setText(R.string.privacy_policy_info);
                    (view.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mBottomSheetDialog.hide();
                        }
                    });

                    (view.findViewById(R.id.submit_rating)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mCtx, "Submit Rating", Toast.LENGTH_SHORT).show();
                        }
                    });

                    mBottomSheetDialog = new BottomSheetDialog(mCtx);
                    mBottomSheetDialog.setContentView(view);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    }

                    // set background transparent
                    ((View) view.getParent()).setBackgroundColor(mCtx.getResources().getColor(android.R.color.transparent));

                    mBottomSheetDialog.show();
                    mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            mBottomSheetDialog = null;
                        }
                    });


*/
                //do show details
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