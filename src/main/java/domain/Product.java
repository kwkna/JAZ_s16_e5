package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name = "product.all", query = "SELECT p FROM Product p"),
        @NamedQuery(name = "product.id", query = "SELECT p FROM Product p WHERE p.id=:productId"),
        @NamedQuery(name = "category.product.id", query = "SELECT p FROM Product p JOIN Category c ON c.id=p.category.id WHERE p.id=:productId AND p.category.id=:categoryId"),
        @NamedQuery(name = "product.findByPrice", query = "SELECT p FROM Product p WHERE p.price BETWEEN :priceFrom AND :priceTo"),
        @NamedQuery(name = "product.findByName", query = "SELECT p FROM Product p WHERE p.title=:name")

})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private float price;
    @ManyToOne
    private Category category;
    private List<Comment> comments = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @XmlTransient
    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.PERSIST)
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public Comment getCommentById(int commentId) {
        for (Comment c : comments) {
            if (c.getId() == commentId)
                return c;
        }
        return null;
    }
}
