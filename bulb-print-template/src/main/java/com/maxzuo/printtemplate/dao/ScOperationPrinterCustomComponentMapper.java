package com.maxzuo.printtemplate.dao;

import com.maxzuo.printtemplate.model.ScOperationPrinterCustomComponent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 打印模板-自定义组件Mapper
 * Created by zfh on 2019/01/03
 */
@Mapper
public interface ScOperationPrinterCustomComponentMapper {

    /**
     * 保存多个自定义组件
     * @param shopPrinterCustomComponentList 组件列表
     * @return 受影响的行数
     */
    Integer insertMultiplePrinterCustomComponent (List<ScOperationPrinterCustomComponent> shopPrinterCustomComponentList);

    /**
     * 根据模板id移除组件
     * @param templateId 模板id
     * @return 受影响的行数
     */
    Integer updateMultiplePrinterCustomComponentByTemplateId (Integer templateId);

    /**
     * 通过模板id查询模板组件（预览组件）
     * @param documentTemplateId 模板id
     * @return 组件列表
     */
    List<ScOperationPrinterCustomComponent> selectPrinterTemplateCustomComponentByTemplateId(Integer documentTemplateId);
}
