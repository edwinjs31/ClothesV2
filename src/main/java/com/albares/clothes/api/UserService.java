package com.albares.clothes.api;

import com.albares.clothes.db.*;
import com.albares.clothes.utils.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@Path("/user")
public class UserService {

    /*
    REQUEST:
    {
       http://localhost:8084/clothes/user/buy
    
      "product":{
                "id":2,
                }, 
    
      "customer":{
                  "id":3
                  },
    
       "quantity":2
    }
    RESPONSE:
    {
      "responseCode": 1 //OK
                     -1 //OUT_OF_STOCK
                      0 //Error
    }
     */
    @POST
    @Path("/buy")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response buyProductCustomer(Purchase buy) throws NoSuchAlgorithmException, SQLException {
        Response r = new Response();
        Db myDb = new Db();
        myDb.connect();
        //si existe compras
        if (buy != null) {
            //en toda compra existe un cliente y producto(ALOMEJOR ES REDUNDANTE)
            if (buy.getProduct() != null && buy.getCustomer() != null) {
                Product product = buy.getProduct();
                int quantity = buy.getQuantity();
                if (product.buyProduct_DB(myDb, quantity)) {
                    buy.insertBuy_DB(myDb);
                    myDb.disconnect();
                    r.setResponseCode(ResponseCode.OK);
                    return r;
                } else {
                    r.setResponseCode(ResponseCode.OUT_OF_STOCK);
                    return r;
                }

            } else {//si falta algun atributo de COMPRAS
                r.setResponseCode(ResponseCode.ERROR);
                return r;
            }
        } else {//si no existe compras
            r.setResponseCode(ResponseCode.ERROR);
            return r;
        }
    }

    
    
    /*
    REQUEST:
    {
        http://localhost:8084/clothes/user/1  || 2
    }
    RESPONSE:
    {
      "responseCode": 1 //OK
                      0 //Error
    }
     */
    @GET
    @Path("/{gender}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsStock(@PathParam("gender") int gender) {
        Response r = new Response();
        try {
            Db myDb = new Db();
            myDb.connect();
            List<Product> products = Product.selectAllProductsGender_DB(myDb, gender);
            myDb.disconnect();

            r.setProducts(products);
            r.setResponseCode(ResponseCode.OK);
        } catch (SQLException e) {
            r.setResponseCode(ResponseCode.ERROR);
        }
        return r;
    }
}
