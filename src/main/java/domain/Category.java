package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name = "category.all", query = "SELECT c FROM Category c"),
        @NamedQuery(name = "category.id", query = "SELECT c FROM Category c WHERE c.id=:categoryId")
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Product> getProducts() {
        return products;
    }
    public Product getProductById(int productId){
        for (Product p:products) {
            if (p.getId()==productId)
                return p;
        }
        return null;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
