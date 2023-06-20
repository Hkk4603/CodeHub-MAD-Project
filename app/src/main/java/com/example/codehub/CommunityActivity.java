package com.example.codehub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommunityActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GroupChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;
    private DatabaseReference groupChatRef;
    private DatabaseReference usersRef; // Add this reference for fetching user details

    private String groupId;
    private FirebaseUser currentUser; // Add this variable to store the current user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        // Get the groupId from intent or any other source
        groupId = "groupId1"; // Replace with your logic to get the groupId

        // Initialize Firebase Database references
        groupChatRef = FirebaseDatabase.getInstance().getReference().child("groups").child(groupId).child("messages");
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        // Get the current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Initialize the chatMessages list and adapter
        chatMessages = new ArrayList<>();
        chatAdapter = new GroupChatAdapter(chatMessages);

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(chatAdapter);

        // Set up the message input and send button
        EditText messageInput = findViewById(R.id.messageInput);
        ImageButton sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageInput.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    sendMessage(message);
                    messageInput.setText("");
                }
            }
        });

        // Set up Firebase Database listener to listen for new messages
        groupChatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                if (chatMessage != null) {
                    chatMessages.add(chatMessage);
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chatMessages.size() - 1);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            // Implement other ChildEventListener methods as needed

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if needed
            }
        });
    }

    private void sendMessage(String message) {
        // Generate a unique message ID
        String messageId = groupChatRef.push().getKey();

        if (messageId != null) {
            // Get the current user ID
            String userId = currentUser.getUid();

            // Create a final variable to store the user name
            final String[] userName = { "Unknown User" }; // Default value if user name is not available

            // Fetch the user details from the database
            usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        userName[0] = snapshot.child("userName").getValue(String.class);

                        // Create a new ChatMessage object
                        ChatMessage chatMessage = new ChatMessage(messageId, userId, userName[0], message, System.currentTimeMillis());

                        // Save the message to Firebase Database
                        groupChatRef.child(messageId).setValue(chatMessage)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        // Message sent successfully
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle message sending failure if needed
                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error if needed
                }
            });
        }
    }
}
