package com.maxzuo.printtemplate.service;

import com.maxzuo.printtemplate.api.IScOperationPrinterKitchenDocumentTypeRulesService;
import com.maxzuo.printtemplate.dao.ScOperationPrinterKitchenDocumentTypeRulesMapper;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenDocumentTypeRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出票口票据类型相关Service实现类
 * Created by zfh on 2019/01/09
 */
@Service("scOperationPrinterKitchenDocumentTypeRulesService")
public class ScOperationPrinterKitchenDocumentTypeRulesServiceImpl implements IScOperationPrinterKitchenDocumentTypeRulesService {

    @Autowired
    private ScOperationPrinterKitchenDocumentTypeRulesMapper scOperationPrinterKitchenDocumentTypeRulesMapper;

    @Override
    public Integer save(ScOperationPrinterKitchenDocumentTypeRules record) {
        scOperationPrinterKitchenDocumentTypeRulesMapper.insert(record);
        return record.getId();
    }

    @Override
    public Integer saveMultipleRecord(List<ScOperationPrinterKitchenDocumentTypeRules> recordList) {
        return scOperationPrinterKitchenDocumentTypeRulesMapper.insertMultipleRecord(recordList);
    }

    @Override
    public Integer removeMultipleRecordByPrinterKitchenId(Integer printerKitchenId) {
        return scOperationPrinterKitchenDocumentTypeRulesMapper.updateMultipleRecordByPrinterKitchenId(printerKitchenId);
    }

    @Override
    public List<ScOperationPrinterKitchenDocumentTypeRules> listPrinterKitchenDocumentTypeRulesByPrinterKitchenId(Integer printerKitchenId) {
        return scOperationPrinterKitchenDocumentTypeRulesMapper.selectPrinterKitchenDocumentTypeRulesListByPrinterKitchenId(printerKitchenId);
    }

    @Override
    public ScOperationPrinterKitchenDocumentTypeRules selectByPrimaryKey(Integer id) {
        return scOperationPrinterKitchenDocumentTypeRulesMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateByPrimaryKeySelective(ScOperationPrinterKitchenDocumentTypeRules record) {
        return scOperationPrinterKitchenDocumentTypeRulesMapper.updateByPrimaryKeySelective(record);
    }
}
