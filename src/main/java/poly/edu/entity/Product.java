package poly.edu.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private int productId;

    @Column(name = "Productname")
    private String productname;

    @Column(name = "Productdescription")
    private String productdescription;

    @Column(name = "Productimage")
    private String productimage;

    @Column(name = "Productprice")
    private double productprice;

    @Column(name = "Productactivate")
    private boolean productactivate;

    @Column(name = "Productsale")
    private double productsale;

    // Thêm các trường khác cho thông tin sản phẩm
}
