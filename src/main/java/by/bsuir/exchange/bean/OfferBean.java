package by.bsuir.exchange.bean;

public class OfferBean {
    private long id;
    private String transport;
    private double price;
    private long courierId;
    private boolean archival;

    public OfferBean() {
    }


    public OfferBean(long id, String transport, double price, long courierId, boolean archival) {
        this.id = id;
        this.transport = transport;
        this.price = price;
        this.courierId = courierId;
        this.archival = archival;
    }

    public boolean getArchival() {
        return archival;
    }

    public void setArchival(boolean archival) {
        this.archival = archival;
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
