package poly.edu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private int customerId;

    @Column(name = "Customername")
    private String customername;

    @Column(name = "Customersphonenumber")
    private String customersphonenumber;

    @Column(name = "Customersemail")
    private String customersemail;

    @Column(name = "Customersaddress")
    private String customersaddress;

    @Column(name = "AccountID")
    private int accountid;

    // Thêm các trường khác cho thông tin khách hàng
}
