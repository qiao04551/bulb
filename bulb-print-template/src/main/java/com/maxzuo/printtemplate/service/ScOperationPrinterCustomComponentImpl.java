package com.maxzuo.printtemplate.service;

import com.maxzuo.printtemplate.api.IScOperationPrinterCustomComponentService;
import com.maxzuo.printtemplate.dao.ScOperationPrinterCustomComponentMapper;
import com.maxzuo.printtemplate.model.ScOperationPrinterCustomComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 打印模板-自定义组件相关Service
 * Created by zfh on 2019/01/03
 */
@Service("scOperationPrinterCustomComponentService")
public class ScOperationPrinterCustomComponentImpl implements IScOperationPrinterCustomComponentService {

    @Autowired
    private ScOperationPrinterCustomComponentMapper scOperationPrinterCustomComponentMapper;

    @Override
    public Integer saveMultiplePrinterCustomComponent(List<ScOperationPrinterCustomComponent> shopPrinterCustomComponentList) {
        return scOperationPrinterCustomComponentMapper.insertMultiplePrinterCustomComponent(shopPrinterCustomComponentList);
    }

    @Override
    public Integer removeMultiplePrinterCustomComponentByTemplateId(Integer templateId) {
        return scOperationPrinterCustomComponentMapper.updateMultiplePrinterCustomComponentByTemplateId(templateId);
    }

    @Override
    public List<ScOperationPrinterCustomComponent> listPrinterTemplateCustomComponentByTemplateId(Integer documentTemplateId) {
        return scOperationPrinterCustomComponentMapper.selectPrinterTemplateCustomComponentByTemplateId(documentTemplateId);
    }
}
