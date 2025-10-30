package com.example.uppgift3

class CartItemDataProcessor(selectedItems: ArrayList<Product>) {
    private val selectedCartItems: ArrayList<Product> = selectedItems

    fun prepareCartData(): List<CartItemData>
    {
        val cartData: ArrayList<CartItemData> = ArrayList()
        val itemsGroupedByProduct = selectedCartItems.groupBy { it.name }

        itemsGroupedByProduct.forEach { (productName, productGroup) ->
            val cartItem = CartItemData(
                product = productGroup.first(),
                count = productGroup.size,
                totalPrice = productGroup.first().price.multiply(productGroup.size.toBigDecimal())
            )
            cartData.add(cartItem)
        }
        return cartData
    }
}