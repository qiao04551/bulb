package com.maxzuo.graphql.vo;

/**
 * 浏览的名片 视图对象
 * <p>
 * Created by zfh on 2019/08/19
 */
public class BrowsingHistoryCardVO {

    /**
     * 名片ID
     */
    private Integer cradId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 姓名
     */
    private String name;

    /**
     * 姓名全拼
     */
    private String namePinyin;

    /**
     * 职位
     */
    private String position;

    /**
     * 公司
     */
    private String company;

    /**
     * 浏览时间，格式：yyyy-MM-dd HH:mm:ss
     */
    private String viewTime;

    public Integer getCradId() {
        return cradId;
    }

    public void setCradId(Integer cradId) {
        this.cradId = cradId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getViewTime() {
        return viewTime;
    }

    public void setViewTime(String viewTime) {
        this.viewTime = viewTime;
    }

    @Override
    public String toString() {
        return "BrowsingHistoryCardVO{" +
                "cradId=" + cradId +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", namePinyin='" + namePinyin + '\'' +
                ", position='" + position + '\'' +
                ", company='" + company + '\'' +
                ", viewTime='" + viewTime + '\'' +
                '}';
    }
}
