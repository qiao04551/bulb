package com.maxzuo.printtemplate.dao;

import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenTableRules;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出票口配置桌台区域相关Mapper
 */
@Mapper
public interface ScOperationPrinterKitchenTableRulesMapper {

    /**
     * 新增记录
     * @param record {@link ScOperationPrinterKitchenTableRules}
     * @return 自增主键
     */
    Integer insert(ScOperationPrinterKitchenTableRules record);

    /**
     * 新增多条记录
     * @param recordList list
     * @return 受影响的条数
     */
    Integer insertMultipleRecord (List<ScOperationPrinterKitchenTableRules> recordList);

    /**
     * 根据出票口 删除多条记录（软删除）
     * @param printerKitchenId 出票口ID
     * @return 受影响的条数
     */
    Integer updateMultipleRecordByPrinterKitchenId (Integer printerKitchenId);

    /**
     * 查询出票口配置的桌台区域
     * @param printerKitchenId 出票口id
     * @return list
     */
    List<ScOperationPrinterKitchenTableRules> selectPrinterKitchenTableRulesListByPrinterKitchenId(Integer printerKitchenId);

    /**
     * 根据主键查询记录
     * @param id 主键
     * @return {@link ScOperationPrinterKitchenTableRules}
     */
    ScOperationPrinterKitchenTableRules selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新记录
     * @param record {@link ScOperationPrinterKitchenTableRules}
     * @return 受影响的条数
     */
    Integer updateByPrimaryKeySelective(ScOperationPrinterKitchenTableRules record);
}