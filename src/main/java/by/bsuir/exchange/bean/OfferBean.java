package by.bsuir.exchange.bean;

public class OfferBean {
    private long id;
    private String transport;
    private double price;
    private long courierId;

    public OfferBean() {
    }

    public OfferBean(long id, String transport, double price, long courierId) {
        this.id = id;
        this.transport = transport;
        this.price = price;
        this.courierId = courierId;
    }

    public long getCourierId() {
        return courierId;
    }

    public void setCourierId(long courierId) {
        this.courierId = courierId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
