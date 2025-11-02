package com.example.uppgift3

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uppgift3.databinding.ActivityConfirmBinding
import com.google.android.material.button.MaterialButton

class ConfirmActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityConfirmBinding
    private lateinit var cartItemData: ArrayList<CartItemData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityConfirmBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(viewBinding.root)
        cartItemData = intent.getSerializableExtra("confirmationCartData") as ArrayList<CartItemData>

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        createButtons()
    }

    private fun createButtons() {
        val confirmOrderButton = MaterialButton(this).apply {
            text = getString(R.string.toCheckout)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                Toast.makeText(this@ConfirmActivity, "Your order has been confirmed.", Toast.LENGTH_LONG).show()
                startActivity(Intent(this@ConfirmActivity, MainActivity::class.java))
                finish()
            }
        }
        viewBinding.llVertical.addView(confirmOrderButton)
    }
}