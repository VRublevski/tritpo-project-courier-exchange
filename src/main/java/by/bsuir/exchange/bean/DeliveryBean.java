package by.bsuir.exchange.bean;

public class DeliveryBean {
    private long id;
    private long clientId;
    private boolean clientFinished;
    private long courierId;
    private boolean courierFinished;


    public DeliveryBean() {
    }

    public DeliveryBean(long clientId, boolean clientFinished, long courierId, boolean courierFinished) {
        this.clientId = clientId;
        this.clientFinished = clientFinished;
        this.courierId = courierId;
        this.courierFinished = courierFinished;
    }

    public DeliveryBean(long id, long clientId, boolean clientFinished, long courierId, boolean courierFinished) {
        this.id = id;
        this.clientId = clientId;
        this.clientFinished = clientFinished;
        this.courierId = courierId;
        this.courierFinished = courierFinished;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public boolean getClientFinished() {
        return clientFinished;
    }

    public void setClientFinished(boolean clientFinished) {
        this.clientFinished = clientFinished;
    }

    public long getCourierId() {
        return courierId;
    }

    public void setCourierId(long courierId) {
        this.courierId = courierId;
    }

    public boolean getCourierFinished() {
        return courierFinished;
    }

    public void setCourierFinished(boolean courierFinished) {
        this.courierFinished = courierFinished;
    }
}
