package com.maxzuo.printtemplate.vo;

/**
 * 出票口视图对象
 * Created by zfh on 2019/01/09
 */
public class PrinterKitchenVO {

    /** 主键 */
    private Integer id;

    /** 出票口名称 */
    private Integer name;

    /** 打印机id */
    private Integer printerDeviceId;

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

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Integer getPrinterDeviceId() {
        return printerDeviceId;
    }

    public void setPrinterDeviceId(Integer printerDeviceId) {
        this.printerDeviceId = printerDeviceId;
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
        return "PrinterKitchenVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", printerDeviceId=" + printerDeviceId +
                ", creatorName='" + creatorName + '\'' +
                ", updatorName='" + updatorName + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
