package poly.edu.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import poly.edu.entity.Account;
import poly.edu.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    // Các phương thức truy vấn cụ thể nếu cần
	
	// Tìm RoleName bằng AccountID

}
