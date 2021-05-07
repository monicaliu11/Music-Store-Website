package cs636.music.presentation.web;

import cs636.music.domain.Cart;
import cs636.music.domain.CartItem;
import cs636.music.domain.Product;
import cs636.music.domain.User;
import cs636.music.service.CatalogService;
import cs636.music.service.ServiceException;
import cs636.music.service.data.CartItemData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

// Stub for CatalogController (rename to MusicController if no SalesController supplied)
@Controller
@SessionAttributes({"cart"})
public class CatalogController {

	// String constants for URLs
	static final String WELCOME_URL = "welcome.html";
	static final String WELCOME_VIEW = "welcome";

	static final String USER_WELCOME_URL ="userWelcome.html";
	static final String USER_WELCOME_VIEW = "WEB-INF/jsp/userWelcome";

	static final String SHOW_CATALOG = "showCatalog";
	static final String SHOW_CATALOG_VIEW = "WEB-INF/jsp/showCatalog";

	static final String PRODUCT_DETAIL = "productDetail";
	static final String PRODUCT_DETAIL_VIEW = "WEB-INF/jsp/productDetail";

	static final String ADD_TO_CART = "addToCart";

	static final String UPDATE_ITEM = "updateItem";
	static final String REMOVE_ITEM = "removeItem";

	static final String SHOW_CART = "showCart";
	static final String SHOW_CART_VIEW = "WEB-INF/jsp/showCart";

	@Autowired
	private CatalogService catalogService;

	@RequestMapping(WELCOME_URL)
	public String handleWelcome() {
		return WELCOME_VIEW;
	}

	@RequestMapping(USER_WELCOME_URL)
	public String handleUserWelcome() {
		return USER_WELCOME_VIEW;
	}

	@RequestMapping(SHOW_CATALOG)
	public String handleShowCatalog(Model model) throws ServiceException {
		List<Product> list = new ArrayList<>(catalogService.getProductList());
		Collections.sort(list, Comparator.comparing(Product::getCode));
		model.addAttribute("products", list);
		return SHOW_CATALOG_VIEW;
	}

	@RequestMapping(PRODUCT_DETAIL)
	public String handleProductDetail(@RequestParam("id") long id, Model model) throws ServiceException {
		Product product = catalogService.getProduct(id);
		model.addAttribute("product", product);
		return PRODUCT_DETAIL_VIEW;
	}

	@RequestMapping(ADD_TO_CART)
	public String handleAddToCart(@RequestParam("id") long id,
								  @RequestParam("quantity") int quantity,
								  @RequestParam(value = "from", required = false) String from,
								  @ModelAttribute("cart") Cart cart,
								  Model model) throws ServiceException {
		CartItem cartItem = cart.findItem(id);
		if (cartItem != null) {
			quantity += cartItem.getQuantity();
		}
		cart.addItem(new CartItem(id, quantity));
		return PRODUCT_DETAIL.equals(from) ? handleProductDetail(id, model) : handleShowCatalog(model);
	}

	@RequestMapping(UPDATE_ITEM)
	public String handleUpdateItem(@RequestParam("id") long id, @RequestParam("quantity") int quantity,
								   @ModelAttribute("cart") Cart cart, Model model) throws ServiceException {
		CartItem cartItem = cart.findItem(id);
		cartItem.setQuantity(quantity);
		return handleShowCart(model, cart);
	}

	@RequestMapping(REMOVE_ITEM)
	public String handleRemoveItem(@RequestParam("id") long id, @ModelAttribute("cart") Cart cart, Model model) throws ServiceException {
		cart.removeItem(id);
		return handleShowCart(model, cart);
	}

	@RequestMapping(SHOW_CART)
	public String handleShowCart(Model model, @ModelAttribute("cart") Cart cart) throws ServiceException {
		List<CartItemData> cartItemData = new ArrayList<>(catalogService.getCartInfo(cart));
		Collections.sort(cartItemData, Comparator.comparing(CartItemData::getCode));
		model.addAttribute("cartItems", cartItemData);
		return SHOW_CART_VIEW;
	}

	@ModelAttribute("cart")
	private Cart createCart() {
		return catalogService.createCart();
	}
}
