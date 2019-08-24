package com.maxzuo.printtemplate.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maxzuo.printtemplate.api.IScOperationPrinterDeviceService;
import com.maxzuo.printtemplate.dao.ScOperationPrinterDeviceMapper;
import com.maxzuo.printtemplate.model.ScOperationPrinterDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 打印机相关Serice实现类
 * Created by zfh on 2019/01/09
 */
@Service("scOperationPrinterDeviceService")
public class ScOperationPrinterDeviceServiceImpl implements IScOperationPrinterDeviceService {

    @Autowired
    private ScOperationPrinterDeviceMapper scOperationPrinterDeviceMapper;

    @Override
    public Integer save(ScOperationPrinterDevice record) {
        scOperationPrinterDeviceMapper.insert(record);
        return record.getId();
    }

    @Override
    public ScOperationPrinterDevice getScShopPrinterDeviceByPrimaryKey(Integer id) {
        return scOperationPrinterDeviceMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<ScOperationPrinterDevice> listPrinterDeviceByShopId(Integer shopId, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<ScOperationPrinterDevice> printerDeviceList = scOperationPrinterDeviceMapper.selectPrinterDeviceListByShopId(shopId);
        return new PageInfo<>(printerDeviceList);
    }

    @Override
    public Integer updateByPrimaryKeySelective(ScOperationPrinterDevice record) {
        return scOperationPrinterDeviceMapper.updateByPrimaryKeySelective(record);
    }
}
