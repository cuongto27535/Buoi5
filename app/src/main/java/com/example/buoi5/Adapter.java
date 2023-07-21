package com.example.buoi5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static List<model> list;
    boolean isExpandedTitle = false;

    public Adapter(Context context, List<model> list) {
        Adapter.context = context;
        Adapter.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new viewHolder(view);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        model item = list.get(position);
        if (item == null) {
            return;
        }
        String urlImage = item.getThumbnailUrl();
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            Glide.with(context)
                    .load(urlImage)
                    .into(holder.img);
        }

        holder.tvId.setText("id: " + item.getId());
        holder.tvTitle.setText("title: " + item.getTitle());
        holder.tvTitle.setOnClickListener(view -> {  // text too long , expanded
            if (isExpandedTitle) {
                holder.tvTitle.setMaxLines(2);
                isExpandedTitle = false;
            } else {
                holder.tvTitle.setMaxLines(Integer.MAX_VALUE);
                isExpandedTitle = true;
            }
        });


    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }


    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvTitle;
        ImageView img;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image);
            tvId = itemView.findViewById(R.id.tvId);
            tvTitle = itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(v -> {
                model item = list.get(getAdapterPosition());
                Toast.makeText(v.getContext(), "Title: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            });
        }
    }
}

