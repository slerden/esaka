package world.esaka.auth.model;

import world.esaka.auth.validation.annotation.IsCorrectPassword;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ESAKA_USER")
@IsCorrectPassword(groups = User.Update.class)
public class User implements Serializable {

    public interface Update{}

    public interface Create{}




    public User() {
    }

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long userId;

    @Column
    @NotNull(groups = Create.class)
    @Pattern(regexp = "^[A-Za-z\\d]*$",groups = Create.class, message = "{esaka.validation.pattern.latinchars.numbers}")
    @Size(min = 3, max = 16, groups = Create.class)
    private String username;

    @Column
    @NotNull(groups = {Create.class, Update.class})
    @Pattern(regexp = "^[A-Za-z\\d]*$",groups = Create.class, message = "{esaka.validation.pattern.latinchars.numbers}")
    @Size(min = 3, max = 64, groups = Create.class, message = "esaka.auth.user.password.length")
    private String password;

    @Column
    private Date createDate;

    @Column
    @Pattern(regexp = "^.+@.+\\..+$",groups = {Update.class, Create.class}, message = "{esaka.validation.pattern.email}")
    @Size(max = 254, groups = Create.class, message = "esaka.auth.user.email.length")
    private String email;

    @Column
    private Date updateDate;

    @Transient
    @Pattern(regexp = "^[A-Za-z\\d]*$",groups = Update.class, message = "{esaka.validation.pattern.latinchars.numbers}")
    @Size(min = 3, max = 64, groups = Update.class, message = "esaka.auth.user.password.length")
    private String newPassword;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Role role;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return userId;
    }


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
