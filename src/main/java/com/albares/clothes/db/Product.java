package com.albares.clothes.db;

/**
 * METODOS API JDBC: executeQuery() => Esta entencia sirve para ejecutar
 * consultas que recuperan datos,devuelve un objeto 'ResulSet'que contiene el
 * resultado de la consulta => SELECT executeUpdate() => Para ejecutar
 * sentencias que modifican la BD => INSERT,UPDATE,DELETE, CREATE,ALTER
 * execute() => Para cualquier tipo de sentencias.
 */
import com.albares.clothes.utils.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    private Integer id;
    private String name;
    private Integer price;
    private Integer stock;
    private Integer gender;

    public Product() {

    }

    public Product( String name, Integer price) {
        this.name = name;
        this.price = price;
    }
    

    public Product(Integer id, String name, Integer price, Integer stock, Integer gender) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.gender = gender;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }


    
    public void insertProduct_DB(Db myDb) throws SQLException, NoSuchAlgorithmException {
        PreparedStatement ps = myDb.prepareStatement(
                "INSERT INTO products (name, price, stock, gender) VALUES (?, ?, ?, ?);"
        );
        ps.setString(1, this.getName());
        ps.setInt(2, this.getPrice());
        ps.setInt(3, this.getStock());
        ps.setInt(4, this.getGender());
        ps.executeUpdate();
    }

   
    public static List selectAllProducts_DB(Db myDb) throws SQLException {
        PreparedStatement ps = myDb.prepareStatement(
                "SELECT id,name,price,stock,gender FROM products;"
        );

        ResultSet rs = myDb.executeQuery(ps);
        List<Product> products = new ArrayList();

        while (rs.next()) {

            Product product = new Product(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getInt(4),
                    rs.getInt(5)
            );
            products.add(product);
        }
        return products;
    }

    public static List selectAllProductsGender_DB(Db myDb, int gender) throws SQLException {
        PreparedStatement ps = myDb.prepareStatement(
                "SELECT id,name,price,stock,gender FROM products WHERE gender = ?;"
        );
        ps.setInt(1, gender);
        ResultSet rs = myDb.executeQuery(ps);
        List<Product> products = new ArrayList();

        while (rs.next()) {
            Product product = new Product(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getInt(3),
                    rs.getInt(4),
                    rs.getInt(5)
            );
            products.add(product);
        }
        return products;
    }

    public boolean buyProduct_DB(Db myDb,int quantity) throws SQLException {
        PreparedStatement ps = myDb.prepareStatement(
                "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ? ;"
        );
        ps.setInt(1, quantity);
        ps.setInt(2, this.getId());
        ps.setInt(3, quantity);
        if (ps.executeUpdate() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void editProduct_DB(Db myDb) throws SQLException {
        String update = "UPDATE products SET ";
        List<String> fields = new ArrayList();
        if (this.getName() != null) {
            fields.add("name");
        }
        if (this.getGender() != null) {
            fields.add("gender");
        }
        if (this.getStock() != null) {
            fields.add("stock");
        }
        if (this.getPrice() != null) {
            fields.add("price");
        }
        for (int i = 0; i < fields.size(); i++) {
            update += fields.get(i) + "= ?";
            if (i != fields.size() - 1) {
                update += ",";
            }
        }
        update += " where id = ?;";
        PreparedStatement ps = myDb.prepareStatement(update);
        for (int i = 0; i < fields.size(); i++) {
            switch (fields.get(i)) {
                case "name":
                    ps.setString(i + 1, this.getName());
                    break;
                case "gender":
                    ps.setInt(i + 1, this.getGender());
                    break;
                case "stock":
                    ps.setInt(i + 1, this.getStock());
                    break;
                case "price":
                    ps.setInt(i + 1, this.getPrice());
                    break;
            }
        }
        ps.setInt(fields.size() + 1, this.getId());
        ps.executeUpdate();
    }

}
