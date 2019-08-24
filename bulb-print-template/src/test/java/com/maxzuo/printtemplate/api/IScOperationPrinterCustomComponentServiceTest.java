package com.maxzuo.printtemplate.api;

import com.alibaba.fastjson.JSON;
import com.maxzuo.printtemplate.model.ScOperationPrinterCustomComponent;
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
public class IScOperationPrinterCustomComponentServiceTest {

    @Autowired
    private IScOperationPrinterCustomComponentService scOperationPrinterCustomComponentService;

    @Transactional
    @Test
    public void saveMultiplePrinterCustomComponent() {
        List<ScOperationPrinterCustomComponent> customComponentList = new ArrayList(10);
        for (int i = 0; i < 3; i++) {
            ScOperationPrinterCustomComponent customComponent = new ScOperationPrinterCustomComponent();
            customComponent.setDocumentTemplateId(1);
            customComponent.setSystemComponentId(i);
            customComponent.setValueStyle("css");
            customComponentList.add(customComponent);
        }
        Integer count = scOperationPrinterCustomComponentService.saveMultiplePrinterCustomComponent(customComponentList);
        System.out.println("count：" + count);
    }

    @Test
    public void listPrinterTemplateCustomComponentByTemplateId() {
        List<ScOperationPrinterCustomComponent> scShopPrinterCustomComponents = scOperationPrinterCustomComponentService.listPrinterTemplateCustomComponentByTemplateId(1);
        System.out.println("json: " + JSON.toJSON(scShopPrinterCustomComponents));
        Assert.assertEquals(scShopPrinterCustomComponents.size(), 151);
    }
}