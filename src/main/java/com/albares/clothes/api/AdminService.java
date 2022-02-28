package com.albares.clothes.api;

import com.albares.clothes.db.Customer;
import com.albares.clothes.db.Product;
import com.albares.clothes.utils.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@Path("/admin")

public class AdminService {

    /*
    REQUEST:
    {
        "product":{}    //el admin puede dar de alta a cualquiera de los dos
        "customer":{}
    }
    RESPONSE:
    {
        "responseCode": 1 //OK
                        0 //Error
                       
    }
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(GenericData gd) throws SQLException, NoSuchAlgorithmException {
        Db myDb = new Db();
        myDb.connect();
        if (gd.getProduct() != null) {
            Product product = gd.getProduct();
            product.insertProduct_DB(myDb);
            myDb.disconnect();
            return new Response(ResponseCode.OK);
        } else if (gd.getCustomer() != null) {
            Customer customer = gd.getCustomer();
            customer.insertCustomer_DB(myDb);
            myDb.disconnect();
            return new Response(ResponseCode.OK);
        } else {
            return new Response(ResponseCode.ERROR);
        }

    }

    /*
    REQUEST:
    {
        http://localhost:8084/clothes/admin/products   //dos posibles opciones
        http://localhost:8084/clothes/admin/customers  
    }
    RESPONSE:
    {
        "responseCode": 1 //OK
                        0 //Error
    
        products:{...}    ||en funcion al REQUEST devolverá una lista u otra.
        customers:{...}
        
    }
     */
    @GET
    @Path("/{entity}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(@PathParam("entity") String entity) throws SQLException {
        Response r = new Response();
        Db myDb = new Db();
        myDb.connect();
        if (entity.equalsIgnoreCase("products")) {
            List<Product> products = Product.selectAllProducts_DB(myDb);
            myDb.disconnect();
            r.setResponseCode(ResponseCode.OK);
            r.setProducts(products);
            return r;
        } else if (entity.equalsIgnoreCase("customers")) {
            List<Customer> customers = Customer.selectAllCustomers_DB(myDb);
            myDb.disconnect();
            r.setResponseCode(ResponseCode.OK);
            r.setCustomers(customers);
            return r;
        } else {
            r.setResponseCode(ResponseCode.ERROR);
            return r;
        }

    }

    /*
    REQUEST:
    {
        "product":{
                    "id":4,
                    "gender":1
                    }      
                                    //se puede editar cualquiera de los dos
    
        "customer":{
                    "id":6,
                    "name":"Maria"
                    }
    }
    RESPONSE:
    {
        "responseCode": 1 //OK
                        0 //Error
                       
    }
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(GenericData gd) throws SQLException {
        Response r = new Response();
        Db myDb = new Db();
        myDb.connect();
        if (gd.getProduct() != null) {
            Product product = gd.getProduct();
            product.editProduct_DB(myDb);
            myDb.disconnect();
            r.setResponseCode(ResponseCode.OK);
            return r;
        } else if (gd.getCustomer() != null) {
            Customer customer = gd.getCustomer();
            customer.editCustomer_DB(myDb);
            myDb.disconnect();
            r.setResponseCode(ResponseCode.OK);
            return r;
        } else {
            r.setResponseCode(ResponseCode.ERROR);
            return r;
        }

    }

}
