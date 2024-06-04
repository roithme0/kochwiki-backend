package org.acme.CustomUser;

import java.util.Map;

import org.acme.ErrorResponse.ErrorResponse;
import org.jboss.logging.Logger;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class CustomUserService {
    /**
     * Logger for this class.
     */
    private static final Logger LOG = Logger.getLogger(CustomUserService.class);

    /**
     * Resource to access users.
     */
    @Inject
    private CustomUserResource userResource;

    @Inject
    EntityManager em;

    /**
     * @param username
     * @return user or error.
     */
    @GET
    @Path("/{username}")
    public Response findByUsername(@PathParam("username") final String username) {
        LOG.info("GET: find user with username '" + username + "' ...");
        try {
            CustomUser user = em
                    .createQuery("SELECT user FROM CustomUser user WHERE user.username = :username", CustomUser.class)
                    .setParameter("username", username)
                    .getSingleResult();
            if (user == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(),
                                "CustomUser with username " + username + " not found"))
                        .build();
            }
            return Response.ok(user).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * @return list of all users.
     */
    @GET
    public Response listAll() {
        LOG.info("GET: listing all users ...");
        try {
            return Response.ok(userResource.listAll()).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * @param user to create.
     * @return created user.
     */
    @POST
    @Transactional
    public Response create(final CustomUser user) {
        LOG.info("POST: creating user '" + user.username + "' ...");
        try {
            userResource.persist(user);
            return Response.ok(user).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * @param id      of user to update.
     * @param updates to apply.
     * @return updated user.
     */
    @PATCH
    @Path("/{id}")
    @Transactional
    public Response patch(@PathParam("id") final Long id, final Map<String, Object> updates) {
        LOG.info("PATCH: patching user with id '" + id + "' ...");
        try {
            CustomUser user = userResource.findById(id);
            if (user == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(),
                                "CustomUser with id " + id + " not found"))
                        .build();
            }
            CustomUser updatedUser = userResource.patch(user, updates);
            return Response.ok(updatedUser).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }

    /**
     * @param id of user to delete.
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") final Long id) {
        LOG.info("DELETE: deleting user with id '" + id + "' ...");
        try {
            CustomUser user = userResource.findById(id);
            if (user == null) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(),
                                "CustomUser with id " + id + " not found"))
                        .build();
            }
            user.delete();
            return Response.ok().build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                            "Unexpected error: " + e.getMessage()))
                    .build();
        }
    }
}
