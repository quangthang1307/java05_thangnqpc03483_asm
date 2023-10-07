package poly.edu.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;


import poly.edu.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Các phương thức truy vấn cụ thể nếu cần
	
	Product findByProductId(int productId);
}
