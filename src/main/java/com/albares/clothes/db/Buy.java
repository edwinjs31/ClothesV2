package com.albares.clothes.db;

import com.albares.clothes.utils.Db;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Edwin Jaldin S.
 */
public class Buy {

    private Integer id;
    private Product product;
    private Customer customer;
    private Integer quantity;
    private Date date;

    public Buy() {
    }

    public Buy(Integer id, Product product, Integer quantity, Date date) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.date = date;
    }

    
    public Buy(Integer id, Product product, Customer customer, Integer quantity, Date date) {
        this.id = id;
        this.product = product;
        this.customer = customer;
        this.quantity = quantity;
        this.date = date;
    }
 
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void insertBuy_DB(Db myDb) throws SQLException {
        this.setDate(new Date());
        long timeMilis = this.getDate().getTime();
        java.sql.Date sqlDate = new java.sql.Date(timeMilis);

        PreparedStatement ps = myDb.prepareStatement(
                "INSERT INTO buys(id_product,id_customer,quantity,date) VALUES(?,?,?,?);");
        ps.setInt(1, this.getProduct().getId());
        ps.setInt(2, this.getCustomer().getId());
        ps.setInt(3, this.getQuantity());
        ps.setDate(4, sqlDate);
        ps.executeUpdate();
    }
    /*
    public static List selectBuysCustomer_DB(Db myDb,int id) throws SQLException {
        PreparedStatement ps = myDb.prepareStatement(
            "SELECT b.id,p.name,b.quantity,b.date" +
             "FROM products p,buys b,customers c" +
             "WHERE c.id=b.id_customer AND p.id=b.id_product AND c.id=?;");
        ps.setInt(1, id);
        ResultSet rs = myDb.executeQuery(ps);
        List<Buy> buysCustomer=new ArrayList();
        while(rs.next()){
            Product product=new Product();
            product.setName(rs.getString(2));
            
            Buy buy=new Buy(rs.getInt(1), product, rs.getInt(3), rs.getDate(4));
            buysCustomer.add(buy);
        }
        return buysCustomer;
    }*/
}
