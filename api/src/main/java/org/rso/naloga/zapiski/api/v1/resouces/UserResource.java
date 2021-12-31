package org.rso.naloga.zapiski.api.v1.resouces;

import user.lib.Password;
import user.lib.User;
import user.services.beans.UserBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private Logger log = Logger.getLogger(UserResource.class.getName());

    @Inject
    private UserBean userBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getUsers(){
        List<User> users = userBean.getAllUsers();

        return Response.status(Response.Status.OK).entity(users).build();
    }

    @GET
    @Path("{userId}")
    public Response getUserById(@PathParam("userId") int userId){

        User user = userBean.getUser(userId);

        if (user == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).entity(user).build();
    }

    @POST
    public Response createUser(User user){

        if (user.getUserFirstName() == null || user.getUserSurname() == null ||
                user.getUsername() == null || user.getPassword() == null) {

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        user = userBean.createUser(user);

        return Response.status(Response.Status.OK).entity(user).build();
    }

    @PUT
    @Path("{userId")
    public Response changePassword(@PathParam("userId") int userId, Password passwordInput){

        User user = userBean.getUser(userId);

        if (user == null || user.getPassword() != Hash(passwordInput.getOld())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        user.setPassword(passwordInput.getNew());

        user = userBean.updateUser(userId, user);

        if (user == null || user.getPassword() != Hash(passwordInput.getOld())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.status(Response.Status.OK).build();
    }


    private String Hash(String value) {
        return value;
    }

}
