package com.maxzuo.printtemplate.form;

import com.maxzuo.printtemplate.vo.Result;
import org.apache.commons.lang3.StringUtils;

/**
 * 更新模板请求对象
 * Created by zfh on 2019/01/04
 */
public class UpdateCustomTemplateDocumentForm {

    /** 店铺id */
    private Integer shopId;

    /** 模板名称 */
    private String name;

    /** 模板id */
    private Integer templateId;

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
        if (StringUtils.isBlank(name)) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("缺少模板名称！");
        }
        if (templateId == null) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("缺少模板id！");
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

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
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
        return "UpdateCustomTemplateDocumentForm{" +
                "shopId=" + shopId +
                ", name='" + name + '\'' +
                ", templateId=" + templateId +
                ", url='" + url + '\'' +
                ", modules='" + modules + '\'' +
                '}';
    }
}
