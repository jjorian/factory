package ir.bokaeyan.factory.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import ir.bokaeyan.factory.R

class UrlActivity : AppCompatActivity() {
    lateinit var web_browser: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url)
        web_browser = findViewById<WebView>(R.id.web_browser)

        var url=intent.getStringExtra("url")
        web_browser.loadUrl(url)
    }
}