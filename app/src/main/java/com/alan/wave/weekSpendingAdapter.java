package com.alan.wave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class weekSpendingAdapter extends RecyclerView.Adapter<weekSpendingAdapter.ViewHolder>{

    private Context mContext;
    private List<Data> myDataList;

    public weekSpendingAdapter(Context mContext, List<Data> myDataList) {
        this.mContext = mContext;
        this.myDataList = myDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.retrieve_layout, parent, false);
        return new weekSpendingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Data data = myDataList.get(position);

        holder.item.setText("Item "+data.getItem());
        holder.amount.setText("Amount: "+data.getAmount());
        holder.date.setText("On "+data.getDate());
        holder.notes.setText("Note "+data.getNotes());
    }

    @Override
    public int getItemCount() {
        return myDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView item, amount, date, notes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item = itemView.findViewById(R.id.item);
            amount = itemView.findViewById(R.id.amount);
            date  = itemView.findViewById(R.id.date);
            notes = itemView.findViewById(R.id.note);
        }
    }
}
