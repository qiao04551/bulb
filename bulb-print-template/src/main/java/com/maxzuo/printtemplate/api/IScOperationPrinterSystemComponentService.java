package com.maxzuo.printtemplate.api;

import com.maxzuo.printtemplate.model.ScOperationPrinterSystemComponent;

import java.util.List;

/**
 * 打印模板-系统组件相关Service
 * Created by zfh on 2018/12/27
 */
public interface IScOperationPrinterSystemComponentService {

    /**
     * 添加系统组件
     * @param systemComponent {@link ScOperationPrinterSystemComponent}
     * @return 自增长主键
     */
    Integer savePrinterSystemComponent (ScOperationPrinterSystemComponent systemComponent);

    /**
     * 通过模板类型查询模板组件（系统组件）
     * @param templateType 模板类型
     * @return 组件列表
     */
    List<ScOperationPrinterSystemComponent> listPrinterSystemComponentByTemplateType(Integer templateType);
}
