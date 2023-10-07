package poly.edu.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import poly.edu.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // Các phương thức truy vấn cụ thể nếu cần
}
