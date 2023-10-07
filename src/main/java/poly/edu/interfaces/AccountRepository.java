package poly.edu.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import poly.edu.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    // Các phương thức truy vấn cụ thể nếu cần
	// Tìm tài khoản bằng username và password
    @Query("SELECT a FROM Account a WHERE a.username = :username AND a.password = :password")
	Account findByUsernameAndPassword(String username, String password);
 // Tìm tài khoản bằng username
    Account findByUsername(String username);
    
   
    @Query(value = "SELECT r.Rolename " +
            "FROM Accounts AS a " +
            "INNER JOIN Employees AS e ON a.AccountID = e.AccountID " +
            "INNER JOIN Roles AS r ON e.RoleID = r.RoleID " +
            "WHERE a.AccountID = ?", nativeQuery = true)
    String findRoleNameByAccountId(int accountId);

    @Query(value = "SELECT E.Employeeemail "
    		+ "FROM Employees E "
    		+ "INNER JOIN Accounts A ON E.AccountID = A.AccountID "
    		+ "WHERE A.Username = ?", nativeQuery = true)
    String findEmailbyUsername(String username);
 
    
}
