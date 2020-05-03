package net.devstudy.ishop.service.impl;

import java.util.List;

import net.devstudy.framework.annotation.Autowired;
import net.devstudy.framework.annotation.Component;
import net.devstudy.framework.annotation.jdbc.Transactional;
import net.devstudy.ishop.entity.Category;
import net.devstudy.ishop.entity.Producer;
import net.devstudy.ishop.entity.Product;
import net.devstudy.ishop.form.SearchForm;
import net.devstudy.ishop.repository.CategoryRepository;
import net.devstudy.ishop.repository.ProducerRepository;
import net.devstudy.ishop.repository.ProductRepository;
import net.devstudy.ishop.service.ProductService;

@Component
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProducerRepository producerRepository;

	@Override
	public List<Product> listAllProducts(int page, int limit) {
		int offset = (page - 1) * limit;
		return productRepository.listAllProducts(limit, offset);
	}

	@Override
	public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
		int offset = (page - 1) * limit;
		return productRepository.listProductByCategory(categoryUrl, limit, offset);
	}

	@Override
	public List<Category> listAllCategories() {
		return categoryRepository.listAllCategories();
	}

	@Override
	public List<Producer> listAllProducers() {
		return producerRepository.listAllProducer();
	}

	@Override
	public int countAllProducts() {
		return productRepository.countAllProducts();
	}

	@Override
	public int countProductsByCategory(String categoryUrl) {
		return productRepository.countProductsByCategory(categoryUrl);
	}

	@Override
	public List<Product> listProductsBySearchForm(SearchForm form, int page, int limit) {
		int offset = (page - 1) * limit;
		return productRepository.listProductsBySearchForm(form, limit, offset);
	}

	@Override
	public int countProductsBySearchForm(SearchForm form) {
		return productRepository.countProductsBySearchForm(form);
	}
}
