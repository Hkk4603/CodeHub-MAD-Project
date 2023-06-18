package com.example.codehub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText messageInput;
    private ImageButton sendButton;

    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;

    private String groupId;

    private GroupChatAdapter adapter;
    private List<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        // Get the current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Get the group ID from the previous activity
        groupId = getIntent().getStringExtra("groupId");

        recyclerView = findViewById(R.id.recyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        chatMessages = new ArrayList<>();
        adapter = new GroupChatAdapter(chatMessages);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("groups").child(groupId).child("messages");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageInput.getText().toString().trim();
                if (!message.isEmpty()) {
                    sendMessage(message);
                    messageInput.setText("");
                }
            }
        });

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatMessages.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                    chatMessages.add(chatMessage);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GroupChatActivity.this, "Failed to retrieve messages.", Toast.LENGTH_SHORT).show();
            }
        };

        databaseReference.addValueEventListener(valueEventListener);
    }

    private void sendMessage(String message) {
        DatabaseReference messageRef = databaseReference.push();
        String messageId = messageRef.getKey();
//        long timestamp = System.currentTimeMillis();

        if (messageId != null) {
            // Replace with your logic to get the current user ID and name
            String userId = getCurrentUserId();
            String userName = getCurrentUserName();

            // Create a new ChatMessage object
            ChatMessage chatMessage = new ChatMessage(messageId, userId, FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString(), message, System.currentTimeMillis());

            // Save the message to Firebase Database
            messageRef.child(messageId).setValue(chatMessage)
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

//        ChatMessage chatMessage = new ChatMessage(messageId, currentUser.getUid(), currentUser.getDisplayName().toString(), message, timestamp);
//        Log.e("userName", "sendMessage: " + currentUser.getDisplayName().toString());
//        messageRef.setValue(chatMessage);
    }

    private String getCurrentUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return null;
    }

    private String getCurrentUserName() {
        String userId = getCurrentUserId();
//        String name="current User";
        if (userId != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String userName = dataSnapshot.child("userName").getValue(String.class);
                        Log.d("username function", "onDataChange: " + userName);
                        // Do something with the userName (e.g., update UI)
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error if needed
                }
            });
//            return name;
        }
        return null;
    }


}
