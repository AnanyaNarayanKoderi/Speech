package com.example.speech



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech   //provides interface to multiple languages
import android.util.Log
import android.widget.EditText
import android.widget.Button
import java.util.*

class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    private var textToSpeech: TextToSpeech? = null   //initially it can be null
    private lateinit var textToSpeechButton: Button
    private lateinit var textInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textToSpeechButton = findViewById(R.id.button)
        textInput = findViewById(R.id.typedtext)
        textToSpeechButton!!.isEnabled = false
        textToSpeech = TextToSpeech(this, this)  //initializes text to speech class
        textToSpeechButton!!.setOnClickListener { convertToSpeech() }
    }


    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech!!.setLanguage(Locale.US)  //to set the language

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language specified NOT SUPPORTED")
            } else {
                textToSpeechButton!!.isEnabled = true
            }
        } else {
            Log.e("TTS", "Initialization Failed")
        }
    }


    private fun convertToSpeech() {
        val text = textInput!!.text.toString()
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")  //it returns either success or failure
    }

    public override fun onDestroy() {
        if (textToSpeech != null) {
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }
        super.onDestroy()
    }
}

