package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView

class MainActivity : AppCompatActivity() {

    private lateinit var geckoView: GeckoView
    private val geckoSession = GeckoSession()

    private fun setupGeckoView() {
        // 1
        geckoView = findViewById(R.id.geckoview)

        // 2
        val runtime = GeckoRuntime.create(this)
        geckoSession.open(runtime)

        geckoSession.promptDelegate = BasicGeckoViewPrompt(this)

        // 3
        geckoView.setSession(geckoSession)

        // 4
        geckoSession.loadUri("https://getbootstrap.com/docs/4.0/components/forms/")


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null)
            supportActionBar?.hide()

        setupGeckoView()
    }
}