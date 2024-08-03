package messager.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;


    private String password;


    private String role;


    @Lob
    private byte[] image;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        role = "ROLE_USER";
    }

}
