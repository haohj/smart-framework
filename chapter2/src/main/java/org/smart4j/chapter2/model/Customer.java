package org.smart4j.chapter2.model;
/**
 * 客户
 * */
public class Customer {
    /**
     * ID
     * */
    private long id;
    /**
     * 客户名称
     * */
    private String name;
    /**
     * 联系人
     * */
    private String contact;
    /**
     * 电话号码
     * */
    private String telphone;
    /**
     * 邮箱地址
     * */
    private String email;
    /**
     * 备注
     * */
    private String remark;

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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
