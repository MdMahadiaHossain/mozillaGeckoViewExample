package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.gecko.GeckoSessionCreator
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView

class OtherActivity : AppCompatActivity() {

    private val geckoSession = GeckoSession()

    private lateinit var geckoView: GeckoView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_other)

        if (supportActionBar != null)
            supportActionBar?.hide()

        geckoView = findViewById(R.id.geckoview)

        val extras = getIntent().getExtras()
        val url = extras?.getString("url")

        if(url!=null){
            println("Mahadi  $url")
            GeckoSessionCreator.setupGeckoView(
                "$url",
                geckoSession,
                geckoView,
                findViewById(R.id.page_progress),
                this,
                true
            ){
                    _ -> println("Hi..........................@@@@@@@@@@@@@"+url)
            }
        }



    }
}