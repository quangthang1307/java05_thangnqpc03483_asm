package poly.edu.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import poly.edu.entity.Product;
import poly.edu.interfaces.ProductRepository;
import poly.edu.service.SessionService;

@Controller
public class ProductController {
	
	@Autowired
	SessionService session;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	jakarta.servlet.http.HttpServletRequest request;
	
	@RequestMapping("/productadmin")
	public String showQLSP(Model model) {
		String role = session.get("role");
		if (role.equals("Admin")) {
			model.addAttribute("product", new Product());
			model.addAttribute("products", productRepository.findAll());
			return "admin/productadmin";
		} else {
			return "redirect:/index";
		}

	}

	@RequestMapping("/showproductsuser")
	public String showProductsUser(Model model, Product product) {
		model.addAttribute("productuser", product);
		List<Product> items = productRepository.findAll();
		model.addAttribute("productusers", items);
		return "user/sanpham";
	}
	
	@RequestMapping("/product/delete/{producteditId}")
	public String productdelete(Model model, @PathVariable("producteditId") Integer productId) {
		Product product = productRepository.findByProductId(productId);
		productRepository.delete(product);
		List<Product> items = productRepository.findAll();
		model.addAttribute("products", items);
		return "redirect:/productadmin";
	}

	@RequestMapping("/product/edit/{producteditId}")
	public String productedit(@PathVariable("producteditId") Integer productId, Model model) {
		Product product = productRepository.findByProductId(productId);
		model.addAttribute("product", product);
		List<Product> items = productRepository.findAll();
		model.addAttribute("products", items);
		return "admin/productadmin";
	}
	
	@PostMapping("/product/edit/saveProduct")
	public String updateProduct(@ModelAttribute("Product") Product product,
			@RequestParam("productId") Integer productId) {
		boolean isActivate = request.getParameter("productactivate") != null;
		if (productId != 0) {
			Product existingProduct = productRepository.findByProductId(productId);
			if (existingProduct != null) {
				existingProduct.setProductname(product.getProductname());
				existingProduct.setProductdescription(product.getProductdescription());
				existingProduct.setProductimage(product.getProductimage());
				existingProduct.setProductprice(product.getProductprice());
				existingProduct.setProductactivate(isActivate);
				existingProduct.setProductsale(product.getProductsale());

				productRepository.save(existingProduct);
			}
		} else {
			productRepository.saveAndFlush(product);
		}
		return "redirect:/productadmin";
	}
}
