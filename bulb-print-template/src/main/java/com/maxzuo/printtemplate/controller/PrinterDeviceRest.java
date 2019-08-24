package com.maxzuo.printtemplate.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.maxzuo.printtemplate.api.IScOperationPrinterDeviceService;
import com.maxzuo.printtemplate.dto.Param;
import com.maxzuo.printtemplate.form.SavePrinterDeviceForm;
import com.maxzuo.printtemplate.form.UpdatePrinterDeviceForm;
import com.maxzuo.printtemplate.model.ScOperationPrinterDevice;
import com.maxzuo.printtemplate.vo.PrinterDeviceVO;
import com.maxzuo.printtemplate.vo.Result;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印机Rest
 * Created by zfh on 2019/01/09
 */
@RequestMapping("/zxcity_restful/ws/foodBoot/printerDevice")
@RestController
public class PrinterDeviceRest {

    @Autowired
    private IScOperationPrinterDeviceService scShopPrinterDeviceService;

    /**
     * 新增打印机
     * @param param {@link Param}
     * @return {@link Result}
     */
    @PostMapping("save")
    public Result save (@RequestAttribute("param") Param param) {
        SavePrinterDeviceForm savePrinterDeviceForm = JSONObject.parseObject(param.getData().toString(), SavePrinterDeviceForm.class);
        Result result = savePrinterDeviceForm.validateParam();
        if (Result.RESULT_FAILURE.equals(result.getCode())) {
            return result;
        }
        scShopPrinterDeviceService.save(savePrinterDeviceForm.getScShopPrinterDevice());
        result.setMsg("添加成功！");
        return result;
    }

    /**
     * 打印机列表
     * @param param {@link Param}
     *          shopId：店铺ID
     *          page：页码
     *          rows：单页条数
     * @return {@link Result}
     */
    @PostMapping("query")
    public Result query (@RequestAttribute("param") Param param) {
        Result result = new Result(Result.RESULT_FAILURE, "系统繁忙！");
        JSONObject jsonObject = JSONObject.parseObject(param.getData().toString());
        Integer shopId = jsonObject.getInteger("shopId");
        Integer page = jsonObject.getInteger("page");
        Integer rows = jsonObject.getInteger("rows");
        if (shopId == null || page == null || rows == null) {
            result.setMsg("缺少参数！");
            return result;
        }
        PageInfo<ScOperationPrinterDevice> pageInfo = scShopPrinterDeviceService.listPrinterDeviceByShopId(shopId, page, rows);
        if (pageInfo != null) {
            List<PrinterDeviceVO> printerDeviceVOList = new ArrayList<>(10);
            for (ScOperationPrinterDevice printerDevice : pageInfo.getList()) {
                PrinterDeviceVO printerDeviceVO = new PrinterDeviceVO();
                BeanUtils.copyProperties(printerDevice, printerDeviceVO);
                printerDeviceVO.setCreateTime(DateFormatUtils.format(printerDevice.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                printerDeviceVOList.add(printerDeviceVO);
            }
            Long total = pageInfo.getTotal();
            result.setTotal(total.intValue());
            result.setData(printerDeviceVOList);
            result.setCode(Result.RESULT_SUCCESS);
            result.setMsg("查询成功！");
        }
        return result;
    }

    /**
     * 编辑打印机
     * @param param {@link Param}
     *          shopId：店铺ID
     *          printerDeviceId：打印机ID
     * @return {@link Result}
     */
    @PostMapping("edit")
    public Result edit (@RequestAttribute("param") Param param) {
        Result result = new Result(Result.RESULT_FAILURE, "系统繁忙！");
        JSONObject jsonObject = JSONObject.parseObject(param.getData().toString());
        Integer shopId = jsonObject.getInteger("shopId");
        Integer printerDeviceId = jsonObject.getInteger("printerDeviceId");
        if (shopId == null || printerDeviceId == null) {
            result.setMsg("缺少参数！");
            return result;
        }
        ScOperationPrinterDevice printerDevice = scShopPrinterDeviceService.getScShopPrinterDeviceByPrimaryKey(printerDeviceId);
        if (printerDevice == null || Integer.valueOf(1).equals(printerDevice.getDelete()) || !shopId.equals(printerDevice.getShopId())) {
            result.setMsg("打印机不存在！");
        } else {
            PrinterDeviceVO printerDeviceVO = new PrinterDeviceVO();
            BeanUtils.copyProperties(printerDevice, printerDeviceVO);
            printerDeviceVO.setCreateTime(DateFormatUtils.format(printerDevice.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            result.setCode(Result.RESULT_SUCCESS);
            result.setData(printerDeviceVO);
            result.setMsg("查询成功！");
        }
        return result;
    }

    /**
     * 更新打印机
     * @param param {@link Param}
     * @return {@link Result}
     */
    @PostMapping("update")
    public Result update (@RequestAttribute("param") Param param) {
        UpdatePrinterDeviceForm updatePrinterDeviceForm = JSONObject.parseObject(param.getData().toString(), UpdatePrinterDeviceForm.class);
        Result result = updatePrinterDeviceForm.validateParam();
        if (Result.RESULT_FAILURE.equals(result.getCode())) {
            return result;
        }
        ScOperationPrinterDevice printerDevice = scShopPrinterDeviceService.getScShopPrinterDeviceByPrimaryKey(updatePrinterDeviceForm.getId());
        if (printerDevice == null || Integer.valueOf(1).equals(printerDevice.getDelete()) || !updatePrinterDeviceForm.getShopId().equals(printerDevice.getShopId())) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("打印机不存在！");
        } else {
            scShopPrinterDeviceService.updateByPrimaryKeySelective(updatePrinterDeviceForm.getScShopPrinterDevice());
            result.setCode(Result.RESULT_SUCCESS);
            result.setMsg("更新成功！");
        }
        return result;
    }

    /**
     * 删除打印机
     * @param param {@link Param}
     *          shopId：店铺ID
     *          printerDeviceId：打印机ID
     * @return {@link Result}
     */
    @PostMapping("delete")
    public Result delete (@RequestAttribute("param") Param param) {
        Result result = new Result(Result.RESULT_FAILURE, "系统繁忙！");
        JSONObject jsonObject = JSONObject.parseObject(param.getData().toString());
        Integer shopId = jsonObject.getInteger("shopId");
        Integer printerDeviceId = jsonObject.getInteger("printerDeviceId");
        if (shopId == null || printerDeviceId == null) {
            result.setMsg("缺少参数！");
            return result;
        }
        ScOperationPrinterDevice printerDevice = scShopPrinterDeviceService.getScShopPrinterDeviceByPrimaryKey(printerDeviceId);
        if (printerDevice == null || Integer.valueOf(1).equals(printerDevice.getDelete()) || !shopId.equals(printerDevice.getShopId())) {
            result.setMsg("打印机不存在！");
        } else {
            printerDevice.setDelete(1);
            Integer affected = scShopPrinterDeviceService.updateByPrimaryKeySelective(printerDevice);
            if (affected != null && affected > 0) {
                result.setCode(Result.RESULT_SUCCESS);
                result.setMsg("删除成功！");
            }
        }
        return result;
    }
}
