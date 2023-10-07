package poly.edu.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.edu.entity.InvoiceDetail;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Integer> {
    // Các phương thức truy vấn cụ thể nếu cần
}
