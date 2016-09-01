package br.com.rotaverde.rotaverde.model;

/**
 * Created by iagobelo on 20/08/2016.
 */
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String score;

    public User() {

    }

    public User(String id, String name, String email, String password, String score) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    public String getUserCode() {
        return id;
    }

    public void setUserCode(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
