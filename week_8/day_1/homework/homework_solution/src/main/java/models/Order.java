package models;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    private int id;
    private String orderCode;
    private String description;

    public Order() {
    }

    public Order(String orderCode, String description) {
        this.orderCode = orderCode;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "order_code")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
