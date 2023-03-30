package com.unipi.kormazos.unipi_audio_stories;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class MyTTS {private TextToSpeech tts;
    private TextToSpeech.OnInitListener initListener = new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status==TextToSpeech.SUCCESS)
                tts.setLanguage(Locale.ENGLISH);
        }
    };
    public MyTTS(Context context){

        tts = new TextToSpeech(context,initListener);
    }
    public void speak(String message){

        tts.speak(message,TextToSpeech.QUEUE_ADD,null,null);
    }

    public void stop() {
        if (tts != null) {
            tts.stop();
        }
    }

    public void shutdown() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
