package com.unipi.kormazos.unipi_audio_stories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class ReadStory extends AppCompatActivity {

    TextView userInput,insertTitle;
    MyTTS myTTS;
    ImageView imagePlay,imageStop,imageBack,imageStory;

    String story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_story);
        userInput = findViewById(R.id.textOfTheBook);
        insertTitle = findViewById(R.id.textViewTitle);
        imageStory = findViewById(R.id.imageViewMain);

        myTTS = new MyTTS(this);
        int position = getIntent().getIntExtra("positionStory", 0);
        String storyTitle =getIntent().getStringExtra("theStory");
        insertTitle.setText(storyTitle);
        String storyText =getIntent().getStringExtra("theStoryText");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        //Μπορούμε να κάνουμε update τις εικόνες αποθηκεύνατας με το ιδιο όνομα στην βάση.
        String url="gs://unipi-audio-stories-956b9.appspot.com/Snow White and the Seven Dwarfs.jpg";
        if(position==1){
            url="gs://unipi-audio-stories-956b9.appspot.com/rat and the elephant.jpg";

        }else if(position==2){
            url="gs://unipi-audio-stories-956b9.appspot.com/The Wolf and the Seven Little Kids.jpg";
        }else if(position==3){
            url="gs://unipi-audio-stories-956b9.appspot.com/The Elves and the Shoemaker.jpg";
        }else if(position==4){
            url="gs://unipi-audio-stories-956b9.appspot.com/The Pied Piper of Hamelin.jpg";
        }
        StorageReference gsReference = storage.getReferenceFromUrl(url);


        // Download the file as a byte array
        final long ONE_MEGABYTE = 1024 * 1024;
        gsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Convert the byte array to a Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                // Set the Bitmap as the image for your ImageView
                imageStory.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        imagePlay = findViewById(R.id.imageViewPlay);
        imagePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userInput.setVisibility(View.GONE);
                userInput.setText(storyText);
                //imagePlay.setImageResource(R.drawable.ic_baseline_pause_24);
                speak();
            }
        });

        imageStop = findViewById(R.id.imageViewPause);
        imageStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTTS.stop();
            }
        });

        imageBack = findViewById(R.id.imageViewBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTTS.stop();
                Intent intent = new Intent(ReadStory.this ,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void speak(){
        if (!userInput.getText().toString().isEmpty()){
            //myTTS.speak(userInput.getText().toString());
            String message = userInput.getText().toString();
            int chunkSize = 100; // split the message into chunks of 100 characters
            int startIndex = 0;
            int endIndex = chunkSize;

            while (startIndex < message.length()) {
                if (endIndex > message.length()) {
                    endIndex = message.length();
                }
                String chunk = message.substring(startIndex, endIndex);
                myTTS.speak(chunk);
                startIndex += chunkSize;
                endIndex += chunkSize;
            }

        } else {
            Toast.makeText(this, "Please write a message", Toast.LENGTH_SHORT).show();
        }
    }
}