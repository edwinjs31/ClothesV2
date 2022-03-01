package com.albares.clothes.utils;

import com.albares.clothes.db.Customer;
import com.albares.clothes.db.Product;

/**
 *
 * @author Edwin Jaldin S.
 */
public class GenericData {

    private Product product;
    private Customer customer;

    public GenericData() {
    }

    public GenericData(Product product) {
        this.product = product;
    }

    public GenericData(Customer customer) {
        this.customer = customer;
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

}
