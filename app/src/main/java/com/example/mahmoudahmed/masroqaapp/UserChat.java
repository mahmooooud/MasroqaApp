package com.example.mahmoudahmed.masroqaapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserChat extends AppCompatActivity implements View.OnClickListener {
    String ConversationId,reverseId;
    DatabaseReference mReference;
    EditText etInputMessage;
    ListView lvMessageList;
    FloatingActionButton bSendMessage;
    FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        bSendMessage = (FloatingActionButton)findViewById(R.id.bSendMessage);
        user = FirebaseAuth.getInstance().getCurrentUser();
        etInputMessage = (EditText) findViewById(R.id.etInputMessage);
        lvMessageList = (ListView) findViewById(R.id.lvMessageList);
        ConversationId=getIntent().getExtras().getString("ConversationId");
        reverseId=getIntent().getExtras().getString("reverseId");
        mReference= FirebaseDatabase.getInstance().getReference().child("Conversations");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(reverseId)){
                    mReference= FirebaseDatabase.getInstance().getReference().child("Conversations").child(reverseId);
                    displayChatMessages(mReference);
                }
                else {
                    mReference= FirebaseDatabase.getInstance().getReference().child("Conversations").child(ConversationId);
                    displayChatMessages(mReference);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        bSendMessage.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        String Message=etInputMessage.getText().toString();
        mReference.push().setValue(new Posts(Message, FirebaseAuth.getInstance().getCurrentUser().getDisplayName()));
        etInputMessage.setText("");

    }
    private void displayChatMessages(DatabaseReference databaseReference) {
        FirebaseListAdapter<Posts> adapter;
        adapter = new FirebaseListAdapter<Posts>(this, Posts.class, R.layout.post, databaseReference) {

            @Override
            protected void populateView(View v, Posts model, int position) {


                long unixTimestamp = (long) (System.currentTimeMillis() / 1000);
                long differenceBetweenTimes = unixTimestamp -Long.parseLong(String.valueOf(model.getPostTime()));
                long seconds = (long) (differenceBetweenTimes ) % 60;
                long minutes = (long) ((differenceBetweenTimes /  60)) % 60;
                long hours = (long) ((differenceBetweenTimes / ( 60 * 60)) % 24);
                long days = (long) (differenceBetweenTimes / ( 60 * 60 * 24));

                TextView txMessageText = (TextView) v.findViewById(R.id.txMessageText);
                TextView txMessageUser = (TextView) v.findViewById(R.id.txMessageUser);
                TextView txMessageTime = (TextView) v.findViewById(R.id.txMessageTime);

                if (model.getPostUser() != null && model.getPostUser().equals(user.getDisplayName())) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.RIGHT;
                    txMessageText.setTextColor(v.getResources().getColor(R.color.colorPrimary));


                    txMessageUser.setLayoutParams(params);

                    txMessageText.setLayoutParams(params);
                    txMessageTime.setLayoutParams(params);


                }else{
                    txMessageUser.setGravity(Gravity.LEFT | Gravity.START);
                    txMessageText.setGravity(Gravity.LEFT | Gravity.START);
                    txMessageTime.setGravity(Gravity.LEFT | Gravity.START);

                }

                txMessageText.setText(model.getPostText());
                txMessageUser.setText(model.getPostUser());
                txMessageTime.setText(DateFormat.format("dd-MM-yyyy (H:mm)",// Format the date before showing it
                        model.getPostTime()));
                if (days > 0) {
                    txMessageTime.setText(days + ""+"day");
                }else if (hours > 0){
                    txMessageTime.setText(hours + ""+"hour");
                }else if (minutes > 0){
                    txMessageTime.setText(minutes + ""+"minute");
                }else {
                    txMessageTime.setText(seconds + ""+"second");
                }
            }
        };

        lvMessageList.setAdapter(adapter);
    }
}
