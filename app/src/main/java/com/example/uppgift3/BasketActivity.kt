package com.example.uppgift3

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.uppgift3.databinding.ActivityShoppingCartBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BasketActivity : AppCompatActivity() {
    private lateinit var cartItemDataProcessor: CartItemDataProcessor
    private lateinit var viewBinding: ActivityShoppingCartBinding
    private lateinit var selectedItems: ArrayList<Product>
    private lateinit var itemsImageViews: ArrayList<ImageView>
    private val currency = "kr"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityShoppingCartBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(viewBinding.root)

        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        selectedItems = intent.getSerializableExtra("selectedItems") as ArrayList<Product>
        cartItemDataProcessor = CartItemDataProcessor(selectedItems)
        val cartData = cartItemDataProcessor.prepareCartData()
        createViews(cartData)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = intent.apply { putExtra("RESULT_KEY", selectedItems) }
                setResult(RESULT_OK, intent)
                finish()
            }
        })
    }

    private fun createViews(cartData: List<CartItemData>) {
        val productImageViewWidthDp = (120 * resources.displayMetrics.density).toInt()
        val productImageViewHeightDp = (120 * resources.displayMetrics.density).toInt()

        val orderTotalPriceTextView = TextView(this).apply {
            val totalPrice = selectedItems.sumOf { item -> item.price }
            text = "Order total price: ${ setDecimalFormat(totalPrice) } $currency"
            setPadding(50, 50, 50, 50)

        }

        cartData.forEach { item ->
            val imageAndInfoHorizontalLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(50, 50, 50, 50)
            }

            val productImageView = ImageView(this).apply {
                id = View.generateViewId()
                layoutParams = LinearLayout.LayoutParams(productImageViewWidthDp, productImageViewHeightDp)
                setImageResource(item.product.image)
            }

            val productInfoVerticalLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(50, 50, 50, 50)
            }

            val nameAndPriceTextView = TextView(this).apply {
                text = "${item.product.name} (${ setDecimalFormat(item.product.price) } $currency)"
                textSize = 16f
            }

            val amountTextView = TextView(this).apply {
                text = "Amount: ${ item.count }"
            }

            val totalProductPriceTextView = TextView(this).apply {
                val count = item.count
                val price = item.product.price
                val totalPrice = (price.multiply(count.toBigDecimal()))
                text = "Total: ${ setDecimalFormat(totalPrice) } $currency"
            }

            productInfoVerticalLayout.addView(nameAndPriceTextView)
            productInfoVerticalLayout.addView(amountTextView)
            productInfoVerticalLayout.addView(totalProductPriceTextView)

            imageAndInfoHorizontalLayout.addView(productImageView)
            imageAndInfoHorizontalLayout.addView(productInfoVerticalLayout)

            viewBinding.llVertical.addView(imageAndInfoHorizontalLayout)
        }

        val backToShoppingButton = Button(this).apply {
            text = "Back to shop"
            setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        viewBinding.llVertical.addView(orderTotalPriceTextView)
        viewBinding.llVertical.addView(backToShoppingButton)
    }

    private fun setDecimalFormat(number: BigDecimal): String {
        return number.setScale(2, RoundingMode.HALF_UP).toPlainString()
    }
}