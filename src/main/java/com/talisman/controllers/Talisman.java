package com.talisman.controllers;

import com.talisman.Utils.SessionUtils;
import com.talisman.constants.ParamConstants;
import com.talisman.dao.OrderDAO;
import com.talisman.dao.ProductDAO;
import com.talisman.entity.Product;
import com.talisman.model.CartDetailInfo;
import com.talisman.model.CustomerInfo;
import com.talisman.model.Paginate;
import com.talisman.model.ProductInfo;
import com.talisman.validators.CustomerInfoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Transactional
@EnableWebMvc
public class Talisman {

    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private CustomerInfoValidator customerInfoValidator;

    @Autowired
    public Talisman(final OrderDAO orderDAO, final ProductDAO productDAO,
                    final CustomerInfoValidator customerInfoValidator) {

        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
        this.customerInfoValidator = customerInfoValidator;
    }

    @InitBinder
    public void webInitBinder(final WebDataBinder webDataBinder) {

        Object object = webDataBinder.getTarget();
        if(object == null) {
            return;
        }

        System.out.println("debug - Databinder : " + object);

        if(object.getClass() == CustomerInfo.class) {
            webDataBinder.setValidator(customerInfoValidator);
        }
    }

    @RequestMapping("/")
    public String homePage() {

        return "index";
    }

    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDenied() {

        return "accessDenied";
    }

    @RequestMapping(value = {"/productList"}, method = RequestMethod.GET)
    public String productListView(@RequestParam(value = "name", defaultValue = "") final String name,
                                        @RequestParam(value = "page", defaultValue = "1") final int page, final Model model) {

        Paginate<ProductInfo> resultList = productDAO.queryProducts(page, ParamConstants.MAX_RESULTS, ParamConstants.MAX_NAVIGATION_PAGE, name);
        model.addAttribute("paginateProducts", resultList);
        return "productList";
    }

    @RequestMapping(value = {"/shoppingCart"}, method = RequestMethod.GET)
    public String cartHandler(final HttpServletRequest request, final Model model) {

        CartDetailInfo clientCart = SessionUtils.getCartDetailInSession(request);
        model.addAttribute("cartDetail", clientCart);
        return "shoppingCart";
    }

    @RequestMapping(value = {"/shoppingCart"}, method = RequestMethod.POST)
    public String updateCartQuantity(final HttpServletRequest request,
                                   @ModelAttribute("cartDetail") final CartDetailInfo cartDetailInformation) {

        CartDetailInfo cartDetail = SessionUtils.getCartDetailInSession(request);
        cartDetail.updateProductQuantity(cartDetailInformation);
        return "redirect:/shoppingCart";

    }

    @RequestMapping(value = {"/buyProduct"}, method = RequestMethod.GET)
    public String productHandler(final HttpServletRequest request,
                                 @RequestParam(value = "code", defaultValue = "") final String productCode) {

        Product product = null;

        if(productCode != null && productCode.length() > 0) {
            product = productDAO.findProduct(productCode);
        }

        if(product != null) {
            CartDetailInfo cartDetailInformation = SessionUtils.getCartDetailInSession(request);
            ProductInfo productInformation = new ProductInfo(product);
            cartDetailInformation.addProductAndQuantity(productInformation,1);
        }
        return "redirect:/shoppingCart";
    }

    @RequestMapping(value = {"/removeCartProduct"}, method = RequestMethod.GET)
    public String removeProductHandler(final HttpServletRequest request,
                                       @RequestParam(value = "code", defaultValue = "") final String productCode) {

        Product product = null;
        if(productCode != null && productCode.length() > 0) {
            product = productDAO.findProduct(productCode);
        }

        if(product != null) {
            CartDetailInfo cartDetailInformation = SessionUtils.getCartDetailInSession(request);
            ProductInfo productInformation = new ProductInfo(product);
            cartDetailInformation.removeProduct(productInformation);
        }

        return "redirect:/shoppingCart";
    }

    @RequestMapping(value = {"/customerInformation"}, method = RequestMethod.GET)
    public String getCustomerInformation(final HttpServletRequest request, final Model model) {

        CartDetailInfo cartDetailInformation = SessionUtils.getCartDetailInSession(request);

        if(cartDetailInformation.isEmpty()) {
            return "redirect:/shoppingCart";
        }
        CustomerInfo customerInformation = cartDetailInformation.getCustomerInformation();

        if(customerInformation == null) {
            customerInformation = new CustomerInfo();
        }

        model.addAttribute("customerInformation", customerInformation);
        return "cartCustomer";
    }

    @RequestMapping(value = {"/customerInformation"}, method = RequestMethod.POST)
    public String saveCustomerInformation(final HttpServletRequest request,
                                         @ModelAttribute("customerInformation") @Validated final CustomerInfo customerInformation, final BindingResult result) {

        if(result.hasErrors()) {
            customerInformation.setValid(false);
            return "cartCustomer";
        }

        customerInformation.setValid(true);
        CartDetailInfo cartDetailInformation = SessionUtils.getCartDetailInSession(request);
        cartDetailInformation.setCustomerInformation(customerInformation);
        return "redirect:/cartConfirmation";
    }

    @RequestMapping(value = {"/cartConfirmation"}, method = RequestMethod.GET)
    public String cartConfirmationReview(final HttpServletRequest request) {

        CartDetailInfo cartDetailInformation = SessionUtils.getCartDetailInSession(request);
        if(cartDetailInformation.isEmpty()) {
            return "redirect:/shoppingCart";
        }
        else if(!cartDetailInformation.isValidCustomer()) {
            return "redirect:/customerInformation";
        }
        return "cartConfirmation";
    }

    @RequestMapping(value = {"/cartConfirmation"}, method = RequestMethod.POST)
    @Transactional(propagation = Propagation.NEVER)
    public String saveCartConfirmation(final HttpServletRequest request) {

        CartDetailInfo cartDetailInformation = SessionUtils.getCartDetailInSession(request);
        if(cartDetailInformation.isEmpty()) {
            return "redirect:/shoppingCart";
        }
        else if(!cartDetailInformation.isValidCustomer()) {
            return "redirect:/cartConfirmation";
        }

        try {
            orderDAO.saveOrder(cartDetailInformation);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "cartConfirmation";
        }

        SessionUtils.removeCartDetailInSession(request);
        SessionUtils.storeLastOrderCartDetailInSession(request,cartDetailInformation);
        return "redirect:/finalizeCart";
    }

    @RequestMapping(value = { "/finalizeCart" }, method = RequestMethod.GET)
    public String finalizeCart(final HttpServletRequest request) {

        CartDetailInfo lastOrderedCart = SessionUtils.getLastOrderCartDetailInSession(request);

        if(lastOrderedCart == null) {
            return "redirect:/shoppingCart";
        }
        return "finalCart";
    }

    @RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
    public void productImage(final HttpServletResponse response, @RequestParam("code") final String productCode) throws IOException {

        Product product = null;
        if (productCode != null) {
            product = this.productDAO.findProduct(productCode);
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }
}
