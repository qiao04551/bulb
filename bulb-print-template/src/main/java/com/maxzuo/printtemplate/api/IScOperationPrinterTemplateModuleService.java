package com.maxzuo.printtemplate.api;

import com.maxzuo.printtemplate.model.ScOperationPrinterTemplateModule;

import java.util.List;

/**
 * 打印模板-模块相关接口
 * Created by zfh on 2018/12/12
 */
public interface IScOperationPrinterTemplateModuleService {

    /**
     * 通过文档类型查询包含的模块
     * @param documentType 文档类型主键
     * @return list
     */
    List<ScOperationPrinterTemplateModule> listPrinterTemplateModuleByDocumentId (Integer documentType);
}
