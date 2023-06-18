package com.example.codehub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.ViewHolder> {

    private List<ChatMessage> chatMessages;

    public GroupChatAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        holder.senderName.setText(chatMessage.getSenderName());
        holder.message.setText(chatMessage.getMessage());
        holder.timestamp.setText(formatTimestamp(chatMessage.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView senderName;
        TextView message;
        TextView timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.senderName);
            message = itemView.findViewById(R.id.message);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }

    private String formatTimestamp(long timestamp) {
        // Implement your custom timestamp formatting logic here
        // For example, you can use SimpleDateFormat to format the timestamp to a desired string format
        // Here's an example format: SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(new Date(timestamp))
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault());
        Date date = new Date(timestamp);
        return sdf.format(date);
    }
}

