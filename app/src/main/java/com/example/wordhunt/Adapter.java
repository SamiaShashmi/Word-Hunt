package com.example.wordhunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.Hold;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<String> letters;
    LayoutInflater inflater;

    public Adapter(Context ctx, List<String> ltr)
    {
        this.letters = ltr;
        this.inflater = LayoutInflater.from(ctx);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.letters.setText(letters.get(position));
    }

    @Override
    public int getItemCount() {
        return letters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView letters;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            letters = itemView.findViewById(R.id.letter);
        }
    }
}
