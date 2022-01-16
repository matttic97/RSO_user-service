package user.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "_user")
@NamedQueries(value =
        {
                @NamedQuery(name = "UserEntity.getAll",
                        query = "SELECT im FROM UserEntity im")
        })
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "userFirstName")
    private String userFirstName;

    @Column(name = "userSurname")
    private String userSurname;

    //Getters
    public Long getUserId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    //Setters
    public void setUserId(Long userId) {
        this.id = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }
}
