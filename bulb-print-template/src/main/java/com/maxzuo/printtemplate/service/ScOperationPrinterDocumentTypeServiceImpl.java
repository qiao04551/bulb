package com.maxzuo.printtemplate.service;

import com.maxzuo.printtemplate.api.IScOperationPrinterDocumentTypeService;
import com.maxzuo.printtemplate.dao.ScOperationPrinterDocumentTypeMapper;
import com.maxzuo.printtemplate.model.ScOperationPrinterDocumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 打印模板实现类
 * Created by zfh on 2018/12/12
 */
@Service("scOperationPrinterDocumentTypeService")
public class ScOperationPrinterDocumentTypeServiceImpl implements IScOperationPrinterDocumentTypeService {

    @Autowired
    private ScOperationPrinterDocumentTypeMapper scOperationPrinterDocumentTypeMapper;

    @Override
    public ScOperationPrinterDocumentType getPrinterDocumentTypeByPrimaryId(Integer id) {
        return scOperationPrinterDocumentTypeMapper.selectPrinterDocumentTypeByPrimaryId(id);
    }

    @Override
    public List<ScOperationPrinterDocumentType> listPrinterDocumentType() {
        return scOperationPrinterDocumentTypeMapper.selectPrinterDocumentType();
    }

    @Override
    public Integer updatePrinterDocumentType(ScOperationPrinterDocumentType scShopPrinterDocumentType) {
        return scOperationPrinterDocumentTypeMapper.updatePrinterDocumentType(scShopPrinterDocumentType);
    }
}
