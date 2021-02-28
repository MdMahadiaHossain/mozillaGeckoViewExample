package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.gecko.GeckoSessionCreator
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView

class MainActivity : AppCompatActivity() {


    private val geckoSession = GeckoSession()

    private lateinit var geckoView: GeckoView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (supportActionBar != null)
            supportActionBar?.hide()

        geckoView = findViewById(R.id.geckoview)

        GeckoSessionCreator.setupGeckoView(
            "https://www.thedailystar.net/",
            geckoSession,
            geckoView,
            findViewById(R.id.page_progress),
            this,
            false
        ) {
            val intent = Intent(this, OtherActivity::class.java).apply {
                putExtra("url", it)
            }

            startActivity(intent)


        }

    }
}


