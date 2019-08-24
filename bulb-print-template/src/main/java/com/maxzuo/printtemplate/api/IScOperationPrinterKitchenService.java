package com.maxzuo.printtemplate.api;

import com.github.pagehelper.PageInfo;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchen;

/**
 * 出票口相关Service
 * Created by zfh on 2019/01/09
 */
public interface IScOperationPrinterKitchenService {

    /**
     * 添加记录
     * @param record {@link ScOperationPrinterKitchen}
     * @return 自增主键
     */
    Integer save(ScOperationPrinterKitchen record);

    /**
     * 根据主键查询
     * @param id 主键
     * @return {@link ScOperationPrinterKitchen}
     */
    ScOperationPrinterKitchen getByPrimaryKey(Integer id);

    /**
     * 查询店铺下所有的出票口（未删除）
     * @param shopId 店铺id
     * @param page   页码
     * @param rows   页条数
     * @return {@link PageInfo}
     */
    PageInfo<ScOperationPrinterKitchen> listPrinterKitchenByShopId (Integer shopId, Integer page, Integer rows);

    /**
     * 根据主键有选择性的更新
     * @param record {@link ScOperationPrinterKitchen}
     * @return 受影响的条数
     */
    Integer updateByPrimaryKeySelective(ScOperationPrinterKitchen record);
}
