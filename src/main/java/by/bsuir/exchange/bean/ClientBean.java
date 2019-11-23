package by.bsuir.exchange.bean;

public class ClientBean implements Markable{
    private long id;
    private String name;
    private String surname;
    private long userId;

    public ClientBean() {
    }

    public ClientBean(long id, String name, String surname, long userId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
