package com.example.uppgift3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.uppgift3.databinding.ActivityBasketBinding
import com.google.android.material.button.MaterialButton
import java.math.BigDecimal
import java.math.RoundingMode

class BasketActivity : AppCompatActivity() {
    private lateinit var cartItemDataProcessor: CartItemDataProcessor
    private lateinit var viewBinding: ActivityBasketBinding
    private lateinit var selectedItems: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityBasketBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(viewBinding.root)

        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        selectedItems = intent.getSerializableExtra("selectedItems") as ArrayList<Product>
        cartItemDataProcessor = CartItemDataProcessor(selectedItems)
        val cartData = cartItemDataProcessor.prepareCartData()
        createViews(cartData)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun createViews(cartData: ArrayList<CartItemData>) {
        val productImageViewWidthDp = (120 * resources.displayMetrics.density).toInt()
        val productImageViewHeightDp = (120 * resources.displayMetrics.density).toInt()

        val orderTotalPriceTextView = TextView(this).apply {
            val totalPrice = selectedItems.sumOf { item -> item.price }
            text = getString(R.string.orderTotalPrice, setDecimalFormat(totalPrice), getString(R.string.currency))
            setPadding(50, 50, 50, 50)
        }

        cartData.forEach { item ->
            val count = item.count
            val price = item.product.price
            val totalPrice = (price.multiply(count.toBigDecimal()))
            val currency = getString(R.string.currency)

            val productInfoVerticalLayout = createVerticalLayout().apply {
                addView(createTextView(
                    getString(
                        R.string.productNameAndPrice,
                        item.product.name,
                        setDecimalFormat(item.product.price),
                        currency)
                    )
                )
                addView(createTextView(
                    getString(R.string.productAmount, item.count.toString()))
                )
                addView(createTextView(
                    getString(R.string.totalProductPrice,setDecimalFormat(totalPrice), currency))
                )
            }

            val productImageView = createImageView(
                item.product.image,
                productImageViewWidthDp,
                productImageViewHeightDp
            )

            val imageAndInfoHorizontalLayout = createHorizontalLayout().apply {
                addView(productImageView)
                addView(productInfoVerticalLayout)
            }

            viewBinding.llVertical.addView(imageAndInfoHorizontalLayout)
        }

        val buttonsHorizontalLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(30, 0, 30, 0)
        }

        val backToShoppingButton = MaterialButton(this).apply {
            text = getString(R.string.btnBackToShopping)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        val confirmOrderButton = MaterialButton(this).apply {
            text = getString(R.string.confirmOrder)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                val intent = Intent(this@BasketActivity, ConfirmActivity::class.java)
                intent.putExtra("cartItemData", cartData)
                startActivity(intent)
            }
        }

        buttonsHorizontalLayout.addView(backToShoppingButton)
        buttonsHorizontalLayout.addView(confirmOrderButton)
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

    private fun createTextView(textViewText: String, fontSize: Float = 16f): TextView {
        return TextView(this).apply {
            text = textViewText
            textSize = fontSize
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

    private fun setDecimalFormat(number: BigDecimal): String {
        return number.setScale(2, RoundingMode.HALF_UP).toPlainString()
    }
}