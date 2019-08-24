package com.maxzuo.printtemplate.vo;

/**
 * 打印机详情VO
 * Created by zfh on 2019/01/09
 */
public class PrinterDeviceVO {

    /** 主键 */
    private Integer id;

    /** 名称 */
    private String deviceName;

    /** 打印机类型 */
    private Integer printerDeviceType;

    /** ip地址 */
    private String address;

    /** 创建人姓名 */
    private String creatorName;

    /** 更新人姓名 */
    private String updatorName;

    /** 添加时间 */
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getPrinterDeviceType() {
        return printerDeviceType;
    }

    public void setPrinterDeviceType(Integer printerDeviceType) {
        this.printerDeviceType = printerDeviceType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PrinterDeviceVO{" +
                "id=" + id +
                ", deviceName='" + deviceName + '\'' +
                ", printerDeviceType=" + printerDeviceType +
                ", address='" + address + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", updatorName='" + updatorName + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
