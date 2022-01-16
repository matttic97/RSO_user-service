package org.rso.naloga.zapiski.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import user.lib.User;
import user.services.beans.UserBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@GraphQLClass
@ApplicationScoped
public class UserQueries {

    @Inject
    private UserBean userBean;


    @GraphQLQuery
    public User getUser(@GraphQLArgument(name = "id") long id){
        return userBean.getUser(id);
    }

    @GraphQLQuery
    public PaginationWrapper<User> getAllUsers(@GraphQLArgument(name = "pagination") Pagination pagination,
                                            @GraphQLArgument(name = "sort") Sort sort,
                                            @GraphQLArgument(name = "filter") Filter filter){

        return GraphQLUtils.process(userBean.getAllUsers(), pagination, sort, filter);

    }

}
