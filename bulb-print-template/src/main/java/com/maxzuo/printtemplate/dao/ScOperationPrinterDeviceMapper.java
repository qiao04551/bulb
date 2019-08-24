package com.maxzuo.printtemplate.dao;

import com.maxzuo.printtemplate.model.ScOperationPrinterDevice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 打印机相关的Mapper
 * Created by zfh on 2019/1/9
 */
@Mapper
public interface ScOperationPrinterDeviceMapper {

    /**
     * 新增记录
     * @param record {@link ScOperationPrinterDevice}
     * @return 自增主键
     */
    Integer insert(ScOperationPrinterDevice record);

    /**
     * 根据主键查询记录
     * @param id 主键
     * @return {@link ScOperationPrinterDevice}
     */
    ScOperationPrinterDevice selectByPrimaryKey(Integer id);

    /**
     * 查询店铺下所有的打印机（未删除）
     * @param shopId 店铺id
     * @return list
     */
    List<ScOperationPrinterDevice> selectPrinterDeviceListByShopId(Integer shopId);

    /**
     * 更新记录
     * @param record {@link ScOperationPrinterDevice}
     * @return 受影响的条数
     */
    Integer updateByPrimaryKeySelective(ScOperationPrinterDevice record);
}