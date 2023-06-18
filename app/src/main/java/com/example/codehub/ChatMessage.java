package com.example.codehub;

public class ChatMessage {
    private String messageId;
    private String senderId;
    private String senderName;
    private String message;
    private long timestamp;

    public ChatMessage() {
        // Empty constructor required for Firebase
    }

    public ChatMessage(String messageId, String senderId, String senderName, String message, long timestamp) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.senderName = senderName;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
