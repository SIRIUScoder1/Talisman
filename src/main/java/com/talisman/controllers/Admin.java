package com.talisman.controllers;

import com.talisman.constants.ParamConstants;
import com.talisman.dao.OrderDAO;
import com.talisman.dao.ProductDAO;
import com.talisman.model.OrderDetailInfo;
import com.talisman.model.OrderInfo;
import com.talisman.model.Paginate;
import com.talisman.model.ProductInfo;
import com.talisman.validators.ProductInfoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Controller
@Transactional
@EnableWebMvc
public class Admin {

    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private ProductInfoValidator productInfoValidator;
    private ResourceBundleMessageSource messageSource;

    @Autowired
    public Admin(final OrderDAO orderDAO, final ProductDAO productDAO, final ProductInfoValidator productInfoValidator,
                 final ResourceBundleMessageSource messageSource) {

        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
        this.productInfoValidator = productInfoValidator;
        this.messageSource = messageSource;
    }

    @InitBinder
    public void webInitBinder(final WebDataBinder webDataBinder) {

        Object object =  webDataBinder.getTarget();
        if(object == null) {
            return;
        }

        System.out.println("Debug Admin databinder = " + object);

        if(object.getClass() == ProductInfo.class) {
            webDataBinder.setValidator(productInfoValidator);
            webDataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String loginPage(final Model model) {

        return "login";
    }

    @RequestMapping(value = { "/accountInformation" }, method = RequestMethod.GET)
    public String accountInfo(final Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.isEnabled());
        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }

    @RequestMapping(value = {"/orderList"}, method = RequestMethod.GET)
    public String orderList(@RequestParam(value = "page", defaultValue = "1") final String pageString, final Model model) {

        int page = 1;
        try {
            page = Integer.parseInt(pageString);
        } catch (Exception ex) {
            System.out.println("An exception has occured " + ex.toString());
        }

        Paginate<OrderInfo>  paginateResults = this.orderDAO.getOrderInformation(page, ParamConstants.MAX_RESULTS, ParamConstants.MAX_NAVIGATION_PAGE);
        model.addAttribute("paginationResult", paginateResults);
        return "orderList";
    }

    @RequestMapping(value = {"/product"}, method = RequestMethod.GET)
    public String findProductInformation(@RequestParam(value = "code", defaultValue = "") final String productCode,
                                         final Model model) {

        ProductInfo productInformation = null;

        if(productCode != null && productCode.length() > 0) {
            productInformation = this.productDAO.findProductInformation(productCode);
        }

        if(productInformation == null) {
            productInformation = new ProductInfo();
            productInformation.setNewProduct(true);
        }
        model.addAttribute("productInfo", productInformation);
        return "product";
    }

    @RequestMapping(value = {"/product"}, method = RequestMethod.POST)
    @Transactional(propagation = Propagation.NEVER)
    public String saveProduct(@ModelAttribute("productInfo") @Validated ProductInfo productInformation,
                              final BindingResult bindingResult, final Model model) {

        if(bindingResult.hasErrors()) {
            return "product";
        }
        try {
            this.productDAO.save(productInformation);
        } catch (Exception ex) {

            String exceptionMessage = ex.getMessage();
            System.out.println(exceptionMessage);
            model.addAttribute("message", exceptionMessage);
            return "product";
        }
        return "redirect:/productList";
    }

    @RequestMapping(value = {"/order"}, method = RequestMethod.GET)
    public String viewOrder(@RequestParam("orderId") final String orderId, final Model model) {

        OrderInfo orderInformation = null;
        if(orderId != null) {
            orderInformation = this.orderDAO.getOrderInformation(orderId);
        }
        if(orderInformation == null) {
            return "redirect:/orderList";
        }

        List<OrderDetailInfo> orderDetailInfoList = this.orderDAO.getOrderDetailInformation(orderId);
        orderInformation.setOrderList(orderDetailInfoList);
        model.addAttribute("orderInfo", orderInformation);
        return "order";
    }
}
