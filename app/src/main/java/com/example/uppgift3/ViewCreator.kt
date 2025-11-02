package com.example.uppgift3

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import java.math.BigDecimal
import java.math.RoundingMode

class ViewCreator {
    fun createHorizontalLayout(context: Context): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    fun createVerticalLayout(context: Context): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    fun productImageAndText(context: Context, product: Product): LinearLayout {
        val horizontalLayout = createHorizontalLayout(context)
        horizontalLayout.setPadding(50,50,0,30)
        horizontalLayout.gravity = Gravity.BOTTOM

        horizontalLayout.addView(
            createProductImageView(context, product, 90)
        )
        horizontalLayout.addView(
            productNameAndPriceText(context, product)
        )

        return horizontalLayout
    }

    fun productNameAndPriceText(context: Context, product: Product): TextView {
        val textView = TextView(context).apply {
            text = context.getString(
                R.string.productNameAndPrice,
                product.name,
                product.price,
                context.getString(R.string.currency)
            )
        }
        return textView
    }

    fun productAmountText(context: Context, amount: Int): TextView {
        return TextView(context).apply {
            text = context.getString(
                R.string.productAmount,
                amount.toString()
            )
        }
    }

    fun totalProductPrice(context: Context, item: CartItemData): TextView {
        val totalPrice = setDecimalFormat((item.product.price.multiply(item.count.toBigDecimal())))
        return TextView(context).apply {
            text = context.getString(R.string.totalProductPrice, totalPrice, context.getString(R.string.currency))
        }
    }

    fun orderTotalPriceText(context: Context, totalPrice: BigDecimal): TextView {
        val orderTotalPriceTextView = TextView(context).apply {
            text = context.getString(
                R.string.orderTotalPrice,
                setDecimalFormat(totalPrice),
                context.getString(R.string.currency)
            )
            setPadding(50, 50, 50, 50)
        }
        return orderTotalPriceTextView
    }


    fun createProductImageView(context: Context, product: Product, size: Int) : ImageView {
        val imageView = ImageView(context).apply {
            val dpSize = (size * resources.displayMetrics.density).toInt()
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(dpSize, dpSize)
            setImageResource(product.image)
        }
        return imageView
    }

    fun createNavigationButtons(context: Context, leftButtonText: String, rightButtonText: String): LinearLayout {
        val leftButton = createButton(context, leftButtonText)
        val rightButton = createButton(context, rightButtonText)

        val buttonsHorizontalLayout = createHorizontalLayout(context)
        buttonsHorizontalLayout.setPadding(40,100,40,0)
        buttonsHorizontalLayout.addView(leftButton)
        buttonsHorizontalLayout.addView(View(context).apply {
            layoutParams = LinearLayout.LayoutParams(0, 0, 1f)
        })
        buttonsHorizontalLayout.addView(rightButton)

        return buttonsHorizontalLayout
    }

    private fun createButton(context: Context, buttonText: String): Button {
        val button = MaterialButton(context).apply {
            text = buttonText
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        return button
    }

//    private fun createRightButton(context: Context, buttonText: String): Button {
//        val rightButton = MaterialButton(context).apply {
//            text = buttonText
//            layoutParams = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//            )
////            setOnClickListener {
////                val intent = Intent(this@ShopActivity, BasketActivity::class.java)
////                intent.putExtra("selectedItems", selectedItems)
////                startActivity(intent)
////            }
//        }
//        return rightButton
//    }

    private fun setDecimalFormat(number: BigDecimal): String {
        return number.setScale(2, RoundingMode.HALF_UP).toPlainString()
    }
}