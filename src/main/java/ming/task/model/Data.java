// Author: Ming Jin
// Contact: jinming0106g@gmail.com
// Created Date: 21th Aug, 2021

package ming.task.model;

public class Data {
    private Integer id;
    private String data;
    private String createdAt;
    private String updatedAt;

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return this.id;
    }

    public void setData(String data) {
        this.data = data;
    }
    public String getData() {
        return this.data;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getUpdatedAt() {
        return this.updatedAt;
    }

}
