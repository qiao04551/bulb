package com.maxzuo.printtemplate.dao;

import com.maxzuo.printtemplate.model.ScOperationPrinterDocumentType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 打印模板文档类型Mapper
 * Created by zfh on 2018/12/11
 */
@Mapper
public interface ScOperationPrinterDocumentTypeMapper {

    /**
     * 主键查询文档类型
     * @param id 主键
     * @return 实体
     */
    ScOperationPrinterDocumentType selectPrinterDocumentTypeByPrimaryId (Integer id);

    /**
     * 查询文档类型列表（status=1）
     * @return list
     */
    List<ScOperationPrinterDocumentType> selectPrinterDocumentType ();

    /**
     * 更新文档类型属性
     * @param scShopPrinterDocumentType 实体
     * @return 受影响的行数
     */
    Integer updatePrinterDocumentType (ScOperationPrinterDocumentType scShopPrinterDocumentType);
}
