package indianpoker.domain.user;

import support.domain.AbstractEntity;

import javax.persistence.Column;
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

    public User() {
    }

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWinCnt(int winCnt) {
        this.winCnt = winCnt;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public int getWinCnt() {
        return winCnt;
    }


    public boolean matchPassword(String otherPassword) {
        return this.password.equals(otherPassword);
    }
}
