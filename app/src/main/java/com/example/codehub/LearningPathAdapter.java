package com.example.codehub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LearningPathAdapter extends RecyclerView.Adapter<LearningPathAdapter.ViewHolder> {

    private List<LearningPath> learningPaths;

    public LearningPathAdapter(List<LearningPath> learningPaths) {
        this.learningPaths = learningPaths;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_learning_path, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LearningPath learningPath = learningPaths.get(position);
        holder.bindLearningPath(learningPath);
    }

    @Override
    public int getItemCount() {
        return learningPaths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iconImageView;
        private TextView titleTextView;
        private TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }

        public void bindLearningPath(LearningPath learningPath) {
            iconImageView.setImageResource(learningPath.getIconResource());
            titleTextView.setText(learningPath.getTitle());
            descriptionTextView.setText(learningPath.getDescription());
        }
    }
}

