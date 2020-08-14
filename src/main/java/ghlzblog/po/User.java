package ghlzblog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue
    private Long uid;
    private String umessage;
    private String username;
    private String password;
    private String email;
    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginTime;

   
    public User() {
    }

    public Long getId() {
        return uid;
    }

    public void setId(Long id) {
        this.uid = id;
    }

  
    public Date getloginTime() {
        return loginTime;
    }

    public void setloginTime(Date loginTime) {
        this.loginTime = loginTime;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + loginTime +
                '}';
    }

	public String getUmessage() {
		return umessage;
	}

	public void setUmessage(String umessage) {
		this.umessage = umessage;
	}


}
