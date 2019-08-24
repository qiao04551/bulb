package com.maxzuo.printtemplate.api;

import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenTableRules;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IScOperationPrinterKitchenTableRulesServiceTest {

    @Autowired
    private IScOperationPrinterKitchenTableRulesService scOperationPrinterKitchenTableRulesService;

    @Transactional
    @Test
    public void save() {
        ScOperationPrinterKitchenTableRules scShopPrinterKitchenTableRules = new ScOperationPrinterKitchenTableRules();
        scShopPrinterKitchenTableRules.setPrinterKitchenId(1);
        scShopPrinterKitchenTableRules.setTableId(1);
        scShopPrinterKitchenTableRules.setDelete(0);
        Integer affected = scOperationPrinterKitchenTableRulesService.save(scShopPrinterKitchenTableRules);
        Assert.assertTrue(affected > 0);
    }

    @Transactional
    @Test
    public void saveMultipleRecord() {
        List<ScOperationPrinterKitchenTableRules> kitchenTableRulesList = new ArrayList<>(10);
        for (int i = 0; i < 5; i++) {
            ScOperationPrinterKitchenTableRules scShopPrinterKitchenTableRules = new ScOperationPrinterKitchenTableRules();
            scShopPrinterKitchenTableRules.setPrinterKitchenId(i);
            scShopPrinterKitchenTableRules.setTableId(1);
            scShopPrinterKitchenTableRules.setDelete(0);
            kitchenTableRulesList.add(scShopPrinterKitchenTableRules);
        }
        Integer integer = scOperationPrinterKitchenTableRulesService.saveMultipleRecord(kitchenTableRulesList);
        Assert.assertEquals(integer.longValue(), 5);
    }

    @Test
    public void listPrinterKitchenTableRulesByPrinterKitchenId() {
        List<ScOperationPrinterKitchenTableRules> kitchenTableRulesList = scOperationPrinterKitchenTableRulesService.listPrinterKitchenTableRulesByPrinterKitchenId(1);
        Assert.assertEquals(kitchenTableRulesList.size(), 2);
    }

    @Test
    public void getByPrimaryKey() {
        ScOperationPrinterKitchenTableRules kitchenTableRules = scOperationPrinterKitchenTableRulesService.getByPrimaryKey(2);
        Assert.assertEquals(kitchenTableRules.getId().longValue(), 2);
    }

    @Transactional
    @Test
    public void updateByPrimaryKeySelective() {
        ScOperationPrinterKitchenTableRules kitchenTableRules = scOperationPrinterKitchenTableRulesService.getByPrimaryKey(2);
        kitchenTableRules.setPrinterKitchenId(3);
        Integer integer = scOperationPrinterKitchenTableRulesService.updateByPrimaryKeySelective(kitchenTableRules);
        Assert.assertTrue(integer > 0);
    }

    @Transactional
    @Test
    public void removeMultipleRecordByPrinterKitchenId() {
        Integer integer = scOperationPrinterKitchenTableRulesService.removeMultipleRecordByPrinterKitchenId(3);
        Assert.assertEquals(integer.longValue(), 2);
    }
}