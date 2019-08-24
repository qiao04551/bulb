package com.maxzuo.printtemplate.service;

import com.maxzuo.printtemplate.api.IScOperationPrinterKitchenTableRulesService;
import com.maxzuo.printtemplate.dao.ScOperationPrinterKitchenTableRulesMapper;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenTableRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出票口配置桌台区域相关Service实现类
 * Created by zfh on 2019/01/09
 */
@Service("scOperationPrinterKitchenTableRulesService")
public class ScOperationPrinterKitchenTableRulesServiceImpl implements IScOperationPrinterKitchenTableRulesService {

    @Autowired
    private ScOperationPrinterKitchenTableRulesMapper scOperationPrinterKitchenTableRulesMapper;

    @Override
    public Integer save(ScOperationPrinterKitchenTableRules record) {
        scOperationPrinterKitchenTableRulesMapper.insert(record);
        return record.getId();
    }

    @Override
    public Integer saveMultipleRecord(List<ScOperationPrinterKitchenTableRules> recordList) {
        return scOperationPrinterKitchenTableRulesMapper.insertMultipleRecord(recordList);
    }

    @Override
    public Integer removeMultipleRecordByPrinterKitchenId(Integer printerKitchenId) {
        return scOperationPrinterKitchenTableRulesMapper.updateMultipleRecordByPrinterKitchenId(printerKitchenId);
    }

    @Override
    public List<ScOperationPrinterKitchenTableRules> listPrinterKitchenTableRulesByPrinterKitchenId(Integer printerKitchenId) {
        return scOperationPrinterKitchenTableRulesMapper.selectPrinterKitchenTableRulesListByPrinterKitchenId(printerKitchenId);
    }

    @Override
    public ScOperationPrinterKitchenTableRules getByPrimaryKey(Integer id) {
        return scOperationPrinterKitchenTableRulesMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateByPrimaryKeySelective(ScOperationPrinterKitchenTableRules record) {
        return scOperationPrinterKitchenTableRulesMapper.updateByPrimaryKeySelective(record);
    }
}
