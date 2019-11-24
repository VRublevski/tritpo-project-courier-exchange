package by.bsuir.exchange.bean;

public class CourierBean implements Markable{
    private long id;
    private String name;
    private String surname;
    private double balance;
    private long userId;


    public CourierBean() {
    }

    public CourierBean(long id, String name, String surname, double balance,  long userId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.balance = balance;
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
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
