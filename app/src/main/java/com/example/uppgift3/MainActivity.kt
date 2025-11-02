package com.example.uppgift3

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uppgift3.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
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

        createWelcomeText()
        createButton()
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
}