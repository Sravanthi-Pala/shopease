package com.ecommerce.demo.controller;

import com.ecommerce.demo.entity.*;
import com.ecommerce.demo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class MainController {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private WishlistRepository wishlistRepository;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderItemRepository orderItemRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    private String getSessionId(HttpSession session) {
        String sessionId = (String) session.getAttribute("SESSION_ID");
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString();
            session.setAttribute("SESSION_ID", sessionId);
        }
        return sessionId;
    }
    
    // ==================== HOME & PRODUCTS ====================
    
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    @GetMapping("/products")
    public String products(@RequestParam(required = false) String category, Model model) {
        if (category != null && !category.isEmpty()) {
            model.addAttribute("products", productRepository.findByCategory(category));
            model.addAttribute("selectedCategory", category);
        } else {
            model.addAttribute("products", productRepository.findAll());
        }
        return "products";
    }
    
    @GetMapping("/api/products")
    @ResponseBody
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    @GetMapping("/api/products/category/{category}")
    @ResponseBody
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return productRepository.findByCategory(category);
    }
    
    // ==================== AUTHENTICATION ====================
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) model.addAttribute("error", "Invalid email or password");
        return "login";
    }
    
    @PostMapping("/login")
    public String doLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            session.setAttribute("user", userOpt.get());
            return "redirect:/";
        }
        return "redirect:/login?error=true";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    
    @GetMapping("/api/user-status")
    @ResponseBody
    public Map<String, Object> getUserStatus(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Map<String, Object> status = new HashMap<>();
        if (user != null) {
            status.put("loggedIn", true);
            status.put("email", user.getEmail());
            status.put("fullName", user.getFullName());
            status.put("isAdmin", "ADMIN".equals(user.getRole()));
        } else {
            status.put("loggedIn", false);
        }
        return status;
    }
    
    // ==================== PROFILE & ADDRESS ====================
    
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        model.addAttribute("addresses", addressRepository.findByUser(user));
        return "profile";
    }
    
    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String fullName, @RequestParam String phone, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            user.setFullName(fullName);
            user.setPhone(phone);
            userRepository.save(user);
            session.setAttribute("user", user);
        }
        return "redirect:/profile";
    }
    
    @PostMapping("/address/add")
    public String addAddress(@ModelAttribute Address address, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        address.setUser(user);
        addressRepository.save(address);
        return "redirect:/profile";
    }
    
    @GetMapping("/address/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {
        addressRepository.deleteById(id);
        return "redirect:/profile";
    }
    
    @GetMapping("/settings")
    public String settings(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        return "settings";
    }
    
    @GetMapping("/notifications")
    public String notifications(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        model.addAttribute("notifications", notificationRepository.findByUserOrderByCreatedAtDesc(user));
        return "notifications";
    }
    
    // ==================== CART ====================
    
    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<CartItem> cartItems;
        
        if (user != null) {
            cartItems = cartRepository.findByUser(user);
        } else {
            cartItems = cartRepository.findBySessionId(getSessionId(session));
        }
        
        double total = 0;
        if (cartItems != null) {
            for (CartItem item : cartItems) {
                total += item.getProduct().getPrice().doubleValue() * item.getQuantity();
            }
        }
        
        model.addAttribute("cartItems", cartItems != null ? cartItems : new ArrayList<>());
        model.addAttribute("total", total);
        return "cart";
    }
    
    @PostMapping("/cart/add")
    @ResponseBody
    public Map<String, Object> addToCart(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Optional<Product> productOpt = productRepository.findById(productId);
        
        if (productOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Product not found");
            return response;
        }
        Product product = productOpt.get();
        
        try {
            if (user != null) {
                Optional<CartItem> existing = cartRepository.findByUserAndProduct(user, product);
                if (existing.isPresent()) {
                    existing.get().setQuantity(existing.get().getQuantity() + quantity);
                    cartRepository.save(existing.get());
                } else {
                    CartItem cartItem = new CartItem();
                    cartItem.setUser(user);
                    cartItem.setProduct(product);
                    cartItem.setQuantity(quantity);
                    cartRepository.save(cartItem);
                }
            } else {
                String sessionId = getSessionId(session);
                List<CartItem> existingItems = cartRepository.findBySessionId(sessionId);
                Optional<CartItem> existing = existingItems.stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();
                if (existing.isPresent()) {
                    existing.get().setQuantity(existing.get().getQuantity() + quantity);
                    cartRepository.save(existing.get());
                } else {
                    CartItem cartItem = new CartItem();
                    cartItem.setSessionId(sessionId);
                    cartItem.setProduct(product);
                    cartItem.setQuantity(quantity);
                    cartRepository.save(cartItem);
                }
            }
            response.put("success", true);
            response.put("message", "Product added to cart");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
    
    @PostMapping("/cart/update")
    @ResponseBody
    public Map<String, Object> updateCartQuantity(@RequestParam Long cartItemId, @RequestParam int quantity) {
        Map<String, Object> response = new HashMap<>();
        Optional<CartItem> itemOpt = cartRepository.findById(cartItemId);
        if (itemOpt.isPresent()) {
            if (quantity <= 0) {
                cartRepository.delete(itemOpt.get());
            } else {
                itemOpt.get().setQuantity(quantity);
                cartRepository.save(itemOpt.get());
            }
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return response;
    }
    
    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id) {
        cartRepository.deleteById(id);
        return "redirect:/cart";
    }
    
    @GetMapping("/api/cart/count")
    @ResponseBody
    public int getCartCount(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return cartRepository.findByUser(user).stream().mapToInt(CartItem::getQuantity).sum();
        }
        return cartRepository.findBySessionId(getSessionId(session)).stream().mapToInt(CartItem::getQuantity).sum();
    }
    
    // ==================== WISHLIST ====================
    
    @GetMapping("/wishlist")
    public String wishlist(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<WishlistItem> wishlistItems;
        
        if (user != null) {
            wishlistItems = wishlistRepository.findByUser(user);
        } else {
            wishlistItems = wishlistRepository.findBySessionId(getSessionId(session));
        }
        
        model.addAttribute("wishlistItems", wishlistItems != null ? wishlistItems : new ArrayList<>());
        return "wishlist";
    }
    
    @PostMapping("/wishlist/add")
    @ResponseBody
    public Map<String, Object> addToWishlist(@RequestParam Long productId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("user");
        Optional<Product> productOpt = productRepository.findById(productId);
        
        if (productOpt.isEmpty()) {
            response.put("success", false);
            return response;
        }
        Product product = productOpt.get();
        
        try {
            if (user != null) {
                if (wishlistRepository.findByUserAndProduct(user, product).isEmpty()) {
                    WishlistItem item = new WishlistItem();
                    item.setUser(user);
                    item.setProduct(product);
                    wishlistRepository.save(item);
                }
            } else {
                String sessionId = getSessionId(session);
                List<WishlistItem> existingItems = wishlistRepository.findBySessionId(sessionId);
                if (existingItems.stream().noneMatch(item -> item.getProduct().getId().equals(productId))) {
                    WishlistItem item = new WishlistItem();
                    item.setSessionId(sessionId);
                    item.setProduct(product);
                    wishlistRepository.save(item);
                }
            }
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
        }
        return response;
    }
    
    @GetMapping("/wishlist/remove/{id}")
    public String removeFromWishlist(@PathVariable Long id) {
        wishlistRepository.deleteById(id);
        return "redirect:/wishlist";
    }
    
    @GetMapping("/api/wishlist/count")
    @ResponseBody
    public int getWishlistCount(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return wishlistRepository.findByUser(user).size();
        }
        return wishlistRepository.findBySessionId(getSessionId(session)).size();
    }
    
    // ==================== CHECKOUT & ORDERS ====================
    
    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        List<CartItem> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) return "redirect:/cart";
        
        model.addAttribute("addresses", addressRepository.findByUser(user));
        model.addAttribute("cartItems", cartItems);
        
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getProduct().getPrice().doubleValue() * item.getQuantity();
        }
        model.addAttribute("total", total);
        
        return "checkout";
    }
    
    @PostMapping("/place-order")
    @ResponseBody
    public Map<String, Object> placeOrder(@RequestParam Long addressId, @RequestParam String paymentMethod, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.put("success", false);
            response.put("message", "Please login first");
            return response;
        }
        
        Optional<Address> addressOpt = addressRepository.findById(addressId);
        if (addressOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Invalid address");
            return response;
        }
        
        List<CartItem> cartItems = cartRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            response.put("success", false);
            response.put("message", "Cart is empty");
            return response;
        }
        
        double subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getProduct().getPrice().doubleValue() * item.getQuantity();
        }
        double shipping = subtotal > 500 ? 0 : 40;
        double tax = subtotal * 0.18;
        double total = subtotal + shipping + tax;
        
        Order order = new Order();
        order.setOrderNumber("ORD" + System.currentTimeMillis());
        order.setUser(user);
        order.setShippingAddress(addressOpt.get());
        order.setOrderDate(LocalDateTime.now());
        order.setSubtotal(subtotal);
        order.setShippingCost(shipping);
        order.setTax(tax);
        order.setTotal(total);
        order.setPaymentMethod(paymentMethod);
        order.setOrderStatus("CONFIRMED");
        order.setPaymentStatus("PAID");
        
        Order savedOrder = orderRepository.save(order);
        
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setProductName(cartItem.getProduct().getName());
            orderItem.setProductImage(cartItem.getProduct().getImageUrl());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItemRepository.save(orderItem);
        }
        
        Notification notification = new Notification();
        notification.setTitle("Order Confirmed");
        notification.setMessage("Your order " + savedOrder.getOrderNumber() + " has been confirmed! Total: ?" + total);
        notification.setType("ORDER");
        notification.setUser(user);
        notificationRepository.save(notification);
        
        cartRepository.deleteAll(cartItems);
        
        response.put("success", true);
        response.put("orderNumber", savedOrder.getOrderNumber());
        return response;
    }
    
    @GetMapping("/orders")
    public String orders(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        model.addAttribute("orders", orderRepository.findByUserOrderByOrderDateDesc(user));
        return "orders";
    }
    
    @GetMapping("/order/{id}")
    public String orderDetail(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty() || !orderOpt.get().getUser().getId().equals(user.getId())) {
            return "redirect:/orders";
        }
        model.addAttribute("order", orderOpt.get());
        return "order-detail";
    }
    
    // ==================== ADMIN ====================
    
    @GetMapping("/admin")
    public String adminDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) return "redirect:/login";
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "admin/dashboard";
    }
    
    @PostMapping("/admin/order/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isPresent()) {
            orderOpt.get().setOrderStatus(status);
            orderRepository.save(orderOpt.get());
        }
        return "redirect:/admin";
    }
}
