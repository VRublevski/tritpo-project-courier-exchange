package by.bsuir.exchange.bean;

public class CourierBean implements Markable{
    private long id;
    private String name;
    private String surname;
    private String transport;
    private long userId;

    public CourierBean() {
    }

    public CourierBean(long id, String name, String surname, String transport, long userId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.transport = transport;
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

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
