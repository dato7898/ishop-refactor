package net.devstudy.ishop.model;

import java.io.Serializable;

import net.devstudy.ishop.entity.Product;

public class ShoppingCartItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private Product product;
	private int count;

	public ShoppingCartItem(Product product, int count) {
		super();
		this.product = product;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Product getProduct() {
		return product;
	}

	public String toString() {
		return String.format("ShoppingCartItem [product=%s, count=%s]", product, count);
	}
}
