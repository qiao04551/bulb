package com.maxzuo.printtemplate.api;

import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenDocumentTypeRules;

import java.util.List;

/**
 * 出票口票据类型相关Service
 * Created by zfh on 2019/01/09
 */
public interface IScOperationPrinterKitchenDocumentTypeRulesService {

    /**
     * 新增记录
     * @param record {@link ScOperationPrinterKitchenDocumentTypeRules}
     * @return 自增主键
     */
    Integer save(ScOperationPrinterKitchenDocumentTypeRules record);

    /**
     * 添加多条记录
     * @param documentTypeRulesList list
     * @return 受影响的条数
     */
    Integer saveMultipleRecord (List<ScOperationPrinterKitchenDocumentTypeRules> documentTypeRulesList);

    /**
     * 根据 出票口ID 删除多条记录（软删除）
     * @param printerKitchenId 出票口ID
     * @return 受影响的条数
     */
    Integer removeMultipleRecordByPrinterKitchenId (Integer printerKitchenId);

    /**
     * 查询 出票口 票据类型
     * @param printerKitchenId 出票口id
     * @return list
     */
    List<ScOperationPrinterKitchenDocumentTypeRules> listPrinterKitchenDocumentTypeRulesByPrinterKitchenId(Integer printerKitchenId);

    /**
     * 根据主键查记录
     * @param id 主键
     * @return {@link ScOperationPrinterKitchenDocumentTypeRules}
     */
    ScOperationPrinterKitchenDocumentTypeRules selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新记录
     * @param record {@link ScOperationPrinterKitchenDocumentTypeRules}
     * @return 受影响的条数
     */
    Integer updateByPrimaryKeySelective(ScOperationPrinterKitchenDocumentTypeRules record);
}
