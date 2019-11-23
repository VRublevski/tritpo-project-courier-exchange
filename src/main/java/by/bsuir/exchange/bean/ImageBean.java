package by.bsuir.exchange.bean;

public class ImageBean {
    private long id;
    private String role;
    private long roleId;
    private String fileName;

    public ImageBean(long id, long roleId, String role, String fileName) {
        this.id = id;
        this.roleId = roleId;
        this.role = role;
        this.fileName = fileName;
    }

    public ImageBean() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
