//package com.triloc.webapp.electonicstore.controller;
//
//
//import com.triloc.webapp.electonicstore.model.*;
//import com.triloc.webapp.electonicstore.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.math.RoundingMode;
//import java.text.DecimalFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Controller
//@RequestMapping("/shoppingcart")
//public class ShoppingCartController {
//
//
//    @Autowired
//    UserRepository userRepository;
//
//
//    @PostMapping("/update-cart")
//    public String updateCart(@RequestParam("productId") long productId, @ModelAttribute("product") ProductItem product, HttpSession session) {
//        List<ProductItem> cart = (List<ProductItem>) session.getAttribute("cartList");
//        for (ProductItem p : cart) {
//            if (p.getProductId() == productId) {
//                p.setQuantity(product.getQuantity());
//                break;
//            }
//        }
//        session.setAttribute("cartList", cart);
//        return "redirect:/shoppingcart";
//    }
//
//
//
//    @GetMapping("/Update")
//    @ResponseBody
//    public String updateShoppingCart(HttpServletRequest request, @RequestParam("productId") String productId,@RequestParam("productName") String productName, @RequestParam("price") String price, @RequestParam("count") int count) {
//        // Your code to update the shopping cart with the received productId and count values goes here
//        HttpSession session = request.getSession();
//        ArrayList<ProductItem> productItemArrayList = new ArrayList<>();
//        ProductItem productItem = new ProductItem();
//        productItem.setProductId(Long.parseLong(productId));
//        productItem.setQuantity(count);
//        productItem.setPrice(Double.parseDouble(price));
//        productItem.setName(productName);
//        if(session.getAttribute("cartList") == null){
//            productItemArrayList.add(productItem);
//            session.setAttribute("cartList",productItemArrayList);
//        }else{
//            productItemArrayList = (ArrayList<ProductItem>) session.getAttribute("cartList");
//            productItemArrayList.add(productItem);
//            session.setAttribute("cartList",productItemArrayList);
//
//        }
//        System.out.println(productItemArrayList);
//        // Return the size of the cartList hashmap as a string
//        return String.valueOf(productItemArrayList.size());
//    }
//
//    @GetMapping("/checkSession")
//    @ResponseBody
//    public String checkSession(HttpServletRequest request) {
//        // Your code to update the shopping cart with the received productId and count values goes here
//        System.out.println(request.getSession().getAttribute("1"));
//       return "session id:" + request.getSession().getId();
//    }
//
//
//    @GetMapping
//    public String getShoppingCart(Model model,HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        ArrayList<ProductItem> productItemArrayList= new ArrayList<>();
//        double totalAmount = 0.0;
//        if(session.getAttribute("cartList") != null){
//            productItemArrayList = (ArrayList<ProductItem>) session.getAttribute("cartList");
//            for(ProductItem productItem: productItemArrayList){
//                totalAmount = totalAmount + productItem.getQuantity()*productItem.getPrice();
//            }
//
//        }
//        DecimalFormat df = new DecimalFormat("#.##;-#.##");
//        df.setRoundingMode(RoundingMode.DOWN);
//        totalAmount = Double.parseDouble(df.format(totalAmount));
//        model.addAttribute("products",productItemArrayList);
//        model.addAttribute("totalAmount",totalAmount);
//        session.setAttribute("totalAmount",totalAmount);
//
//
//        return "shoppingcart";
//    }
//
//
//
//    @GetMapping("/checkout")
//    public  String checkContent(Model model, HttpServletRequest request){
//        HttpSession session = request.getSession();
//        String username = "";
//        if(session.getAttribute("username") == null){
//
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//             username = authentication.getName();
//            Long id = userRepository.findByUsername(username).getCustomer_id();
//
//        }
//        else{
//            username = session.getAttribute("username").toString();
//
//        }
//
//        User user  = userRepository.findByUsername(username);
//
//        model.addAttribute("username",username);
//        model.addAttribute("userId",user.getCustomer_id());
//        model.addAttribute("sessionId",request.getSession().getId());
//        return "checkout";
//
//    }
//
//    @PostMapping("/checkout")
//    public String processCheckout(HttpServletRequest request,@RequestParam("userId") String userId,
//                                  @RequestParam("sessionId") String sessionId,
//                                  @RequestParam("name") String name,
//                                  @RequestParam("address") String address,
//                                  @RequestParam("city") String city,
//                                  @RequestParam("state") String state,
//                                  @RequestParam("zip") String zip,
//                                  @RequestParam(value = "default-address", required = false) boolean defaultAddress,
//                                  @RequestParam("card-number") String cardNumber,
//                                  @RequestParam("card-holder-name") String cardHolderName,
//                                  @RequestParam("expiration-date") String expirationDate,
//                                  @RequestParam("security-code") String securityCode) {
//        Address addressInfo = new Address();
//
//        addressInfo.setCity(city);
//        addressInfo.setState(state);
//        addressInfo.setZipCode(zip);
//
//        HttpSession session = request.getSession();
//        String username = "";
//        User user = null;
//        if(session.getAttribute("username") == null){
//
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//            username = authentication.getName();
//            user = userRepository.findByUsername(username);
//
//        }
//        else{
//            username = session.getAttribute("username").toString();
//            user = userRepository.findByUsername(username);
//
//
//        }
//        if(defaultAddress){
//
//            user.setAddress(addressInfo);
//        }
//        Order order = new Order();
//        order.setOrder_date(new Date());
//        List<ProductItem>productItemArrayList = (ArrayList<ProductItem>) session.getAttribute("cartList");
//        List<OrderDetail> orderDetails = new ArrayList<>();
//        for(ProductItem productItem: productItemArrayList){
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setQuantity(productItem.getQuantity());
//            orderDetail.setProductId(productItem.getProductId());
//
//            orderDetail.setQuantity(productItem.getQuantity());
//            orderDetail.setOrder(order);
//            orderDetails.add(orderDetail);
//
//        }
//        order.setOrderDetails(orderDetails);
//
//        order.setUser(user);
//        double totalAmount = (double) session.getAttribute("totalAmount");
//
//        order.setTotal_amount(totalAmount);
//        Payment payment = new Payment();
//        payment.setAmount(totalAmount);
//        payment.setCard_number(cardNumber);
//        payment.setCvv(securityCode);
//        payment.setCardholder_name(cardHolderName);
//        order.setPayment(payment);
//        List<Order> orderList = user.getOrders();
//        orderList.add(order);
//        user.setOrders(orderList);
//        userRepository.save(user);
//        session.removeAttribute("cartList");
//
//
//        return "checkout-success";
//    }
//
//
//}
