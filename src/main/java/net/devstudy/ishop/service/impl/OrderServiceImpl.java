package net.devstudy.ishop.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.devstudy.framework.annotation.Autowired;
import net.devstudy.framework.annotation.Component;
import net.devstudy.framework.annotation.jdbc.Transactional;
import net.devstudy.framework.factory.TransactionSynchronization;
import net.devstudy.framework.factory.TransactionSynchronizationManager;
import net.devstudy.ishop.entity.Account;
import net.devstudy.ishop.entity.Order;
import net.devstudy.ishop.entity.OrderItem;
import net.devstudy.ishop.entity.Product;
import net.devstudy.ishop.exception.InternalServerErrorException;
import net.devstudy.ishop.exception.ResourceNotFoundException;
import net.devstudy.ishop.exception.AccessDeniedException;
import net.devstudy.ishop.form.ProductForm;
import net.devstudy.ishop.model.CurrentAccount;
import net.devstudy.ishop.model.ShoppingCart;
import net.devstudy.ishop.model.ShoppingCartItem;
import net.devstudy.ishop.model.SocialAccount;
import net.devstudy.ishop.repository.AccountRepository;
import net.devstudy.ishop.repository.OrderItemRepository;
import net.devstudy.ishop.repository.OrderRepository;
import net.devstudy.ishop.repository.ProductRepository;
import net.devstudy.ishop.service.AvatarService;
import net.devstudy.ishop.service.CookieService;
import net.devstudy.ishop.service.NotificationService;
import net.devstudy.ishop.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private AvatarService AvatarService;
	@Autowired
	private CookieService cookieService;
	@Autowired
	private NotificationService notificationServie;

	@Override
	@Transactional
	public void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart) {
		Product product = productRepository.findById(productForm.getIdProduct());
		if (product == null) {
			throw new InternalServerErrorException("Product not found by id=" + productForm.getIdProduct());
		}
		shoppingCart.addProduct(product, productForm.getCount());
	}

	@Override
	public void removeProductFromShoppingCart(ProductForm form, ShoppingCart shoppingCart) {
		shoppingCart.removeProduct(form.getIdProduct(), form.getCount());
	}

	@Override
	public String serializeShoppingCart(ShoppingCart shoppingCart) {
		return cookieService.createShoppingCartCookie(shoppingCart.getItems());
	}

	@Override
	public ShoppingCart deserializeShoppingCart(String cookieValue) {
		ShoppingCart shoppingCart = new ShoppingCart();
		List<ProductForm> products = cookieService.parseShoppingCartCookie(cookieValue);
		for (ProductForm product : products) {
			try {
				addProductToShoppingCart(product, shoppingCart);
			} catch (RuntimeException e) {
				LOGGER.error("Can't add product to ShoppingCart during deserialization: item=" + product, e);
			}
		}
		return shoppingCart.getItems().isEmpty() ? null : shoppingCart;
	}

	@Override
	@Transactional(readOnly = false)
	public CurrentAccount authentificate(SocialAccount socialAccount) {
		Account account = accountRepository.findByEmail(socialAccount.getEmail());
		if (account == null) {
			String avatarUrl = AvatarService.processAvatartLink(socialAccount.getAvatarUrl());
			account = new Account(socialAccount.getName(), socialAccount.getEmail(), avatarUrl);
			accountRepository.create(account);
		}
		return account;
	}

	@Override
	@Transactional(readOnly = false)
	public long makeOrder(ShoppingCart shoppingCart, final CurrentAccount currentAccount) {
		validateShoppingCart(shoppingCart);
		final Order order = new Order(currentAccount.getId(), new Timestamp(System.currentTimeMillis()));
		orderRepository.create(order);
		for (ShoppingCartItem item : shoppingCart.getItems()) {
			orderItemRepository.create(new OrderItem(order.getId(), item.getProduct(), item.getCount()));
		}
		TransactionSynchronizationManager.addSynchronization(new TransactionSynchronization() {
			@Override
			public void afterTransaction() {
				notificationServie.sendNewOrderCreatedNotification(currentAccount.getEmail(), order);
			}
		});
		return order.getId();
	}

	private void validateShoppingCart(ShoppingCart shoppingCart) {
		if (shoppingCart == null || shoppingCart.getItems().isEmpty()) {
			throw new InternalServerErrorException("shoppingCart is null or empty");
		}
	}

	@Override
	@Transactional
	public Order findOrderById(long id, CurrentAccount currentAccount) {
		Order order = orderRepository.findById(id);
		if (order == null) {
			throw new ResourceNotFoundException("Order not found by id: " + id);
		}
		if (!order.getIdAccount().equals(currentAccount.getId())) {
			throw new AccessDeniedException(
					"Account with id=" + currentAccount.getId() + " is not owner for order with id=" + id);
		}
		order.setItems(orderItemRepository.findByIdOrder(id));
		return order;
	}

	@Override
	@Transactional
	public List<Order> listMyOrders(CurrentAccount currentAccount, int page, int limit) {
		int offset = limit * (page - 1);
		return orderRepository.listMyOrders(currentAccount.getId(), limit, offset);
	}

	@Override
	@Transactional
	public int countMyOrders(CurrentAccount currentAccount) {
		return orderRepository.countMyOrders(currentAccount.getId());
	}
}
