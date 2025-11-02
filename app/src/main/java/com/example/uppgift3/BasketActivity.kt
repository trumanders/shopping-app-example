package com.example.uppgift3

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.uppgift3.databinding.ActivityBasketBinding

class BasketActivity : AppCompatActivity() {
    private lateinit var cartItemDataProcessor: CartItemDataProcessor
    private lateinit var viewBinding: ActivityBasketBinding
    private lateinit var selectedItems: ArrayList<Product>
    private lateinit var viewCreator: ViewCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityBasketBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(viewBinding.root)

        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        selectedItems = intent.getSerializableExtra("selectedItems") as ArrayList<Product>

        cartItemDataProcessor = CartItemDataProcessor(selectedItems)
        viewCreator = ViewCreator()
        val cartData = cartItemDataProcessor.prepareCartData()

        createViews(cartData)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun createViews(cartData: ArrayList<CartItemData>) {
        val size = 120

        val orderTotalPriceTextView = viewCreator.orderTotalPriceText(this, selectedItems.sumOf { it.price })

        cartData.forEach { item ->
            val productInfoVerticalLayout = viewCreator.createVerticalLayout(this).apply {
                addView(viewCreator.productNameAndPriceText(this@BasketActivity, item.product))
                addView(viewCreator.productAmountText(this@BasketActivity, item.count))
                addView(viewCreator.totalProductPrice(this@BasketActivity, item))
            }

            val productImageView = viewCreator.createProductImageView(
                this@BasketActivity,
                item.product,
                size
            )

            val imageAndInfoHorizontalLayout = viewCreator.createHorizontalLayout(this).apply {
                addView(productImageView)
                addView(productInfoVerticalLayout)
            }
            imageAndInfoHorizontalLayout.gravity = Gravity.BOTTOM

            viewBinding.llVertical.addView(imageAndInfoHorizontalLayout)
        }

        val buttonsHorizontalLayout = viewCreator.createNavigationButtons(
            this,
            getString(R.string.backToShop),
            getString(R.string.toCheckout)
        )

        buttonsHorizontalLayout.getChildAt(0)
            .setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        buttonsHorizontalLayout.getChildAt(2).setOnClickListener {
            val intent = Intent(this@BasketActivity, ConfirmActivity::class.java)
            intent.putExtra("confirmationCartData", cartData)
            startActivity(intent)
        }

        viewBinding.llVertical.addView(orderTotalPriceTextView)
        viewBinding.llVertical.addView(buttonsHorizontalLayout)
    }

    private fun createImageView(image: Int, width: Int, height: Int): ImageView {
        return ImageView(this).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(width, height)
            setImageResource(image)
        }
    }

    private fun createVerticalLayout(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
        }
    }

    private fun createHorizontalLayout(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(50, 50, 50, 50)
        }
    }


}