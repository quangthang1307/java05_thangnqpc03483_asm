package poly.edu.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.edu.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    // Các phương thức truy vấn cụ thể nếu cần
}
