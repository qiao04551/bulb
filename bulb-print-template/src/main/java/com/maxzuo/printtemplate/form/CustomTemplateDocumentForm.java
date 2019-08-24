package com.maxzuo.printtemplate.form;

import com.maxzuo.printtemplate.model.ScOperationPrinterTemplateDocument;
import com.maxzuo.printtemplate.vo.Result;
import org.apache.commons.lang3.StringUtils;

/**
 * 添加模板请求对象
 * Created by zfh on 2019/01/04
 */
public class CustomTemplateDocumentForm {

    /** 店铺id */
    private Integer shopId;

    /** 操作员id */
    private Integer operatorId;

    /** 模板名称 */
    private String name;

    /** 文档类型 */
    private Integer documentType;

    /** 票据预览图 */
    private String url;

    /** 模板-模块 */
    private String modules;

    /**
     * 验证入参
     * @return {@link Result}
     */
    public Result validateParam () {

        Result result = new Result(Result.RESULT_SUCCESS);
        if (shopId == null) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("缺少店铺id！");
        }
        if (operatorId == null) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("缺少平台用户id！");
        }
        if (StringUtils.isBlank(name)) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("缺少模板名称！");
        }
        if (documentType == null) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("缺少模板类型！");
        }
        if (StringUtils.isBlank(url)) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("缺少票据预览图！");
        }
        if (StringUtils.isBlank(modules)) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("缺少模块！");
        }
        return result;
    }

    /**
     * 入参对象转换为实体对象
     * @return {@link ScOperationPrinterTemplateDocument}
     */
    public ScOperationPrinterTemplateDocument convertToScShopPrinterTemplateDocument () {
        ScOperationPrinterTemplateDocument templateDocument = new ScOperationPrinterTemplateDocument();
        templateDocument.setName(name);
        templateDocument.setDocumentType(documentType);
        templateDocument.setUrl(url);
        // 默认状态：1-启用 2-禁用
        templateDocument.setStatus(2);
        templateDocument.setShopId(shopId);
        templateDocument.setCreatorId(operatorId);
        templateDocument.setCreatorName("");
        return templateDocument;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Integer documentType) {
        this.documentType = documentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }

    @Override
    public String toString() {
        return "CustomTemplateDocumentForm{" +
                "shopId=" + shopId +
                ", operatorId=" + operatorId +
                ", name='" + name + '\'' +
                ", documentType=" + documentType +
                ", url='" + url + '\'' +
                ", modules='" + modules + '\'' +
                '}';
    }
}
