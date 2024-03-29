package net.devstudy.ishop.servlet.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.devstudy.ishop.form.ProductForm;
import net.devstudy.ishop.model.ShoppingCart;
import net.devstudy.ishop.util.SessionUtils;

@WebServlet("/ajax/json/product/remove")
public class RemoveProductController extends AbstractProductController {
	private static final long serialVersionUID = 1L;

	@Override
	protected void processProductForm(ProductForm form, ShoppingCart shoppingCart, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		getOrderService().removeProductFromShoppingCart(form, shoppingCart);
		if (shoppingCart.getItems().isEmpty()) {
			SessionUtils.clearCurrentShoppingCart(req, resp);
		}
		String cookieValue = getOrderService().serializeShoppingCart(shoppingCart);
		SessionUtils.updateCurrentShoppingCartCookie(cookieValue, resp);
	}

}
