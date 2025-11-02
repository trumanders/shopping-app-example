package com.example.uppgift3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.uppgift3.databinding.ActivityShopBinding

class ShopActivity : AppCompatActivity() {
    private val selectedItems = ArrayList<Product>()
    private var numItems: Int = 0
    private lateinit var viewBinding: ActivityShopBinding
    private lateinit var viewCreator: ViewCreator

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
        viewCreator = ViewCreator()
        createViews(createProducts())
    }

    private fun createViews(products: Array<Product>) {
        products.forEach { product ->
            val horizontalLayout = viewCreator.productImageAndText(this, product)
            horizontalLayout.getChildAt(0)
                .setOnClickListener { addToShoppingCart(product) }
            viewBinding.llVertical.addView(horizontalLayout)
        }
        val buttonsHorizontalLayout = viewCreator.createNavigationButtons(
            this,
            getString(R.string.backToMain),
            getString(R.string.toShoppingCart)
        )

        buttonsHorizontalLayout.getChildAt(0)
            .setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        buttonsHorizontalLayout.getChildAt(2)
            .setOnClickListener {
                val intent = Intent(this@ShopActivity, BasketActivity::class.java)
                intent.putExtra("selectedItems", selectedItems)
                startActivity(intent)
            }

        viewBinding.llVertical.addView(buttonsHorizontalLayout)
    }

    private fun createProducts(): Array<Product> {
        val productImages = arrayOf(
            R.drawable.banana,
            R.drawable.apple,
            R.drawable.orange,
            R.drawable.grapes
        )

        val productNames = resources.getStringArray(R.array.availableProducts)
        val productPrices = resources.getStringArray(R.array.availableProductsPrices)
        val availableProducts = productNames.mapIndexed { index, productName ->
            Product(
                name = productName,
                image = productImages[index],
                price = productPrices[index].toBigDecimal()
            )
        }.toTypedArray()

        return availableProducts
    }

    private fun setUpTextViews() {
        viewBinding.tvItemInfo.text = getString(R.string.iteminfo, numItems.toString())
    }

    private fun addToShoppingCart(item: Product) {
        selectedItems.add(item)
        numItems++
        viewBinding.tvItemInfo.text = getString(R.string.iteminfo, numItems.toString())
    }
}