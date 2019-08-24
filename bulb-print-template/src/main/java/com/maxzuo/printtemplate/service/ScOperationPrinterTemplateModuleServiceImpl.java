package com.maxzuo.printtemplate.service;

import com.maxzuo.printtemplate.api.IScOperationPrinterTemplateModuleService;
import com.maxzuo.printtemplate.dao.ScOperationPrinterTemplateModuleMapper;
import com.maxzuo.printtemplate.model.ScOperationPrinterTemplateModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 打印模板-模块接口实现类
 * Created by zfh on 2018/12/12
 */
@Service("scOperationPrinterTemplateModuleService")
public class ScOperationPrinterTemplateModuleServiceImpl implements IScOperationPrinterTemplateModuleService {

    @Autowired
    private ScOperationPrinterTemplateModuleMapper scOperationPrinterTemplateModuleMapper;

    @Override
    public List<ScOperationPrinterTemplateModule> listPrinterTemplateModuleByDocumentId(Integer documentType) {
        return scOperationPrinterTemplateModuleMapper.selectPrinterTemplateModuleByDocumentId(documentType);
    }
}
