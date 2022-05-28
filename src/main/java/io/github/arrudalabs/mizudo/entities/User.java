package io.github.arrudalabs.mizudo.entities;

import io.github.arrudalabs.mizudo.services.GeneratedPassword;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {


    public static User createUser(String username,
                                  GeneratedPassword newPassword) {
        User user = new User();
        user.username = username;
        user.salt = newPassword.salt();
        user.hash = newPassword.hash();
        return user;
    }

    @Deprecated
    public static void deleteAllUsers() {
        User.streamAll()
                .forEach(PanacheEntityBase::delete);
    }

    @Id
    public String username;

    @Lob
    @Basic
    public byte[] salt;

    @Lob
    @Basic
    public byte[] hash;

    @ElementCollection
    @CollectionTable(
            name = "users_roles",
            joinColumns = {
                    @JoinColumn(name = "username")
            }
    )
    @Column(name = "role")
    public Set<String> roles = new HashSet<>();

    public static User findByUsername(String username) {
        return User.findById(username);
    }
}
