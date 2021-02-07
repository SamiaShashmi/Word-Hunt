package com.example.wordhunt;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.Hold;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<String> letters;
    LayoutInflater inflater;
    Activity activity;

    /**
     *
     * @param act
     * @param ctx
     * @param ltr
     */
    public Adapter(Activity act, Context ctx, List<String> ltr)
    {
        this.activity = act;
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
        holder.textLetter.setText(letters.get(position));
    }

    @Override
    public int getItemCount() {
        return letters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textLetter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textLetter = itemView.findViewById(R.id.letter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textFromWord = (TextView) activity.findViewById(R.id.madeWord);
                    String word = textFromWord.getText().toString();
                    textFromWord.setText(word + letters.get(getAdapterPosition()));
                }
            });
        }
    }
}
