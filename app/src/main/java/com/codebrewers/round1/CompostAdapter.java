package com.codebrewers.round1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CompostAdapter extends RecyclerView.Adapter<CompostAdapter.viewholder> {
String[]sign,amt;
public CompostAdapter(String[] amt,String[] sign){
this.sign=sign;
this.amt=amt;
}

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.recycler_layout,viewGroup,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {
        viewholder.amount.setText(amt[i]+ " kg");
        if(sign[i].equals("+")){
            Glide.with(viewholder.img.getContext()).load(R.drawable.up).into(viewholder.img);
        }
        else
        {
            Glide.with(viewholder.img.getContext()).load(R.drawable.down).into(viewholder.img);
        }
    }

    @Override
    public int getItemCount() {
        return amt.length;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView amount;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            amount=(TextView)itemView.findViewById(R.id.amount);
            img=(ImageView)itemView.findViewById(R.id.img);

        }
    }
}
