package com.albares.clothes.api;

import com.albares.clothes.db.Purchase;
import com.albares.clothes.db.Customer;
import com.albares.clothes.db.Product;
import com.albares.clothes.utils.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@Path("/admin")

public class AdminService {

    /*
    REQUEST:
    {
        "product":{...}    //el admin puede dar de alta a clientes y productos
        "customer":{...}
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
    public Response addResource(GenericData gd) throws SQLException, NoSuchAlgorithmException {
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
        http://localhost:8084/clothes/admin/products      //lista todos los productos.
        http://localhost:8084/clothes/admin/customers     //lista todos los clientes.
    }
    RESPONSE:
    {
        "responseCode": 1 //OK
                        0 //Error
    
        products:{...}    ||en funcion al REQUEST devolverá una lista u otra(2 posibles listas):
    
        customers:{...}
    
    }
     */
    @GET
    @Path("/{resource}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts(@PathParam("resource") String resource) throws SQLException {
        Response r = new Response();
        Db myDb = new Db();
        myDb.connect();

        if (resource.equalsIgnoreCase("products")) {
            List<Product> products = Product.selectAllProducts_DB(myDb);
            myDb.disconnect();
            r.setResponseCode(ResponseCode.OK);
            r.setProducts(products);
            return r;
        } else if (resource.equalsIgnoreCase("customers")) {
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
      http://localhost:8084/clothes/admin/purchases/customers/5    //Compras del cliente con id 5.
       
    }
    RESPONSE:
    {
      purchases:{"id":4,
                 "product":{"name":"Camiseta,"price":67},
                 "quantity":3,
                 "date":2022-02-28
                }
    
    }
     */
    @GET
    @Path("/purchases/customers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPurchaseByCustomer(@PathParam("id") int id) {
        Response r = new Response();
        try {
            Db myDb = new Db();
            myDb.connect();
            List<Purchase> buys = Purchase.selectBuysCustomer_DB(myDb, id);
            myDb.disconnect();
            r.setResponseCode(ResponseCode.OK);
            r.setPurchases(buys);
            return r;
        } catch (SQLException e) {
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
    public Response updateResource(GenericData gd) throws SQLException {
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
