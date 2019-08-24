package com.maxzuo.printtemplate.api;

import com.github.pagehelper.PageInfo;
import com.maxzuo.printtemplate.model.ScOperationPrinterDevice;

/**
 * 打印机相关Service
 * Created by zfh on 2019/01/09
 */
public interface IScOperationPrinterDeviceService {

    /**
     * 新增记录
     * @param record {@link ScOperationPrinterDevice}
     * @return 自增主键
     */
    Integer save(ScOperationPrinterDevice record);

    /**
     * 根据主键查询记录
     * @param id 主键
     * @return {@link ScOperationPrinterDevice}
     */
    ScOperationPrinterDevice getScShopPrinterDeviceByPrimaryKey(Integer id);

    /**
     * 查询店铺下所有的打印机（未删除）
     * @param shopId 店铺id
     * @param page   页码
     * @param rows   页条数
     * @return {@link PageInfo}
     */
    PageInfo<ScOperationPrinterDevice> listPrinterDeviceByShopId(Integer shopId, Integer page, Integer rows);

    /**
     * 更新记录
     * @param record {@link ScOperationPrinterDevice}
     * @return 受影响的条数
     */
    Integer updateByPrimaryKeySelective(ScOperationPrinterDevice record);
}
