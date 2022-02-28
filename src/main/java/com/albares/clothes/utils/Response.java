package com.albares.clothes.utils;

import com.albares.clothes.db.Buy;
import com.albares.clothes.db.Customer;
import com.albares.clothes.db.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private Integer responseCode;
    private List<Product> products;
    private List<Customer> customers;
    private List<Buy> buys;
    private GenericData genericData;

    public Response() {
    }

    public Response(Integer responseCode) {
        this.responseCode = responseCode;
    }    
    
    public Response(Integer responseCode, GenericData genericData) {
        this.responseCode = responseCode;
        this.genericData = genericData;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public GenericData getGenericData() {
        return genericData;
    }

    public void setGenericData(GenericData genericData) {
        this.genericData = genericData;
    }

    public List<Buy> getBuys() {
        return buys;
    }

    public void setBuys(List<Buy> buys) {
        this.buys = buys;
    }

    
}