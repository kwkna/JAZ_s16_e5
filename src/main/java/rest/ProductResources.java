package rest;

import domain.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/products")
@Stateless
public class ProductResources {
    @PersistenceContext
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts(@QueryParam("from") int priceFrom,//metoda ktora pod adresem /products zwraca wszystkie porodukty
                                        @QueryParam("to") int priceTo,//te wszystkie @QueryParam to są parametry w adresie np./products?name=ASUS
                                        @QueryParam("name") String name) {//lub /products?from=1&to=1000
        if (name!=null){//jezeli w adresie jest parametr name to if jest true
            return em.createNamedQuery("product.findByName", Product.class)
                    .setParameter("name", name)
                    .getResultList();
        }
        if (priceFrom>0 && priceTo>0){
            return em.createNamedQuery("product.findByPrice", Product.class)
                    .setParameter("priceFrom", priceFrom)
                    .setParameter("priceTo", priceTo)
                    .getResultList();
        }
        return em.createNamedQuery("product.all", Product.class).getResultList();
    }

    @GET
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("productId") int productId) {
        Product result = em.createNamedQuery("product.id", Product.class)
                .setParameter("productId",productId)
                .getSingleResult();
        if (result == null)
            return Response.status(404).build();
        return Response.ok(result).build();
    }
}
