package com.maxzuo.printtemplate.service;

import com.maxzuo.printtemplate.api.IScOperationPrinterKitchenGoodsRulesService;
import com.maxzuo.printtemplate.dao.ScOperationPrinterKitchenGoodsRulesMapper;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenGoodsRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出票口配置打印菜品相关Service实现类
 * Created by zfh on 2019/01/09
 */
@Service("scOperationPrinterKitchenGoodsRulesService")
public class ScOperationPrinterKitchenGoodsRulesServiceImpl implements IScOperationPrinterKitchenGoodsRulesService {

    @Autowired
    private ScOperationPrinterKitchenGoodsRulesMapper scOperationPrinterKitchenGoodsRulesMapper;

    @Override
    public Integer save(ScOperationPrinterKitchenGoodsRules record) {
        scOperationPrinterKitchenGoodsRulesMapper.insert(record);
        return record.getId();
    }

    @Override
    public Integer saveMultipleRecord(List<ScOperationPrinterKitchenGoodsRules> recordList) {
        return scOperationPrinterKitchenGoodsRulesMapper.insertMultipleRecord(recordList);
    }

    @Override
    public Integer removeMultipleRecordByPrinterKitchenId(Integer printerKitchenId) {
        return scOperationPrinterKitchenGoodsRulesMapper.updateMultipleRecordByPrinterKitchenId(printerKitchenId);
    }

    @Override
    public List<ScOperationPrinterKitchenGoodsRules> listPrinterKitchenGoodsRulesByPrinterKitchenId(Integer printerKitchenId) {
        return scOperationPrinterKitchenGoodsRulesMapper.selectPrinterKitchenGoodsRulesListByPrinterKitchenId(printerKitchenId);
    }

    @Override
    public ScOperationPrinterKitchenGoodsRules getByPrimaryKey(Integer id) {
        return scOperationPrinterKitchenGoodsRulesMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateByPrimaryKeySelective(ScOperationPrinterKitchenGoodsRules record) {
        return scOperationPrinterKitchenGoodsRulesMapper.updateByPrimaryKeySelective(record);
    }
}
