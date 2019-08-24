package com.maxzuo.printtemplate.service;

import com.maxzuo.printtemplate.api.IScOperationPrinterSystemComponentService;
import com.maxzuo.printtemplate.dao.ScOperationPrinterSystemComponentMapper;
import com.maxzuo.printtemplate.model.ScOperationPrinterSystemComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 打印模板-系统组件实现类
 * Created by zfh on 2018/12/27
 */
@Service("scOperationPrinterSystemComponentService")
public class ScOperationPrinterSystemComponentServiceImpl implements IScOperationPrinterSystemComponentService {

    @Autowired
    private ScOperationPrinterSystemComponentMapper scOperationPrinterSystemComponentMapper;

    @Override
    public Integer savePrinterSystemComponent(ScOperationPrinterSystemComponent systemComponent) {
        return scOperationPrinterSystemComponentMapper.insertPrinterSystemComponent(systemComponent);
    }

    @Override
    public List<ScOperationPrinterSystemComponent> listPrinterSystemComponentByTemplateType(Integer templateType) {
        return scOperationPrinterSystemComponentMapper.selectPrinterSystemComponentListByTemplateTypeId(templateType);
    }
}
