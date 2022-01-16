package org.rso.naloga.zapiski.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import user.lib.User;
import user.services.beans.UserBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class UserMutations {

    @Inject
    private UserBean userBean;

    @GraphQLMutation
    public User addUser(@GraphQLArgument(name = "user") User user){
        userBean.createUser(user);
        return user;
    }

    @GraphQLMutation
    public DeleteResponse deleteUser(@GraphQLArgument(name = "id") long userId){
        return new DeleteResponse(userBean.deleteUser(userId));
    }

}
