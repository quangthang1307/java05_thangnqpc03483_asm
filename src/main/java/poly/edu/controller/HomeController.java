package poly.edu.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import poly.edu.entity.Account;
import poly.edu.entity.Customer;
import poly.edu.entity.Product;
import poly.edu.interfaces.AccountRepository;
import poly.edu.interfaces.CustomerRepository;
import poly.edu.interfaces.ProductRepository;
import poly.edu.service.CookieService;
import poly.edu.service.SessionService;

@Controller

public class HomeController {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	ProductRepository productRepository;

	@Autowired
	jakarta.servlet.http.HttpServletRequest request;
	@Autowired
	SessionService session;
	@Autowired
	CookieService cookie;
	@Autowired
	ServletContext application;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping("/index")
	public String showIndex() {
		return "user/index";
	}

	@RequestMapping("/login")
	public String showLogin() {
		return "user/login";
	}

	@PostMapping("/login")
	public String loginSubmit(@RequestParam String username, @RequestParam String password, Model model) {
		Account account = accountRepository.findByUsernameAndPassword(username, password);
		if (account != null && account.isActivate()) {
			String role = accountRepository.findRoleNameByAccountId(account.getAccountId());
			if (role == null) {
				role = "Guest";
			}
			if (role != null && role.equals("Admin")) {
				// Chỉ khi vai trò là "Admin" mới được chuyển đến trang "admin/home"
				session.set("sessionID", account);
				session.set("role", role);
				session.set("accountId", account.getAccountId());
				return "admin/home";
			} else {
				// Đối với các vai trò khác, chuyển đến trang "index"
				session.set("sessionID", account);
				session.set("role", role);
				session.set("accountId", account.getAccountId());
				return "redirect:/index";
			}
		} else if (account != null && !account.isActivate()) {
			model.addAttribute("error", "Tài khoản đã bị đóng");
			return "user/login";
		} else {
			model.addAttribute("error", "Invalid username or password");
			return "user/login";
		}
	}

	@PostMapping("/register")
	public String register(@RequestParam String username, @RequestParam String password, Model model) {
		// Kiểm tra xem tên người dùng đã tồn tại trong cơ sở dữ liệu hay chưa
		Account existingAccount = accountRepository.findByUsername(username);

		if (existingAccount != null) {
			// Tên người dùng đã tồn tại, hiển thị thông báo lỗi
			model.addAttribute("message", "Tên người dùng đã tồn tại. Vui lòng chọn tên khác.");
			return "user/register";
		} else {
			// Tên người dùng chưa tồn tại, tiến hành đăng ký
			Account newAccount = new Account();
			newAccount.setUsername(username);
			newAccount.setPassword(password);
			newAccount.setActivate(true);
			accountRepository.save(newAccount);

			Account acccount = accountRepository.findByUsername(username);

			Customer custom = new Customer();
			custom.setAccountid(acccount.getAccountId());
			customerRepository.save(custom);

			model.addAttribute("message", "Đăng ký thành công!");
			return "user/login";
		}
	}

	@PostMapping("/forgotpass")
	public String forgotpass(@RequestParam String username, Model model) {
		Account existingAccount = accountRepository.findByUsername(username);
		String findEmail = accountRepository.findEmailbyUsername(username);
		if (existingAccount == null) {
			model.addAttribute("message", "Username không tồn tại");
			return "user/forgotpass";

		} else {
			String code = generateRandomCode();
			session.set("resetCode", code);
			String mail = findEmail;
			String email = findEmail;
			int atIndex = email.indexOf("@");

			String usernameconvert = email.substring(0, Math.min(atIndex, 3)) + "***";
			String domain = email.substring(atIndex);

			String maskedEmail = usernameconvert + domain;

			String subject = "Khôi phục mật khẩu tài khoản PHONE GROUP:  ... ";
			String content = "Đây là Code để khôi phục mật khẩu: " + code;
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			final String useremail = "vanghppc03907@fpt.edu.vn";
			final String password = "vnxcmdhpumnrxann";

			Session sessionEmail = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(useremail, password);
				}
			});

			try {

				// Tạo đối tượng MimeMessage
				MimeMessage message = new MimeMessage(sessionEmail);

				// Đặt thông tin người gửi
				message.setFrom(new InternetAddress(username));

				// Đặt thông tin người nhận
				message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mail));

				// Đặt tiêu đề email
				message.setSubject(subject);

				// Đặt nội dung email
				message.setText(content);

				// Gửi email
				Transport.send(message);

				// Chuyển hướng về trang thành công
				model.addAttribute("message", "Please check your mailbox: " + maskedEmail);
				session.set("sessionID", username);
				return "user/forgotpass";

			} catch (MessagingException e) {
				// Xử lý lỗi gửi email
				e.printStackTrace();
				// Chuyển hướng về trang lỗi
				model.addAttribute("message", "Lỗi");
				return "user/forgotpass";
			}

		}
	}

	@PostMapping("verifyCode")
	public String verifyCode(@RequestParam("code") String code, Model model) {
		String storedCode = session.get("resetCode");
		String sessionID = session.get("sessionID");
		if (storedCode != null && storedCode.equals(code)) {
			model.addAttribute("resetPass", "Nhập mật khẩu mới");
			model.addAttribute("message", "Xin chào " + sessionID + " hãy nhập mật khẩu mới");
			return "user/forgotpass";
		} else {
			model.addAttribute("message", "Code không đúng");
			return "user/forgotpass";
		}
	}

	@PostMapping("confirmPass")
	public String confirmPass(@RequestParam("newpassword") String newpassword, Model model) {
		String sessionID = session.get("sessionID");
		Account account = accountRepository.findByUsername(sessionID);
		account.setPassword(newpassword);
		accountRepository.save(account);
		return "redirect:/login";
	}

	@PostMapping("/useradmin")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {
		customerRepository.save(customer);
		return "redirect:/useradmin";
	}

	@PostMapping("/customer/edit/useradmin")
	public String updateCustomer(@ModelAttribute("customer") Customer updatedCustomer,
			@RequestParam("customerid") Integer customerId) {
		if (customerId != 0) {
			Customer existingCustomer = customerRepository.findByCustomerId(customerId);
			System.out.println(customerId);
			if (existingCustomer != null) {
				existingCustomer.setCustomername(updatedCustomer.getCustomername());
				existingCustomer.setCustomersphonenumber(updatedCustomer.getCustomersphonenumber());
				existingCustomer.setCustomersemail(updatedCustomer.getCustomersemail());
				existingCustomer.setCustomersaddress(updatedCustomer.getCustomersaddress());
				existingCustomer.setAccountid(updatedCustomer.getAccountid());

				customerRepository.save(existingCustomer);
			}
		} else {
			customerRepository.saveAndFlush(updatedCustomer);
		}
		return "redirect:/useradmin";
	}

	@PostMapping("/saveProduct")
	public String saveProduct(Model model, @ModelAttribute("product") Product product,
			@RequestParam("productimage") MultipartFile productImage) {

		boolean isActivate = request.getParameter("productactivate") != null;
		
		product.setProductactivate(isActivate);

		if (productImage.isEmpty()) {
			return "info";
		}

		Path path = Paths.get("D:/TaiXuong/image/");
		try {
			InputStream inputStream = productImage.getInputStream();
			Files.copy(inputStream, path.resolve(productImage.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			product.setProductimage(productImage.getOriginalFilename().toLowerCase());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

//		if (!productImage.isEmpty()) {
//			try {
//				 // Lấy phần mở rộng của tập tin hình ảnh
//		        String originalFileName = productImage.getOriginalFilename();
//		        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
//				
//				String imageFileName = "product_" + product.getProductId() + fileExtension; // Tạo tên tập tin duy nhất
//				Path imagePath = Paths.get("D:/TaiXuong/image/" + imageFileName);
//				Files.write(imagePath, productImage.getBytes());
//				product.setProductimage("/image/" + imageFileName); 
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		productRepository.save(product);
		return "redirect:/productadmin";
	}

//	@PostMapping("/product/edit/saveProduct")
//	public String updateProduct(@ModelAttribute("Product") Product product,
//			@RequestParam("productId") Integer productId) {
//		boolean isActivate = request.getParameter("productactivate") != null;
//		if (productId != 0) {
//			Product existingProduct = productRepository.findByProductId(productId);
//			if (existingProduct != null) {
//				existingProduct.setProductname(product.getProductname());
//				existingProduct.setProductdescription(product.getProductdescription());
//				existingProduct.setProductimage(product.getProductimage());
//				existingProduct.setProductprice(product.getProductprice());
//				existingProduct.setProductactivate(isActivate);
//				existingProduct.setProductsale(product.getProductsale());
//
//				productRepository.save(existingProduct);
//			}
//		} else {
//			productRepository.saveAndFlush(product);
//		}
//		return "redirect:/productadmin";
//	}

	@PostMapping("/editprofile")
	public String updateInforUser(Model model, @ModelAttribute("customer") Customer updatedCustomer) {
		Integer sessionID = session.get("accountId");
		Customer editprofile = customerRepository.findByCustomerId(sessionID);
		model.addAttribute("accountID", editprofile);
		editprofile.setCustomername(updatedCustomer.getCustomername());
		editprofile.setCustomersphonenumber(updatedCustomer.getCustomersphonenumber());
		editprofile.setCustomersemail(updatedCustomer.getCustomersemail());
		editprofile.setCustomersaddress(updatedCustomer.getCustomersaddress());
		customerRepository.save(editprofile);
		return "user/editprofile";
	}

	@GetMapping("/logout")
	public String logout(Model model) {
		session.remove("sessionID");
		return "redirect:/login";
	}

	@RequestMapping("/register")
	public String showRegister() {
		return "user/register";
	}

	@RequestMapping("/forgotpass")
	public String showForgotpass() {
		return "user/forgotpass";
	}

	@RequestMapping("/editprofile")
	public String showInformation(Model model) {
		Integer sessionID = session.get("accountId");
		Customer editprofile = customerRepository.findByCustomerId(sessionID);
		model.addAttribute("accountID", editprofile);

		return "user/editprofile";
	}

	@RequestMapping("/useradmin")
	public String showQLNG(Model model) {
		String role = session.get("role");
		if (role.equals("Admin")) {
			model.addAttribute("customer", new Customer());
			model.addAttribute("customers", customerRepository.findAll());
			return "admin/useradmin";
		} else {
			return "redirect:/index";
		}

	}

	@RequestMapping("/customer/edit/{customerId}")
	public String edit(Model model, @PathVariable("customerId") Integer customerId) {
		Customer customer = customerRepository.findByCustomerId(customerId);
		model.addAttribute("customer", customer);
		List<Customer> items = customerRepository.findAll();
		model.addAttribute("customers", items);
		return "admin/useradmin";
	}

	@RequestMapping("/customer/delete/{customerId}")
	public String delete(Model model, @PathVariable("customerId") Integer customerId) {
		Customer customer = customerRepository.findByCustomerId(customerId);
		customerRepository.delete(customer);
		List<Customer> items = customerRepository.findAll();
		model.addAttribute("customers", items);
		return "redirect:/useradmin";
	}

//	@RequestMapping("/productadmin")
//	public String showQLSP(Model model) {
//		String role = session.get("role");
//		if (role.equals("Admin")) {
//			model.addAttribute("product", new Product());
//			model.addAttribute("products", productRepository.findAll());
//			return "admin/productadmin";
//		} else {
//			return "redirect:/index";
//		}
//
//	}

//	@RequestMapping("/product/delete/{producteditId}")
//	public String productdelete(Model model, @PathVariable("producteditId") Integer productId) {
//		Product product = productRepository.findByProductId(productId);
//		productRepository.delete(product);
//		List<Product> items = productRepository.findAll();
//		model.addAttribute("products", items);
//		return "redirect:/productadmin";
//	}
//
//	@RequestMapping("/product/edit/{producteditId}")
//	public String productedit(@PathVariable("producteditId") Integer productId, Model model) {
//		Product product = productRepository.findByProductId(productId);
//		model.addAttribute("product", product);
//		List<Product> items = productRepository.findAll();
//		model.addAttribute("products", items);
//		return "admin/productadmin";
//	}

	public String generateRandomCode() {
		int codeLength = 5;
		String allowedChars = "0123456789";
		Random random = new Random();
		StringBuilder code = new StringBuilder();
		for (int i = 0; i < codeLength; i++) {
			int index = random.nextInt(allowedChars.length());
			char randomChar = allowedChars.charAt(index);
			code.append(randomChar);
		}

		return code.toString();
	}

}
