package com.maxzuo.printtemplate.form;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchen;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenDocumentTypeRules;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenGoodsRules;
import com.maxzuo.printtemplate.model.ScOperationPrinterKitchenTableRules;
import com.maxzuo.printtemplate.vo.Result;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 新增出票口 请求
 * Created by zfh on 2019/01/09
 */
public class SavePrinterKitchenForm {

    /** 出票口名称 */
    private String name;

    /** 打印机ID */
    private Integer printerDeviceId;

    /** 店铺ID */
    private Integer shopId;

    /** 平台用户ID */
    private Integer operatorId;

    /** 出票口票据类型 */
    private String documentTypeRules;

    /** 是否配置桌台区域：0-未配置 1-配置 */
    private Integer table;

    /** 是否配置打印的菜品：0-未配置 1-配置 */
    private Integer goods;

    /** 出票口桌台区域 */
    private String tableRules;

    /** 出票口配置的商品 */
    private String goodsRules;

    /**
     * 验证入参
     * @return {@link Result}
     */
    public Result validateParam () {
        Result result = new Result(Result.RESULT_SUCCESS);
        if (StringUtils.isBlank(name)) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("出票口名称不能为空！");
        }
        if (printerDeviceId == null) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("打印机ID不能为空！");
        }
        if (shopId == null) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("店铺ID不能为空！");
        }
        if (operatorId == null) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("平台用户ID不能为空！");
        }
        if (StringUtils.isBlank(documentTypeRules)) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("未设置票据类型！");
        }
        if (table == null) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("是否配置桌台区域！");
        } else {
            if (Integer.valueOf(1).equals(table)) {
                if (StringUtils.isBlank(tableRules)) {
                    result.setCode(Result.RESULT_FAILURE);
                    result.setMsg("未设置桌台区域！");
                }
            }
        }
        if (goods == null) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("是否配置菜品！");
        } else {
            if (Integer.valueOf(1).equals(goods)) {
                if (StringUtils.isBlank(goodsRules)) {
                    result.setCode(Result.RESULT_FAILURE);
                    result.setMsg("未设置打印菜品！");
                }
            }
        }
        return result;
    }

    /**
     * 获取 出票口实体对象
     * @return {@link ScOperationPrinterKitchen}
     */
    public ScOperationPrinterKitchen getScShopPrinterKitchen () {
        ScOperationPrinterKitchen scShopPrinterKitchen = new ScOperationPrinterKitchen();
        scShopPrinterKitchen.setName(name);
        scShopPrinterKitchen.setPrinterDeviceId(printerDeviceId);
        scShopPrinterKitchen.setTable(table);
        scShopPrinterKitchen.setGoods(goods);
        scShopPrinterKitchen.setDelete(0);
        scShopPrinterKitchen.setShopId(shopId);
        scShopPrinterKitchen.setCreatorId(operatorId);
        return scShopPrinterKitchen;
    }

    /**
     * 获取出票口 票据类型
     * @param printerKitchenId 出票口ID
     * @return list
     */
    public List<ScOperationPrinterKitchenDocumentTypeRules> getDocumentTypeRulesList(Integer printerKitchenId) {
        JSONArray jsonArray = JSONObject.parseArray(documentTypeRules);
        List<ScOperationPrinterKitchenDocumentTypeRules> kitchenDocumentTypeRulesList = new ArrayList<>(10);
        for (Object item : jsonArray) {
            ScOperationPrinterKitchenDocumentTypeRules scShopPrinterKitchenDocumentTypeRules = JSONObject.parseObject(JSONObject.toJSONString(item), ScOperationPrinterKitchenDocumentTypeRules.class);
            scShopPrinterKitchenDocumentTypeRules.setPrinterKitchenId(printerKitchenId);
            scShopPrinterKitchenDocumentTypeRules.setDelete(0);
            kitchenDocumentTypeRulesList.add(scShopPrinterKitchenDocumentTypeRules);
        }
        return kitchenDocumentTypeRulesList;
    }

    /**
     * 获取出票口 配置的桌台区域
     * @param printerKitchenId 出票口ID
     * @return list
     */
    public List<ScOperationPrinterKitchenTableRules> getTableRulesList(Integer printerKitchenId) {
        JSONArray tableRulesArray = JSONObject.parseArray(tableRules);
        List<ScOperationPrinterKitchenTableRules> tableRulesList = new ArrayList<>(10);
        for (Object item: tableRulesArray) {
            ScOperationPrinterKitchenTableRules tableRules = JSONObject.parseObject(JSONObject.toJSONString(item), ScOperationPrinterKitchenTableRules.class);
            tableRules.setPrinterKitchenId(printerKitchenId);
            tableRules.setDelete(0);
            tableRulesList.add(tableRules);
        }
        return tableRulesList;
    }

    /**
     * 获取出票口 配置的打印菜品
     * @param printerKitchenId 出票口ID
     * @return list
     */
    public List<ScOperationPrinterKitchenGoodsRules> getGoodsRulesList(Integer printerKitchenId) {
        JSONArray goodsRulesArray = JSONObject.parseArray(goodsRules);
        List<ScOperationPrinterKitchenGoodsRules> goodsRulesList = new ArrayList<>(10);
        for (Object item: goodsRulesArray) {
            ScOperationPrinterKitchenGoodsRules goodsRules = JSONObject.parseObject(JSONObject.toJSONString(item), ScOperationPrinterKitchenGoodsRules.class);
            goodsRules.setPrinterKitchenId(printerKitchenId);
            goodsRules.setDelete(0);
            goodsRulesList.add(goodsRules);
        }
        return goodsRulesList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrinterDeviceId() {
        return printerDeviceId;
    }

    public void setPrinterDeviceId(Integer printerDeviceId) {
        this.printerDeviceId = printerDeviceId;
    }

    public Integer getTable() {
        return table;
    }

    public void setTable(Integer table) {
        this.table = table;
    }

    public Integer getGoods() {
        return goods;
    }

    public void setGoods(Integer goods) {
        this.goods = goods;
    }

    public String getDocumentTypeRules() {
        return documentTypeRules;
    }

    public void setDocumentTypeRules(String documentTypeRules) {
        this.documentTypeRules = documentTypeRules;
    }

    public String getTableRules() {
        return tableRules;
    }

    public void setTableRules(String tableRules) {
        this.tableRules = tableRules;
    }

    public String getGoodsRules() {
        return goodsRules;
    }

    public void setGoodsRules(String goodsRules) {
        this.goodsRules = goodsRules;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        return "SavePrinterKitchenForm{" +
                "name='" + name + '\'' +
                ", printerDeviceId=" + printerDeviceId +
                ", table=" + table +
                ", goods=" + goods +
                ", documentTypeRules='" + documentTypeRules + '\'' +
                ", tableRules='" + tableRules + '\'' +
                ", goodsRules='" + goodsRules + '\'' +
                ", shopId=" + shopId +
                ", operatorId=" + operatorId +
                '}';
    }
}
