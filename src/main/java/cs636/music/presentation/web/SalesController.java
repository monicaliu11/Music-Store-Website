package cs636.music.presentation.web;

import cs636.music.domain.Cart;
import cs636.music.domain.LineItem;
import cs636.music.domain.Product;
import cs636.music.domain.User;
import cs636.music.service.CatalogService;
import cs636.music.service.SalesService;
import cs636.music.service.ServiceException;
import cs636.music.service.data.InvoiceData;
import cs636.music.service.data.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static cs636.music.presentation.web.CatalogController.USER_WELCOME_VIEW;

@Controller
@SessionAttributes({"cart", "user"})
public class SalesController {

    static final String CHECKOUT = "checkout";
    static final String INVOICE_VIEW = "WEB-INF/jsp/invoice";

    static final String LOGOUT = "logout";

    static final String REGISTER = "register";
    static final String REGISTER_VIEW = "WEB-INF/jsp/register";

    @Autowired
    private SalesService salesService;

    @Autowired
    private CatalogService catalogService;

    @RequestMapping(LOGOUT)
    public String handleLogout(@ModelAttribute("user") User user) {
        user.setEmailAddress(null);
        user.setFirstname(null);
        user.setLastname(null);
        return USER_WELCOME_VIEW;
    }

    @RequestMapping(CHECKOUT)
    public ModelAndView handleCheckout(
            @ModelAttribute("user") User user,
            @ModelAttribute("cart") Cart cart,
            Model model) throws ServiceException {
        if (user.getEmailAddress() == null) {
            return new ModelAndView("redirect:/" + REGISTER + "?from=" + CHECKOUT);
        } else {
            InvoiceData invoiceData = salesService.checkout(cart, user.getId());
            Set<LineItem> lineItems = invoiceData.getLineItems();
            Map<String, Product> productMap = new HashMap<>();
            if (lineItems != null) {
                for (LineItem item : lineItems) {
                    Product product = catalogService.getProductByCode(item.getProductCode());
                    productMap.put(item.getProductCode(), product);
                }
            }
            model.addAttribute("productMap", productMap);
            model.addAttribute("invoice", invoiceData);
            return new ModelAndView(INVOICE_VIEW);
        }
    }

    @RequestMapping(REGISTER)
    public String handleRegister(@RequestParam(value = "firstName", required = false) String firstName,
                                 @RequestParam(value = "lastName", required = false) String lastName,
                                 @RequestParam(value = "email", required = false) String email,
                                 @RequestParam(value = "address", required = false) String address,
                                 @RequestParam(value = "from") String from,
                                 @ModelAttribute("user") User user,
                                 Model model) throws ServiceException {
        UserData userData = null;
        try {
            userData = salesService.getUserInfoByEmail(email);
        } catch (Exception e) {}
        if (userData == null) {
            if (!isEmpty(firstName) && !isEmpty(lastName) && !isEmpty(email) && !isEmpty(address)) {
                salesService.registerUser(firstName, lastName, email);
                userData = salesService.getUserInfoByEmail(email);
                salesService.addUserAddress(userData.getId(), address);
            }
        }
        if (userData != null) {
            user.setId(userData.getId());
            user.setFirstname(userData.getFirstname());
            user.setLastname(userData.getLastname());
            user.setEmailAddress(email);
            return "forward:/" + from;
        } else {
            model.addAttribute("from", from);
            return REGISTER_VIEW;
        }
    }


    @ModelAttribute("user")
    private User createUser() {
        return new User();
    }

    private boolean isEmpty(String check) {
        return check == null || "".equals(check.trim());
    }
}
