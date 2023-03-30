package com.unipi.kormazos.unipi_audio_stories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Stats extends AppCompatActivity {
    TextView messageOutput, messageOutputName,messageOutputFavor;
    int max=0;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        messageOutput =findViewById(R.id.textViewData);
        messageOutputName = findViewById(R.id.textView3);
        messageOutputFavor= findViewById(R.id.textViewData2);

    }
    public void read(View view){
        FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
        DatabaseReference listRef1 = rootRef.getReference("lists");

        listRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageOutputName.setText("");
                messageOutput.setText(messageOutput.getText().toString()+"\n");

                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String bookName = childSnapshot.child("bookName").getValue(String.class);
                    int clicks = childSnapshot.child("clickCount").getValue(Integer.class);
                    if(max<clicks){
                        max=clicks;
                        name=bookName;
                    }

                    messageOutputName.setText(messageOutputName.getText().toString() + "" + bookName+  ":" + clicks +"\n");
                    messageOutputFavor.setText(getResources().getString(R.string.favstory1)+"\n" + "'" + name+ "'" +"\n" + getResources().getString(R.string.favstory2) + max + " clicks.");

                    //messageOutputName.setText(bookName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                messageOutput.setText(error.getMessage());
            }
        });
    }
    public void goToMain(View view){

        Intent intent = new Intent(Stats.this,MainActivity.class);
        startActivity(intent);

    }
}