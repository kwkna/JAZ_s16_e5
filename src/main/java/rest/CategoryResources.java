package rest;

import domain.Category;
import domain.Product;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
@Stateless
public class CategoryResources {

    @PersistenceContext
    EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getAllCat() {
        return em.createNamedQuery("category.all", Category.class).getResultList();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AddCat(Category category) {
        em.persist(category);
        return Response.ok(category.getId()).build();
    }
    @GET
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("categoryId") int categoryId){
        Category result = em.createNamedQuery("category.id", Category.class)
                .setParameter("categoryId",categoryId)
                .getSingleResult();
        if(result==null){
            return Response.status(404).build();
        }
        return Response.ok(result).build();
    }
    @PUT
    @Path("/{categoryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCat(@PathParam("categoryId") int categoryId, Category c) {
        Category result = em.createNamedQuery("category.id", Category.class)
                .setParameter("categoryId", categoryId)
                .getSingleResult();
        if (result == null) {
            return Response.status(404).build();
        }
        result.setName(c.getName());
        em.persist(result);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{categoryId}")
    public Response deleteCat(@PathParam("categoryId") int categoryId) {
        Category result = em.createNamedQuery("category.id", Category.class)
                .setParameter("categoryId", categoryId)
                .getSingleResult();
        if (result == null) {
            return Response.status(404).build();
        }
        em.remove(result);
        return Response.ok().build();
    }
/*******************************************************************************/
    @GET
    @Path("/{categoryId}/products")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProducts(@PathParam("categoryId") int categoryId) {
        Category result = em.createNamedQuery("category.id", Category.class)
                .setParameter("categoryId", categoryId)
                .getSingleResult();
        if (result == null)
            return null;
        return result.getProducts();
    }

    @GET
    @Path("/{categoryId}/products/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("categoryId") int categoryId,
                                    @PathParam("productId") int productId) {
        Category result = em.createNamedQuery("category.id", Category.class)
                .setParameter("categoryId", categoryId)
                .getSingleResult();
        if (result == null||result.getProductById(productId)==null)
            return Response.status(404).build();
        return Response.ok(result.getProductById(productId)).build();
    }

    @POST
    @Path("/{categoryId}/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(@PathParam("categoryId") int categoryId, Product product) {
        Category result = em.createNamedQuery("category.id", Category.class)
                .setParameter("categoryId", categoryId)
                .getSingleResult();
        if (result == null)
            return Response.status(404).build();
        result.getProducts().add(product);
        product.setCategory(result);
        em.persist(product);
        return Response.ok().build();
    }
    @PUT
    @Path("/{categoryId}/products/{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("categoryId") int categoryId, Product p,
                                  @PathParam("productId") int productId) {
        Product result = em.createNamedQuery("category.product.id", Product.class)
                .setParameter("productId", productId)
                .setParameter("categoryId",categoryId)
                .getSingleResult();
        if (result == null) {
            return Response.status(404).build();
        }
        result.setTitle(p.getTitle());
        result.setDescription(p.getDescription());
        result.setPrice(p.getPrice());
        em.persist(result);
        return Response.ok().build();
    }

}
