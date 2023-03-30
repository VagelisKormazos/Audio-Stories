package com.unipi.kormazos.unipi_audio_stories;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener{

    String[] Names = new String[10];
    int i=0,positionSave;
    ImageView imageVoice;
    String voiceMessage;
    TextView voiceText;

    @Override
    public void onItemClicked(Item item) {
        item.incrementClickCount();
        for (int i = 0; i < Names.length; i++) {
            if (Names[i].equals(item.getBookName())) {
                //Toast.makeText(this,item.getBookName() , Toast.LENGTH_SHORT).show();
                positionSave=i;
                break;
            }
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        if(positionSave==0){
            DatabaseReference itemRef = database.getReference("lists/0/clickCount");
            itemRef.setValue(item.getClickCount());
        }else if(positionSave==1){
                DatabaseReference itemRef = database.getReference("lists/1/clickCount");
                itemRef.setValue(item.getClickCount());
        }else if(positionSave==2){
            DatabaseReference itemRef = database.getReference("lists/2/clickCount");
            itemRef.setValue(item.getClickCount());
        }else if(positionSave==3){
            DatabaseReference itemRef = database.getReference("lists/3/clickCount");
            itemRef.setValue(item.getClickCount());
        }else if(positionSave==4){
            DatabaseReference itemRef = database.getReference("lists/4/clickCount");
            itemRef.setValue(item.getClickCount());
        }
        Intent intent = new Intent(MainActivity.this, ReadStory.class);
        intent.putExtra("positionStory", positionSave);
        intent.putExtra("theStory",item.getBookName());
        intent.putExtra("theStoryText",item.getMainStory());
        startActivity(intent);

        imageVoice = findViewById(R.id.imageViewPause);
        imageVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Start Speaking");
                startActivityForResult(intent,100);
            }
        });
    }
    public void speak(View view){
        Intent intent  = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Start Speaking");
        startActivityForResult(intent,100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String go="stats";
        String read="Fairytale";
        if(requestCode == 100 && resultCode == RESULT_OK){
            voiceMessage = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
            voiceText.setText(voiceMessage);
            if(go.equals(voiceMessage)){
                Intent intent1 = new Intent(MainActivity.this,Stats.class);
                startActivity(intent1);
            }
            if(read.equals(voiceMessage)){
                Intent intent2 = new Intent(MainActivity.this, ReadStory.class);
                startActivity(intent2);
            }
        }
    }

    public void goToStats(View view){

        Intent intent = new Intent(MainActivity.this,Stats.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        voiceText=findViewById(R.id.voiceTextView);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FirebaseDatabase rootRef = FirebaseDatabase.getInstance();
        DatabaseReference listRef = rootRef.getReference("lists");
        listRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Item> items = new ArrayList<>();
                for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                    String bookName = childSnapshot.child("bookName").getValue(String.class);
                    String information = childSnapshot.child("information").getValue(String.class);
                    String mainStory = childSnapshot.child("mainStory").getValue(String.class);
                    int clicks = childSnapshot.child("clickCount").getValue(Integer.class);
                    Names[i]=bookName;
                    i++;
                    items.add(new Item(bookName,information,mainStory,R.drawable.book_play,clicks));
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(new MyAdapter(getApplicationContext(),items,MainActivity.this));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        //Προσοχή μην το βαλεις στην επαναληψη
        //Tο save παρακατω κανει restart την βαση ή προσθετεις ιστορίες
        /*List<Item> items = new ArrayList<>();
        items.add(new Item("Snow White and the Seven Dwarfs"," 20 min+, age 7-12,Brothers Grimm Fairytale","text",R.drawable.book_play,1));
        items.add(new Item("The Rat and the Elephant","3 min, age 7-12, Animal  Fairytale","text",R.drawable.book_play,2));
        items.add(new Item("The Wolf and the Seven Little Kids"," 10 min, age 7-12, Animal  Fairytale","text",R.drawable.book_play,3));
        items.add(new Item("The Elves and the Shoemaker"," 5 min, age 7-12,Elves Fairytale","text",R.drawable.book_play,4));
        items.add(new Item("The Pied Piper of Hamelin"," 10 min, age 7-12, Animal  Fairytale","text",R.drawable.book_play,5));

        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            map.put(String.valueOf(i), items.get(i));
        }
        listRef.setValue(map);*/

        //-----------SAVE STORIES-------
        /*DatabaseReference listRef0 = rootRef.getReference("stories0");
        String story0="Some people say that rats are ugly creatures. When they see a rat running along, they go ee-yuck! Well I don’t know about you, but I’ve always thought that this was rather rude. Rats can have hurt feelings too you know! In any case, when I catch sight of my reflection in a stream, I think I’m rather cute.\n" +
                "\n" +
                "Just recently, I was trotting along the King’s Highway, in my sweet little way, when I heard a great commotion on the road up ahead. Who or what is causing all that fuss? I wondered.\n" +
                "\n" +
                "When I got closer, I saw the King himself, riding along on top of a great fat lump of an elephant. The crowd of onlookers were ooo-ing and aah-ing full of admiration for that stupid beast with a nose that’s far too big for her face. She’s much uglier than me, I thought. So I started to spring up and down and say: “Hey everyone! Why not look at me? I’m such a cutie-pie! I could join the King’s household and be a royal rat, if only there was any justice in the world.”\n" +
                "\n" +
                "At first, nobody noticed me. They were all too busy ogling that stupid elephant. Little did I know that riding behind the elephant in a carriage, was the princess, and she was holding a beastly cat in her arms. When he caught sight of me, the cat leapt out of the carriage and started to chase me. I had to run for my life, and popped down a hole just in time before the cat could eat me up.\n" +
                "\n" +
                "Now I’ve changed my mind about wanting people to notice me and admire me. I’ve decided that sometimes, it’s far better not to draw attention to yourself, but just to get on quietly with your own business.";

        listRef0.setValue(story0);

        DatabaseReference listRef1 = rootRef.getReference("stories1");
        String story1=""
        listRef1.setValue(story1);

        DatabaseReference listRef2 = rootRef.getReference("stories2");
        String story2="";
        listRef2.setValue(story2);

        DatabaseReference listRef3 = rootRef.getReference("stories3");
        String story3="";
        listRef3.setValue(story3);

        DatabaseReference listRef4 = rootRef.getReference("stories4");
        String story4="";
        listRef4.setValue(story4);

        DatabaseReference listRef5 = rootRef.getReference("stories5");
        String story5="";
        listRef5.setValue(story5);*/


    }



}