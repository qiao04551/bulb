package com.maxzuo.printtemplate.service;

import com.maxzuo.printtemplate.api.IScOperationPrinterTemplateDocumentService;
import com.maxzuo.printtemplate.dao.ScOperationPrinterTemplateDocumentMapper;
import com.maxzuo.printtemplate.model.ScOperationPrinterTemplateDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 打印模板-文档相关实现类
 * Created by zfh on 2018/12/12
 */
@Service("scOperationPrinterTemplateDocumentService")
public class ScOperationPrinterTemplateDocumentServiceImpl implements IScOperationPrinterTemplateDocumentService {

    @Autowired
    private ScOperationPrinterTemplateDocumentMapper scOperationPrinterTemplateDocumentMapper;

    @Override
    public Integer saveShopPrinterTemplateDocument(ScOperationPrinterTemplateDocument scShopPrinterTemplateDocument) {
        scOperationPrinterTemplateDocumentMapper.insertShopPrinterTemplateDocument(scShopPrinterTemplateDocument);
        return scShopPrinterTemplateDocument.getId();
    }

    @Override
    public ScOperationPrinterTemplateDocument getShopPrinterTemplateDocumentByPrimaryId(Integer id) {
        return scOperationPrinterTemplateDocumentMapper.selectShopPrinterTemplateDocumentByPrimaryId(id);
    }

    @Override
    public ScOperationPrinterTemplateDocument getShopPrinterTemplateDocumentByName(String name) {
        return scOperationPrinterTemplateDocumentMapper.selectShopPrinterTemplateDocumentByName(name);
    }

    @Override
    public Integer updateShopPrinterTemplateDocumentByPrimaryId(ScOperationPrinterTemplateDocument scShopPrinterTemplateDocument) {
        return scOperationPrinterTemplateDocumentMapper.updateShopPrinterTemplateDocumentByPrimaryId(scShopPrinterTemplateDocument);
    }

    @Override
    public Integer enableShopPrinterTemplate(Integer shopId, Integer documentType, Integer templateId) {
        // 禁用已启用的模板
        scOperationPrinterTemplateDocumentMapper.disableShopPrinterTemplateDocumentStatusByShopIdAndDocumentType(shopId, documentType);
        return scOperationPrinterTemplateDocumentMapper.updateShopPrinterTemplateDocumentStatusByShopIdAndDocumentType(templateId, 1);
    }

    @Override
    public Integer removeShopPrinterTemplateDocumentByPrimaryId(Integer id) {
        return scOperationPrinterTemplateDocumentMapper.updateShopPrinterTemplateDocumentStatusByPrimaryId(id);
    }

    @Override
    public List<ScOperationPrinterTemplateDocument> listPrinterTemplateDocumentByShopIdAndDocumentType(Integer shopId, Integer documentType) {
        return scOperationPrinterTemplateDocumentMapper.selectPrinterTemplateDocumentByShopIdAndDocumentType(shopId, documentType);
    }
}
