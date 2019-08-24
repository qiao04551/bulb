package com.maxzuo.printtemplate.api;

import com.maxzuo.printtemplate.model.ScOperationPrinterTemplateModule;
import com.maxzuo.printtemplate.service.ScOperationPrinterTemplateModuleServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IScOperationPrinterTemplateModuleServiceTest {

    @Autowired
    private ScOperationPrinterTemplateModuleServiceImpl scOperationPrinterTemplateModuleService;

    @Test
    public void listPrinterTemplateModuleByDocumentId() {
        List<ScOperationPrinterTemplateModule> moduleList = scOperationPrinterTemplateModuleService.listPrinterTemplateModuleByDocumentId(1);
        Assert.assertEquals(moduleList.size(), 8);
    }
}