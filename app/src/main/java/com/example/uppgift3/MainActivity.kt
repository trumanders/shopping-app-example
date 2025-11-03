package com.example.uppgift3

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import com.example.uppgift3.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var viewCreator: ViewCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(viewBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewCreator = ViewCreator()
        createWelcomeText()
        createButton()
        createLanguageOptions()
    }

    fun setAppLanguage(languageCode: String) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    private fun createWelcomeText() {
        val textView = TextView(this).apply {
            text = getString(R.string.welcometext)
            textSize = 30f
            setTypeface(typeface, Typeface.ITALIC)
            setTypeface(typeface, Typeface.BOLD)
            gravity = Gravity.CENTER
        }
        viewBinding.llVertical.addView(textView)
    }

    private fun createButton() {
        val btnStartShopping = MaterialButton(this).apply {
            text = getString(R.string.toShop)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0,60,0,0)
                gravity = Gravity.CENTER_HORIZONTAL
            }
            setOnClickListener {
                startActivity(Intent(this@MainActivity, ShopActivity::class.java))
            }
        }
        viewBinding.llVertical.addView(btnStartShopping)
    }

    private fun createLanguageOptions() {
        val languageHorizontalLayout = LinearLayout(this).apply {
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            gravity = Gravity.CENTER_HORIZONTAL
        }

        val languageIconSize = 80
        val languageImages = arrayOf(
            R.drawable.english,
            R.drawable.swedish
        )
        val languages = resources.getStringArray(R.array.languages)

        languages.forEachIndexed { i, language ->
            languageHorizontalLayout.addView(
                viewCreator.createImageView(this, languageImages[i], languageIconSize).apply {
                    setOnClickListener {
                        setAppLanguage(languages[i])
                    }
                    setPadding(30)
                }
            )
        }
        viewBinding.llVertical.addView(languageHorizontalLayout)
    }
}