package com.maxzuo.printtemplate.api;

import com.maxzuo.printtemplate.model.ScOperationPrinterDocumentType;
import com.maxzuo.printtemplate.service.ScOperationPrinterDocumentTypeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IScOperationPrinterDocumentTypeServiceTest {

    @Autowired
    private ScOperationPrinterDocumentTypeServiceImpl scOperationPrinterDocumentTypeService;

    @Test
    public void getPrinterDocumentTypeByPrimaryId() {

    }

    @Test
    public void listPrinterDocumentType() {
        List<ScOperationPrinterDocumentType> typeList = scOperationPrinterDocumentTypeService.listPrinterDocumentType();
        Assert.assertEquals(typeList.size(), 6);
    }

    @Test
    public void updatePrinterDocumentType() {
    }
}