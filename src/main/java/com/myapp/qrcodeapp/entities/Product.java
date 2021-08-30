package com.myapp.qrcodeapp.entities;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private float price;
    @Column
    private int quantity;
    @Column
    private String status;
    @Column()
    private String correctionLevel = "M";
    @Column
    private String img;

    public String getCorrectionLevel() {
        return correctionLevel;
    }

    public void setCorrectionLevel(String correctionLevel) {
        this.correctionLevel = correctionLevel;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product(String name, float price, int quantity, String status) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
