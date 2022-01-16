package user.services.beans;

import user.lib.User;
import user.models.converters.UserConverter;
import user.models.entities.UserEntity;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
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

    @Timeout(value = 3, unit = ChronoUnit.SECONDS)
    @CircuitBreaker(requestVolumeThreshold = 2)
    @Fallback(fallbackMethod = "getUserFallback")
    public User getUser(long id){

        if(id==2022){
            try{
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e){
                System.out.println("Time error:\n" + e);
            }
        }

        UserEntity ow = em.find(UserEntity.class, id);


        if (ow == null){
            throw new NotFoundException();
        }

        User u = UserConverter.toDto(ow);

        return u;

    }

    public User getUserFallback(long id){
        User u = new User();
        u.setUserId((long)-1);
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

    public User updateUser(long id, User user) {

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



    public boolean deleteUser(Long id){

        UserEntity user = em.find(UserEntity.class, id);

        if (user != null) {
            try {
                beginTx();
                em.remove(user);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
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
