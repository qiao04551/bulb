package com.maxzuo.printtemplate.api;

import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenDocumentTypeRules;
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
public class IScOperationPrinterKitchenDocumentTypeRulesServiceTest {

    @Autowired
    private IScOperationPrinterKitchenDocumentTypeRulesService scOperationPrinterKitchenDocumentTypeRulesService;

    @Transactional
    @Test
    public void save() {
        ScOperationPrinterKitchenDocumentTypeRules documentTypeRules = new ScOperationPrinterKitchenDocumentTypeRules();
        documentTypeRules.setPrinterKitchenId(1);
        documentTypeRules.setDocumentTypeId(1);
        documentTypeRules.setNumber(1);
        documentTypeRules.setPrinterType(1);
        documentTypeRules.setDelete(0);
        Integer affected = scOperationPrinterKitchenDocumentTypeRulesService.save(documentTypeRules);
        Assert.assertTrue(affected > 0);
    }

    @Transactional
    @Test
    public void saveMultipleRecord() {
        List<ScOperationPrinterKitchenDocumentTypeRules> documentTypeRulesList = new ArrayList<>(10);
        for (int i = 0; i < 5; i++) {
            ScOperationPrinterKitchenDocumentTypeRules documentTypeRules = new ScOperationPrinterKitchenDocumentTypeRules();
            documentTypeRules.setPrinterKitchenId(i);
            documentTypeRules.setDocumentTypeId(i);
            documentTypeRules.setNumber(1);
            documentTypeRules.setPrinterType(i);
            documentTypeRules.setDelete(0);
            documentTypeRulesList.add(documentTypeRules);
        }
        Integer affected = scOperationPrinterKitchenDocumentTypeRulesService.saveMultipleRecord(documentTypeRulesList);
        Assert.assertTrue(affected > 5);
    }

    @Test
    public void listPrinterKitchenDocumentTypeRulesByPrinterKitchenId() {
        List<ScOperationPrinterKitchenDocumentTypeRules> documentTypeRulesList = scOperationPrinterKitchenDocumentTypeRulesService.listPrinterKitchenDocumentTypeRulesByPrinterKitchenId(1);
        Assert.assertEquals(documentTypeRulesList.size(), 2);
    }

    @Test
    public void selectByPrimaryKey() {
        ScOperationPrinterKitchenDocumentTypeRules documentTypeRules = scOperationPrinterKitchenDocumentTypeRulesService.selectByPrimaryKey(2);
        Assert.assertEquals(documentTypeRules.getId().longValue(), 2);
    }

    @Transactional
    @Test
    public void updateByPrimaryKeySelective() {
        ScOperationPrinterKitchenDocumentTypeRules documentTypeRules = scOperationPrinterKitchenDocumentTypeRulesService.selectByPrimaryKey(2);
        documentTypeRules.setDocumentTypeId(2);
        Integer integer = scOperationPrinterKitchenDocumentTypeRulesService.updateByPrimaryKeySelective(documentTypeRules);
        Assert.assertTrue(integer > 0);
    }

    @Transactional
    @Test
    public void removeMultipleRecordByPrinterKitchenId() {
        Integer integer = scOperationPrinterKitchenDocumentTypeRulesService.removeMultipleRecordByPrinterKitchenId(1);
        Assert.assertEquals(integer.longValue(), 2);
    }
}