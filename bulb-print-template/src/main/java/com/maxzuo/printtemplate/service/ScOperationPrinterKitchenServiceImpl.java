package com.maxzuo.printtemplate.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maxzuo.printtemplate.api.IScOperationPrinterKitchenService;
import com.maxzuo.printtemplate.dao.ScOperationPrinterKitchenMapper;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出票口相关Service实现类
 * Created by zfh on 2019/01/09
 */
@Service("scOperationPrinterKitchenService")
public class ScOperationPrinterKitchenServiceImpl implements IScOperationPrinterKitchenService {

    @Autowired
    private ScOperationPrinterKitchenMapper scOperationPrinterKitchenMapper;

    @Override
    public Integer save(ScOperationPrinterKitchen record) {
        scOperationPrinterKitchenMapper.insert(record);
        return record.getId();
    }

    @Override
    public ScOperationPrinterKitchen getByPrimaryKey(Integer id) {
        return scOperationPrinterKitchenMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<ScOperationPrinterKitchen> listPrinterKitchenByShopId(Integer shopId, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<ScOperationPrinterKitchen> kitchenList = scOperationPrinterKitchenMapper.selectPrinterKitchenListByShopId(shopId);
        return new PageInfo<>(kitchenList);
    }

    @Override
    public Integer updateByPrimaryKeySelective(ScOperationPrinterKitchen record) {
        return scOperationPrinterKitchenMapper.updateByPrimaryKeySelective(record);
    }
}
