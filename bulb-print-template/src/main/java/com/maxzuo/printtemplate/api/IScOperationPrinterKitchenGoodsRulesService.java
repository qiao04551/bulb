package com.maxzuo.printtemplate.api;

import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenGoodsRules;

import java.util.List;

/**
 * 出票口配置打印菜品相关Service
 * Created by zfh on 2019/01/09
 */
public interface IScOperationPrinterKitchenGoodsRulesService {

    /**
     * 新增记录
     * @param record {@link ScOperationPrinterKitchenGoodsRules}
     * @return 自增主键
     */
    Integer save(ScOperationPrinterKitchenGoodsRules record);

    /**
     * 新增多条记录
     * @param recordList list
     * @return 受影响的条数
     */
    Integer saveMultipleRecord(List<ScOperationPrinterKitchenGoodsRules> recordList);

    /**
     * 通过出票口ID 删除多条记录（软删除）
     * @param printerKitchenId 出票口ID
     * @return 受影响的条数
     */
    Integer removeMultipleRecordByPrinterKitchenId (Integer printerKitchenId);

    /**
     * 查询出票口 配置的打印菜品
     * @param printerKitchenId 出票口id
     * @return list
     */
    List<ScOperationPrinterKitchenGoodsRules> listPrinterKitchenGoodsRulesByPrinterKitchenId(Integer printerKitchenId);

    /**
     * 根据主键查记录
     * @param id 主键
     * @return {@link ScOperationPrinterKitchenGoodsRules}
     */
    ScOperationPrinterKitchenGoodsRules getByPrimaryKey(Integer id);

    /**
     * 根据主键更新记录
     * @param record {@link ScOperationPrinterKitchenGoodsRules}
     * @return 受影响的条数
     */
    Integer updateByPrimaryKeySelective(ScOperationPrinterKitchenGoodsRules record);
}
