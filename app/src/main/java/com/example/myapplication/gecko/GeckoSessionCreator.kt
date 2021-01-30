package com.example.myapplication.gecko

import android.app.Activity
import android.view.View
import android.widget.ProgressBar
import org.mozilla.geckoview.*

object GeckoSessionCreator {

    private lateinit var progressView: ProgressBar
    private lateinit var  runtime : GeckoRuntime

    fun setupGeckoView(
        uri: String,
        geckoSession: GeckoSession,
        geckoView: GeckoView,
        progressView_: ProgressBar,
        activityContext: Activity,
        attachToRuntime:Boolean,
        f: (url: String?) -> Unit
    ) {


        // 2
        if(attachToRuntime){
            runtime.attachTo(activityContext)
        }else{
            runtime = GeckoRuntime.create(activityContext)
        }




        geckoSession.open(runtime)

        geckoSession.promptDelegate = BasicGeckoViewPrompt(activityContext)

        geckoSession.navigationDelegate = createNavigationDelegate() {
            f(it)
        }

        progressView = progressView_

        geckoSession.progressDelegate = createProgressDelegate()

        // 3
        geckoView.setSession(geckoSession)

        // 4
        geckoSession.loadUri(uri)


    }

    private fun createNavigationDelegate(f: (url: String?) -> Unit) =
        object : GeckoSession.NavigationDelegate {
            override fun onLoadRequest(
                session: GeckoSession,
                request: GeckoSession.NavigationDelegate.LoadRequest
            ): GeckoResult<AllowOrDeny> {
                return if (request.uri.startsWith("https://youtube.com/")) {
                    //This link will open android webview because this method will always block the
                    //link and it is safe to open other url in android webview. And there needs to block
                    //central app link to avoid forgery.
                    f(request.uri)
                    GeckoResult.fromValue(AllowOrDeny.DENY)
                } else {
                    GeckoResult.fromValue(AllowOrDeny.ALLOW)
                }
            }
        }

    private fun createProgressDelegate(): GeckoSession.ProgressDelegate {
        return object : GeckoSession.ProgressDelegate {

            override fun onPageStop(session: GeckoSession, success: Boolean) = Unit

            override fun onSecurityChange(
                session: GeckoSession,
                securityInfo: GeckoSession.ProgressDelegate.SecurityInformation
            ) = Unit

            override fun onPageStart(session: GeckoSession, url: String) = Unit

            override fun onProgressChange(session: GeckoSession, progress: Int) {
                progressView.progress = progress
                println(progress)
                if (progress in 1..99) {
                    progressView.visibility = View.VISIBLE
                } else {
                    progressView.visibility = View.GONE
                }
            }
        }
    }

}

