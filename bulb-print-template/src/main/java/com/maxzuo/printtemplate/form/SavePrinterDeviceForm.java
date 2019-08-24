package com.maxzuo.printtemplate.form;

import com.maxzuo.printtemplate.model.ScOperationPrinterDevice;
import com.maxzuo.printtemplate.vo.Result;
import org.apache.commons.lang3.StringUtils;

/**
 * 新增 打印机 请求
 * Created by zfh on 2019/01/09
 */
public class SavePrinterDeviceForm {

    /** 打印机名称 */
    private String deviceName;

    /** 打印机类型：1-服务器 2-网络打印机 */
    private Integer printerDeviceType;

    /** ip地址 */
    private String address;

    /** 店铺id */
    private Integer shopId;

    /** 平台用户ID */
    private Integer operatorId;

    /**
     * 验证入参
     * @return {@link Result}
     */
    public Result validateParam () {
        Result result = new Result(Result.RESULT_SUCCESS);
        if (StringUtils.isBlank(deviceName)) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("打印机名称不能为空！");
        }
        if (printerDeviceType == null) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("打印机类型不能为空！");
        }
        if (StringUtils.isBlank(address)) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("ip地址不能为空！");
        }
        if (shopId == null) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("店铺ID不能为空！");
        }
        if (operatorId == null) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("平台用户ID不能为空！");
        }
        return result;
    }

    /**
     * 获取 打印机实体对象
     * @return {@link ScOperationPrinterDevice}
     */
    public ScOperationPrinterDevice getScShopPrinterDevice () {
        ScOperationPrinterDevice scShopPrinterDevice = new ScOperationPrinterDevice();
        scShopPrinterDevice.setDeviceName(deviceName);
        scShopPrinterDevice.setPrinterDeviceType(printerDeviceType);
        scShopPrinterDevice.setAddress(address);
        scShopPrinterDevice.setDelete(0);
        scShopPrinterDevice.setShopId(shopId);
        scShopPrinterDevice.setCreatorId(operatorId);
        return scShopPrinterDevice;
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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        return "SavePrinterDeviceForm{" +
                "deviceName='" + deviceName + '\'' +
                ", printerDeviceType=" + printerDeviceType +
                ", address='" + address + '\'' +
                ", shopId=" + shopId +
                ", operatorId=" + operatorId +
                '}';
    }
}
