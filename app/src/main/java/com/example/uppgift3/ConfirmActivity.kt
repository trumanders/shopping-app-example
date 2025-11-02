package com.example.uppgift3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uppgift3.databinding.ActivityConfirmBinding

class ConfirmActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityConfirmBinding
    private lateinit var cartItemData: ArrayList<CartItemData>
    private lateinit var viewCreator: ViewCreator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityConfirmBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(viewBinding.root)

        viewCreator = ViewCreator()
        cartItemData = intent.getSerializableExtra("confirmationCartData") as ArrayList<CartItemData>

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        createViews()
        createButtons()
    }

    private fun createViews() {
        val formVerticalLayout = viewCreator.form(this)
        viewBinding.llVertical.addView(formVerticalLayout)
    }

    private fun createButtons() {
        val buttonsHorizontalLayout = viewCreator.createNavigationButtons(
            this,
            getString(R.string.cancel),
            getString(R.string.paynow)
        )
        buttonsHorizontalLayout.getChildAt(0)
            .setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        buttonsHorizontalLayout.getChildAt(2)
            .setOnClickListener {
                Toast.makeText(this@ConfirmActivity, "Your order has been confirmed.", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@ConfirmActivity, MainActivity::class.java))
                finish()
            }
        viewBinding.llVertical.addView(buttonsHorizontalLayout)
    }
}