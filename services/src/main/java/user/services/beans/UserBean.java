package user.services.beans;

import user.lib.User;
import user.models.converters.UserConverter;
import user.models.entities.UserEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class UserBean {

    private Logger log = Logger.getLogger(UserBean.class.getName());

    @Inject
    private EntityManager em;

    public List<User> getAllUsers() {
        TypedQuery<UserEntity> query = em.createNamedQuery(
                "UserEntity.getAll", UserEntity.class);

        List<UserEntity> resultList = query.getResultList();

        return resultList.stream().map(UserConverter::toDto).collect(Collectors.toList());

    }

    public User getUser(Integer id){
        UserEntity UserEn = em.find(UserEntity.class, id);

        if (UserEn == null){
            throw new NotFoundException();
        }

        User u = UserConverter.toDto(UserEn);

        return u;

    }

    public User createUser(User user){

        UserEntity UserEn = UserConverter.toEntity(user);

        try{
            beginTx();
            em.persist(UserEn);
            commitTx();
        }
        catch (Exception e){
            rollbackTx();
        }

        if (UserEn.getUserId()==null){
            throw new RuntimeException("Entity user was not persisted");
        }

        return UserConverter.toDto(UserEn);

    }

    public User updateUser(int id, User user) {

        UserEntity UserEn_old = em.find(UserEntity.class, id);

        if (UserEn_old == null) {
            return null;
        }

        UserEntity UserEn_new = UserConverter.toEntity(user);

        try{
            beginTx();
            UserEn_new.setUserId(UserEn_old.getUserId());
            UserEn_new = em.merge(UserEn_new);
            commitTx();
        }
        catch (Exception e){
            rollbackTx();
        }

        return UserConverter.toDto(UserEn_new);
    }



    private void beginTx(){
        if(!em.getTransaction().isActive()){
            em.getTransaction().begin();
        }
    }

    private void commitTx(){
        if (em.getTransaction().isActive()){
            em.getTransaction().commit();
        }
    }

    private void rollbackTx(){
        if(em.getTransaction().isActive()){
            em.getTransaction().rollback();
        }
    }

}
