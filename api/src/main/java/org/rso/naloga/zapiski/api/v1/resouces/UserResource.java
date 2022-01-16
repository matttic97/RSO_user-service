package org.rso.naloga.zapiski.api.v1.resouces;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
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
@CrossOrigin(supportedMethods = "GET, POST, PUT, DELETE, OPTIONS")
public class UserResource {

    private Logger log = Logger.getLogger(UserResource.class.getName());

    @Inject
    private UserBean userBean;

    @Context
    protected UriInfo uriInfo;


    @Operation(description = "Get all users.", summary = "Get users.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "All users",
                    content = @Content(schema = @Schema(implementation = User.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Users")}
            )})
    @GET
    public Response getUsers(){
        List<User> users = userBean.getAllUsers();

        return Response.status(Response.Status.OK).entity(users).build();
    }


    @Operation(description = "Get specified user.", summary = "Get user.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Specified user",
                    content = @Content(schema = @Schema(implementation = User.class, type = SchemaType.OBJECT)),
                    headers = {@Header(name = "X-Total-Count", description = "User")}
            )})
    @GET
    @Path("{userId}")
    public Response getUserById(@PathParam("userId") long userId){

        User user = userBean.getUser(userId);

        if (user == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(user.getUserId()==-1){
            return Response.status(500, "Fallback. User does not exist or circuit open.").build();
        }

        return Response.status(Response.Status.OK).entity(user).build();
    }


    @Operation(description = "Create new user", summary = "Create user.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "New user",
                    content = @Content(schema = @Schema(implementation = User.class, type = SchemaType.OBJECT)),
                    headers = {@Header(name = "X-Total-Count", description = "User")}
            )})
    @POST
    public Response createUser(User user){

        if (user.getUserFirstName() == null || user.getUserSurname() == null ||
                user.getUsername() == null || user.getPassword() == null) {

            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        user = userBean.createUser(user);

        return Response.status(Response.Status.OK).entity(user).build();
    }


    @Operation(description = "Update a users password.", summary = "Update users password.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Updated users password",
                    content = @Content(schema = @Schema(implementation = Password.class, type = SchemaType.OBJECT)),
                    headers = {@Header(name = "X-Total-Count", description = "User")}
            )})
    @PUT
    @Path("{userId}")
    public Response changePassword(@PathParam("userId") long userId, Password passwordInput){

        User user = userBean.getUser(userId);

        if (user == null || !user.getPassword().equals(Hash(passwordInput.getOldPassword()))){
            return Response.status(Response.Status.BAD_REQUEST).build();

        }


        user.setPassword(passwordInput.getNewPassword());

        user = userBean.updateUser(userId, user);

        return Response.status(Response.Status.OK).entity(user).build();
    }

    @Operation(description = "Delete a user with given id.", summary = "Delete a user.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Delete a user with given id.",
                    content = @Content(schema = @Schema(implementation = User.class, type = SchemaType.OBJECT)),
                    headers = {@Header(name = "X-Total-Count", description = "User")}
            )})
    @DELETE
    @Path("{userId}")
    public Response deleteUser(@PathParam("userId") long userId){

        boolean deleted = userBean.deleteUser(userId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }





    private String Hash(String value) {
        return value;
    }

}
