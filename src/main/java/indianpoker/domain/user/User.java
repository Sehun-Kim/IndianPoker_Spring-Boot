package indianpoker.domain.user;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class User extends AbstractEntity {

    @Size(min = 3, max = 20)
    @Column(unique = true, nullable = false)
    private String userId;

    @Size(min = 3, max = 20)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int winCnt = 0;

    @Embedded
    private Picture picture;

    public User() {
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setWinCnt(int winCnt) {
        this.winCnt = winCnt;
    }

    public int getWinCnt() {
        return winCnt;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Picture getPicture() {
        return picture;
    }

    public boolean matchPassword(String otherPassword) {
        return this.password.equals(otherPassword);
    }
}
