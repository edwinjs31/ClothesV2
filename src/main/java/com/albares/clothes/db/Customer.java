package com.albares.clothes.db;

import com.albares.clothes.utils.Db;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Edwin Jaldin S.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {

    private Integer id;
    private String name;
    private String email;

    public Customer() {
    }

    public Customer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Customer(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void insertCustomer_DB(Db myDb) throws SQLException {
        PreparedStatement ps = myDb.prepareStatement(
                "INSERT INTO customers(name,email) VALUES(?,?);");
        ps.setString(1, this.getName());
        ps.setString(2, this.getEmail());
        ps.executeUpdate();
    }

    public static List selectAllCustomers_DB(Db myDb) throws SQLException {
        PreparedStatement ps = myDb.prepareStatement(
                "SELECT id,name,email FROM customers;");
        ResultSet rs = myDb.executeQuery(ps);
        List<Customer> customers = new ArrayList();
        while (rs.next()) {
            Customer customer = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3));
            customers.add(customer);
        }
        return customers;
    }

    public void editCustomer_DB(Db myDb) throws SQLException {
        String update = "UPDATE customers SET ";
        List<String> fields = new ArrayList();
        if (this.getName() != null) {
            fields.add("name");
        }
        if (this.getEmail() != null) {
            fields.add("email");
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
                case "email":
                    ps.setString(i + 1, this.getEmail());
                    break;
            }
        }
        ps.setInt(fields.size() + 1, this.getId());
        ps.executeUpdate();
    }
}
