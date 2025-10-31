package com.example.uppgift3

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uppgift3.databinding.ActivityShopBinding
import java.math.BigDecimal

class ShopActivity : AppCompatActivity() {

    private lateinit var apple_iv: ImageView
    private lateinit var banana_iv: ImageView
    private lateinit var orange_iv: ImageView
    private lateinit var grapes_iv: ImageView
    private val selectedItems = ArrayList<Product>()
    private var numItems: Int = 0

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private lateinit var viewBinding: ActivityShopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityShopBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(viewBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupImageViews()
        setUpTextViews()
        setUpButtons()
    }


    private fun setupImageViews() {
        apple_iv = viewBinding.ivItem1
        banana_iv = viewBinding.ivItem2
        orange_iv = viewBinding.ivItem3
        grapes_iv = viewBinding.ivItem4

        apple_iv.setOnClickListener {
            addToShoppingCart(Product(getString(R.string.apple), R.drawable.apple,
                BigDecimal(24.90)
            ))
        }
        banana_iv.setOnClickListener {
            addToShoppingCart(Product(getString(R.string.banana), R.drawable.banana, BigDecimal(19.80)))
        }
        orange_iv.setOnClickListener {
            addToShoppingCart(Product(getString(R.string.orange), R.drawable.orange, BigDecimal(21.40)))
        }
        grapes_iv.setOnClickListener {
            addToShoppingCart(Product(getString(R.string.grapes), R.drawable.grapes, BigDecimal(43.90)))
        }
        apple_iv.setImageResource(R.drawable.apple)
        banana_iv.setImageResource(R.drawable.banana)
        orange_iv.setImageResource(R.drawable.orange)
        grapes_iv.setImageResource(R.drawable.grapes)
    }

    private fun setUpTextViews() {
        viewBinding.tvItemInfo.text = getString(R.string.iteminfo, numItems.toString())
    }

    // Sends data to ShoppingCartActivity
    private fun setUpButtons() {
        viewBinding.btnGoToShoppingCart.setOnClickListener {
            val intent = Intent(this@ShopActivity, BasketActivity::class.java)
            intent.putExtra("selectedItems", selectedItems)
            startActivity(intent)
        }
    }

    private fun addToShoppingCart(item: Product) {
        selectedItems.add(item)
        numItems++
        viewBinding.tvItemInfo.text = getString(R.string.iteminfo, numItems.toString())
    }
}