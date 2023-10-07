package poly.edu.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.edu.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // Các phương thức truy vấn cụ thể nếu cần
	Customer findByCustomerId(int customerId);
}
