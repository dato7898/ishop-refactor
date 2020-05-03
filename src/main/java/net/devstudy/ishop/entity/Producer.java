package net.devstudy.ishop.entity;

import net.devstudy.framework.annotation.jdbc.Column;
import net.devstudy.framework.annotation.jdbc.Table;

@Table(name = "producer")
public class Producer extends AbstractEntity<Integer> {
	private static final long serialVersionUID = 1L;
	private String name;
	@Column("product_count")
	private Integer productCount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
}
