package com.maxzuo.printtemplate.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.maxzuo.printtemplate.api.IScOperationPrinterKitchenDocumentTypeRulesService;
import com.maxzuo.printtemplate.api.IScOperationPrinterKitchenGoodsRulesService;
import com.maxzuo.printtemplate.api.IScOperationPrinterKitchenService;
import com.maxzuo.printtemplate.api.IScOperationPrinterKitchenTableRulesService;
import com.maxzuo.printtemplate.dto.Param;
import com.maxzuo.printtemplate.form.SavePrinterKitchenForm;
import com.maxzuo.printtemplate.form.UpdatePrinterKitchenForm;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchen;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenDocumentTypeRules;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenGoodsRules;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenTableRules;
import com.maxzuo.printtemplate.vo.PrinterKitchenIntegrationVO;
import com.maxzuo.printtemplate.vo.PrinterKitchenVO;
import com.maxzuo.printtemplate.vo.Result;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 打印机-出票口Rest
 * Created by zfh on 2019/01/09
 */
@RequestMapping("/zxcity_restful/ws/foodBoot/printerKitchen")
@RestController
public class PrinterKitchenRest {

    private static final Logger logger = LoggerFactory.getLogger(PrinterKitchenRest.class);

    @Autowired
    private IScOperationPrinterKitchenService scShopPrinterKitchenService;

    @Autowired
    private IScOperationPrinterKitchenDocumentTypeRulesService scShopPrinterKitchenDocumentTypeRulesService;

    @Autowired
    private IScOperationPrinterKitchenTableRulesService scShopPrinterKitchenTableRulesService;

    @Autowired
    private IScOperationPrinterKitchenGoodsRulesService scShopPrinterKitchenGoodsRulesService;

    /**
     * 新增出票口
     * @param param {@link Param}
     * @return {@link Result}
     */
    @PostMapping("save")
    public Result save (@RequestAttribute("param") Param param) {
        SavePrinterKitchenForm printerKitchenForm = JSONObject.parseObject(param.getData().toString(), SavePrinterKitchenForm.class);
        Result result = printerKitchenForm.validateParam();
        if (Result.RESULT_FAILURE.equals(result.getCode())) {
            return result;
        }
        // 新增出票口，配置：票据类型、桌台区域、打印的菜品
        Integer printerKitchenId = 0;
        try {
            printerKitchenId = scShopPrinterKitchenService.save(printerKitchenForm.getScShopPrinterKitchen());
            scShopPrinterKitchenDocumentTypeRulesService.saveMultipleRecord(printerKitchenForm.getDocumentTypeRulesList(printerKitchenId));
            if (Integer.valueOf(1).equals(printerKitchenForm.getTable())) {
                scShopPrinterKitchenTableRulesService.saveMultipleRecord(printerKitchenForm.getTableRulesList(printerKitchenId));
            }
            if (Integer.valueOf(1).equals(printerKitchenForm.getGoods())) {
                scShopPrinterKitchenGoodsRulesService.saveMultipleRecord(printerKitchenForm.getGoodsRulesList(printerKitchenId));
            }
            result.setMsg("添加成功！");
        } catch (Exception e) {
            if (printerKitchenId > 0) {
                ScOperationPrinterKitchen scShopPrinterKitchen = new ScOperationPrinterKitchen();
                scShopPrinterKitchen.setDelete(1);
                scShopPrinterKitchenService.updateByPrimaryKeySelective(scShopPrinterKitchen);
            }
            logger.error("【打印机-出票口Rest】新增出票口异常", e);
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("系统繁忙！");
        }
        return result;
    }

    /**
     * 出票口列表
     * @param param {@link Param}
     *          shopId：店铺ID
     *          page：页码
     *          rows：每页条数
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
        PageInfo<ScOperationPrinterKitchen> printerKitchenPageInfo = scShopPrinterKitchenService.listPrinterKitchenByShopId(shopId, page, rows);
        if (printerKitchenPageInfo != null) {
            List<PrinterKitchenVO> printerKitchenVOList = new ArrayList<>(10);
            for (ScOperationPrinterKitchen printerKitchen : printerKitchenPageInfo.getList()) {
                PrinterKitchenVO printerKitchenVO = new PrinterKitchenVO();
                BeanUtils.copyProperties(printerKitchen, printerKitchenVO);
                printerKitchenVO.setCreateTime(DateFormatUtils.format(printerKitchen.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
                printerKitchenVOList.add(printerKitchenVO);
            }
            Long total = printerKitchenPageInfo.getTotal();
            result.setCode(Result.RESULT_SUCCESS);
            result.setMsg("查询成功！");
            result.setTotal(total.intValue());
            result.setData(printerKitchenVOList);
        }
        return result;
    }

    /**
     * 编辑出票口
     * @param param {@link Param}
     *          shopId：店铺ID
     *          printerKitchenId：出票口ID
     * @return {@link Result}
     */
    @PostMapping("edit")
    public Result edit (@RequestAttribute("param") Param param) {
        Result result = new Result(Result.RESULT_FAILURE, "系统繁忙！");
        JSONObject jsonObject = JSONObject.parseObject(param.getData().toString());
        Integer shopId = jsonObject.getInteger("shopId");
        Integer printerKitchenId = jsonObject.getInteger("printerKitchenId");
        if (shopId == null || printerKitchenId == null) {
            result.setMsg("缺少参数！");
            return result;
        }
        ScOperationPrinterKitchen printerKitchen = scShopPrinterKitchenService.getByPrimaryKey(printerKitchenId);
        if (printerKitchen == null || Integer.valueOf(1).equals(printerKitchen.getDelete()) || !shopId.equals(printerKitchen.getShopId())) {
            result.setMsg("出票口不存在！");
        } else {
            PrinterKitchenIntegrationVO printerKitchenIntegrationVO = new PrinterKitchenIntegrationVO();
            BeanUtils.copyProperties(printerKitchen, printerKitchenIntegrationVO);

            List<ScOperationPrinterKitchenDocumentTypeRules> documentTypeRulesList = scShopPrinterKitchenDocumentTypeRulesService.listPrinterKitchenDocumentTypeRulesByPrinterKitchenId(printerKitchenId);
            List<Map<String, Object>> documentTypeRules = new ArrayList<>(10);
            for (ScOperationPrinterKitchenDocumentTypeRules kitchenDocumentTypeRules : documentTypeRulesList) {
                Map<String, Object> documentTypeRulesItem = new HashMap<>(10);
                documentTypeRulesItem.put("documentTypeId", kitchenDocumentTypeRules.getDocumentTypeId());
                documentTypeRulesItem.put("number", kitchenDocumentTypeRules.getNumber());
                documentTypeRulesItem.put("printerType", kitchenDocumentTypeRules.getPrinterType());
                documentTypeRules.add(documentTypeRulesItem);
            }
            List<Map<String, Object>> tableRules = new ArrayList<>(10);
            if (Integer.valueOf(1).equals(printerKitchen.getTable())) {
                List<ScOperationPrinterKitchenTableRules> kitchenTableRulesList = scShopPrinterKitchenTableRulesService.listPrinterKitchenTableRulesByPrinterKitchenId(printerKitchenId);
                for (ScOperationPrinterKitchenTableRules kitchenTableRules : kitchenTableRulesList) {
                    Map<String, Object> tableRulesItem = new HashMap<>(10);
                    tableRulesItem.put("tableId", kitchenTableRules.getTableId());
                    tableRules.add(tableRulesItem);
                }
            }
            List<Map<String, Object>> goodsRules = new ArrayList<>(10);
            if (Integer.valueOf(1).equals(printerKitchen.getGoods())) {
                List<ScOperationPrinterKitchenGoodsRules> printerKitchenGoodsRulesList = scShopPrinterKitchenGoodsRulesService.listPrinterKitchenGoodsRulesByPrinterKitchenId(printerKitchenId);
                for (ScOperationPrinterKitchenGoodsRules kitchenGoodsRules : printerKitchenGoodsRulesList) {
                    Map<String, Object> goodsRulesItem = new HashMap<>(10);
                    goodsRulesItem.put("goodsId", kitchenGoodsRules.getGoodsId());
                    goodsRulesItem.put("stockId", kitchenGoodsRules.getStockId());
                    goodsRules.add(goodsRulesItem);
                }
            }
            printerKitchenIntegrationVO.setDocumentTypeRules(documentTypeRules);
            printerKitchenIntegrationVO.setTableRules(tableRules);
            printerKitchenIntegrationVO.setGoodsRules(goodsRules);
            result.setData(printerKitchenIntegrationVO);
            result.setCode(Result.RESULT_SUCCESS);
            result.setMsg("查询成功！");
        }
        return result;
    }

    /**
     * 更新出票口
     * @param param {@link Param}
     * @return {@link Result}
     */
    @PostMapping("update")
    public Result update (@RequestAttribute("param") Param param) {
        UpdatePrinterKitchenForm printerKitchenForm = JSONObject.parseObject(param.getData().toString(), UpdatePrinterKitchenForm.class);
        Result result = printerKitchenForm.validateParam();
        if (Result.RESULT_FAILURE.equals(result.getCode())) {
            return result;
        }
        ScOperationPrinterKitchen oldPrinterKitchen = scShopPrinterKitchenService.getByPrimaryKey(printerKitchenForm.getId());
        if (oldPrinterKitchen == null || Integer.valueOf(1).equals(oldPrinterKitchen.getDelete()) || !printerKitchenForm.getShopId().equals(oldPrinterKitchen.getShopId())) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("出票口不存在！");
            return result;
        }
        Integer affected = scShopPrinterKitchenService.updateByPrimaryKeySelective(printerKitchenForm.getScShopPrinterKitchen());
        if (affected > 0) {
            scShopPrinterKitchenDocumentTypeRulesService.removeMultipleRecordByPrinterKitchenId(printerKitchenForm.getId());
            scShopPrinterKitchenTableRulesService.removeMultipleRecordByPrinterKitchenId(printerKitchenForm.getId());
            scShopPrinterKitchenGoodsRulesService.removeMultipleRecordByPrinterKitchenId(printerKitchenForm.getId());

            scShopPrinterKitchenDocumentTypeRulesService.saveMultipleRecord(printerKitchenForm.getDocumentTypeRulesList());
            if (Integer.valueOf(1).equals(printerKitchenForm.getTable())) {
                scShopPrinterKitchenTableRulesService.saveMultipleRecord(printerKitchenForm.getTableRulesList());
            }
            if (Integer.valueOf(1).equals(printerKitchenForm.getGoods())) {
                scShopPrinterKitchenGoodsRulesService.saveMultipleRecord(printerKitchenForm.getGoodsRulesList());
            }
        }
        result.setMsg("更新成功！");
        return result;
    }

    /**
     * 删除出票口
     * @param request {@link HttpServletRequest}
     *          shopId：店铺ID
     *          printerKitchenId：出票口ID
     * @return {@link Result}
     */
    @PostMapping("delete")
    public Result delete (HttpServletRequest request) {
        Result result = new Result(Result.RESULT_FAILURE, "系统繁忙！");
        Param param = (Param) request.getAttribute("param");
        JSONObject jsonObject = JSONObject.parseObject(param.getData().toString());
        Integer shopId = jsonObject.getInteger("shopId");
        Integer printerKitchenId = jsonObject.getInteger("printerKitchenId");
        if (shopId == null || printerKitchenId == null) {
            result.setMsg("缺少参数！");
            return result;
        }
        ScOperationPrinterKitchen printerKitchen = scShopPrinterKitchenService.getByPrimaryKey(printerKitchenId);
        if (printerKitchen == null || Integer.valueOf(1).equals(printerKitchen.getDelete()) || !shopId.equals(printerKitchen.getShopId())) {
            result.setMsg("出票口不存在！");
        } else {
            printerKitchen.setDelete(1);
            Integer affected = scShopPrinterKitchenService.updateByPrimaryKeySelective(printerKitchen);
            if (affected > 0) {
                scShopPrinterKitchenDocumentTypeRulesService.removeMultipleRecordByPrinterKitchenId(printerKitchenId);
                scShopPrinterKitchenTableRulesService.removeMultipleRecordByPrinterKitchenId(printerKitchenId);
                scShopPrinterKitchenGoodsRulesService.removeMultipleRecordByPrinterKitchenId(printerKitchenId);
                result.setCode(Result.RESULT_SUCCESS);
                result.setMsg("删除成功！");
            }
        }
        return result;
    }
}
