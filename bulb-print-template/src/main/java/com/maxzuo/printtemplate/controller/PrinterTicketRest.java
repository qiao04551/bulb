package com.maxzuo.printtemplate.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.maxzuo.printtemplate.api.*;
import com.maxzuo.printtemplate.dto.Param;
import com.maxzuo.printtemplate.enums.SystemComponentTypeEnum;
import com.maxzuo.printtemplate.form.CustomTemplateDocumentForm;
import com.maxzuo.printtemplate.form.UpdateCustomTemplateDocumentForm;
import com.maxzuo.printtemplate.model.*;
import com.maxzuo.printtemplate.vo.PrinterDocumentTypeVO;
import com.maxzuo.printtemplate.vo.PrinterTemplateDocumentVO;
import com.maxzuo.printtemplate.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 打印机设置-票据样式管理Rest
 * Created by zfh on 2018/12/12
 */
@RequestMapping("/zxcity_restful/ws/foodBoot/printerTicket")
@RestController
public class PrinterTicketRest {

    private final Logger logger = LoggerFactory.getLogger(PrinterTicketRest.class);

    @Autowired
    private IScOperationPrinterDocumentTypeService scShopPrinterDocumentTypeService;

    @Autowired
    private IScOperationPrinterTemplateModuleService scShopPrinterTemplateModuleService;

    @Autowired
    private IScOperationPrinterTemplateDocumentService scShopPrinterTemplateDocumentService;

    @Autowired
    private IScOperationPrinterSystemComponentService scShopPrinterSystemComponentService;

    @Autowired
    private IScOperationPrinterCustomComponentService scShopPrinterCustomComponentService;

    /**
     * 票据类型
     */
    @PostMapping("queryType")
    public Result queryType () {
        List<ScOperationPrinterDocumentType> typeList = scShopPrinterDocumentTypeService.listPrinterDocumentType();
        List<PrinterDocumentTypeVO> typeVOList = new ArrayList<>(10);
        for (ScOperationPrinterDocumentType documentType : typeList) {
            PrinterDocumentTypeVO typeVO = new PrinterDocumentTypeVO();
            BeanUtils.copyProperties(documentType, typeVO);
            typeVOList.add(typeVO);
        }
        Result result = new Result(Result.RESULT_SUCCESS, "查询成功");
        result.setData(typeVOList);
        result.setTotal(typeVOList.size());
        return result;
    }

    /**
     * 查询店铺 某种票据类型模板列表
     * @param param {@link Param}
     *          shopId：店铺id
     *          documentType：文档类型
     * @return {@link Result}
     */
    @PostMapping("query")
    public Result query (@RequestAttribute("param") Param param) {
        JSONObject jsonObject = JSON.parseObject(param.getData().toString());
        Integer shopId = jsonObject.getInteger("shopId");
        Integer documentType = jsonObject.getInteger("documentType");
        if (shopId == null || documentType == null) {
            return new Result(Result.RESULT_FAILURE,"缺少参数！");
        }
        // 模板列表（含默认的模板）
        List<PrinterTemplateDocumentVO> templateList = new ArrayList<>(10);
        List<ScOperationPrinterTemplateDocument> scShopPrinterTemplateDocuments = scShopPrinterTemplateDocumentService.listPrinterTemplateDocumentByShopIdAndDocumentType(shopId, documentType);
        for (ScOperationPrinterTemplateDocument templateDocument : scShopPrinterTemplateDocuments) {
            PrinterTemplateDocumentVO templateVO = new PrinterTemplateDocumentVO();
            BeanUtils.copyProperties(templateDocument, templateVO);
            templateVO.setDefaultTemplet(0);
            if (Integer.valueOf(0).equals(templateDocument.getShopId())) {
                templateVO.setDefaultTemplet(1);
            }
            // 查询模板组件
            List<ScOperationPrinterCustomComponent> customComponentList = scShopPrinterCustomComponentService.listPrinterTemplateCustomComponentByTemplateId(templateDocument.getId());
            Map<Integer, List<Object>> previewRowsMap = buildPreviewComponentStructure(customComponentList);
            List<ScOperationPrinterTemplateModule> moduleList = scShopPrinterTemplateModuleService.listPrinterTemplateModuleByDocumentId(templateDocument.getDocumentType());
            List<Map<String, Object>> modules = new ArrayList<>(10);
            for (ScOperationPrinterTemplateModule templateModule : moduleList) {
                Map<String, Object> module = new HashMap<>(10);
                module.put("moduleId", templateModule.getId());
                module.put("moduleName", templateModule.getModuleName());
                module.put("moduleDescribe", templateModule.getModuleDescribe());
                module.put("sort", templateModule.getSort());
                module.put("rows", previewRowsMap.get(templateModule.getId()));
                modules.add(module);
            }
            templateVO.setModules(modules);
            templateList.add(templateVO);
        }
        Result result = new Result(Result.RESULT_SUCCESS, "查询成功");
        result.setData(templateList);
        result.setTotal(templateList.size());
        return result;
    }

    /**
     * 票据样式-获取系统组件
     * @param param {@link Param}
     *          templateId：模板id
     *          documentType：票据类型
     * @return {@link Result}
     */
    @PostMapping("getSystemComponents")
    public Result getSystemComponents (@RequestAttribute("param") Param param) {
        Result result = new Result(Result.RESULT_FAILURE);
        JSONObject jsonObject = JSON.parseObject(param.getData().toString());
        Integer templateId = jsonObject.getInteger("templateId");
        Integer documentType = jsonObject.getInteger("documentType");
        if (templateId == null || documentType == null) {
            result.setMsg("缺少参数！");
            return result;
        }
        ScOperationPrinterTemplateDocument templateDocument = scShopPrinterTemplateDocumentService.getShopPrinterTemplateDocumentByPrimaryId(templateId);
        if (templateDocument == null || Integer.valueOf(0).equals(templateDocument.getStatus()) || !documentType.equals(templateDocument.getDocumentType())) {
            result.setMsg("模板不存在！");
            return result;
        }

        List<ScOperationPrinterSystemComponent> scShopPrinterSystemComponents = scShopPrinterSystemComponentService.listPrinterSystemComponentByTemplateType(templateDocument.getDocumentType());
        Map<Integer, List<Object>> listMap = buildSystemComponentStructure(scShopPrinterSystemComponents);
        List<ScOperationPrinterTemplateModule> moduleList = scShopPrinterTemplateModuleService.listPrinterTemplateModuleByDocumentId(documentType);
        List<Map<String, Object>> modules = new ArrayList<>(10);
        for (ScOperationPrinterTemplateModule templateModule : moduleList) {
            Map<String, Object> module = new HashMap<>(10);
            module.put("moduleId", templateModule.getId());
            module.put("moduleName", templateModule.getModuleName());
            module.put("moduleDescribe", templateModule.getModuleDescribe());
            module.put("sort", templateModule.getSort());
            module.put("components", listMap.get(templateModule.getId()));
            modules.add(module);
        }
        Map<String, Object>  data = new HashMap<>(2);
        data.put("modules", modules);
        result.setCode(Result.RESULT_SUCCESS);
        result.setMsg("查询成功");
        result.setData(data);
        return result;
    }

    /**
     * 票据样式-系统组件数据结构
     * @param systemComponentList 组件列表
     * @return 按模块划分组件，K -> 模块名 V -> components
     */
    private Map<Integer, List<Object>> buildSystemComponentStructure (List<ScOperationPrinterSystemComponent> systemComponentList) {
        Map<Integer, List<Object>> moduleComponentMap = new HashMap<>(10);
        for (ScOperationPrinterSystemComponent systemComponent : systemComponentList) {
            List<Object> components = new ArrayList<>();
            // 1.判断所属模块
            if (moduleComponentMap.containsKey(systemComponent.getModuleId())) {
                components = moduleComponentMap.get(systemComponent.getModuleId());
            }
            // 2.判断父子组件
            if (systemComponent.getParentId() == 0) {
                if (SystemComponentTypeEnum.TEXT.getName().equals(systemComponent.getType())) {
                    components.add(systemComponent);
                }
                if (SystemComponentTypeEnum.HIDDEN.getName().equals(systemComponent.getType())) {
                    components.add(systemComponent);
                }
                if (SystemComponentTypeEnum.GRID.getName().equals(systemComponent.getType())) {
                    Map<String, Object> componentItem = new HashMap<>(16);
                    componentItem.put("component", systemComponent);
                    components.add(componentItem);
                }
            } else {
                // 3.找到父组件
                Map<String, Object> componentItem = new HashMap<>(16);
                for (Object item : components) {
                    if (item instanceof Map) {
                        Map<String, Object> oldComponentItem = (Map<String, Object>) item;
                        ScOperationPrinterSystemComponent component = (ScOperationPrinterSystemComponent) oldComponentItem.get("component");
                        if (component.getId().equals(systemComponent.getParentId())) {
                            componentItem = oldComponentItem;
                        }
                    }
                }
                if (componentItem.size() == 0) {
                    components.add(componentItem);
                }
                // 4.判断所属分类
                for (SystemComponentTypeEnum typeEnum : SystemComponentTypeEnum.values()) {
                    if (typeEnum.getName().equals(systemComponent.getType())) {
                        switch (typeEnum) {
                            case TITLE:
                                componentItem.put("tableTitle", systemComponent);
                                break;
                            case CATEGORY:
                                componentItem.put("category", systemComponent);
                                break;
                            case TABLE_HEAD:
                                // 5.表格标题（单行）
                                Map<String, Object> thead = new HashMap<>(16);
                                if (componentItem.containsKey("thead")) {
                                    thead = (Map<String, Object>) componentItem.get("thead");
                                } else {
                                    componentItem.put("thead", thead);
                                }
                                Map<String, Object> tableRowSet = new HashMap<>(16);
                                if (thead.containsKey("tableRowSet")) {
                                    tableRowSet = (Map<String, Object>) thead.get("tableRowSet");
                                } else {
                                    thead.put("tableRowSet", tableRowSet);
                                }
                                List<ScOperationPrinterSystemComponent> columnSet = new ArrayList<>(10);
                                if (tableRowSet.containsKey("columnSet")) {
                                    columnSet = (List<ScOperationPrinterSystemComponent>) tableRowSet.get("columnSet");
                                } else {
                                    tableRowSet.put("columnSet", columnSet);
                                }
                                columnSet.add(systemComponent);
                                break;
                            case TEXT:
                                // 6.表格体（多行）
                                Map<String, Object> tbody = new HashMap<>(16);
                                if (componentItem.containsKey("tbody")) {
                                    tbody = (Map<String, Object>) componentItem.get("tbody");
                                } else {
                                    componentItem.put("tbody", tbody);
                                }
                                List<Map<String, List<ScOperationPrinterSystemComponent>>> tbodyTableRowSet = new ArrayList<>(10);
                                if (tbody.containsKey("tableRowSet")) {
                                    tbodyTableRowSet = (List<Map<String, List<ScOperationPrinterSystemComponent>>>) tbody.get("tableRowSet");
                                } else {
                                    tbody.put("tableRowSet", tbodyTableRowSet);
                                }
                                List<ScOperationPrinterSystemComponent> tbodyColumnSet = new ArrayList<>(10);
                                for (Map<String, List<ScOperationPrinterSystemComponent>> tbodyColumnSetItem : tbodyTableRowSet) {
                                    List<ScOperationPrinterSystemComponent> oldTbodyColumnSet = tbodyColumnSetItem.get("columnSet");
                                    for (ScOperationPrinterSystemComponent anOldTbodyColumnSet : oldTbodyColumnSet) {
                                        if (anOldTbodyColumnSet.getRow().equals(systemComponent.getRow())) {
                                            tbodyColumnSet = oldTbodyColumnSet;
                                        }
                                    }
                                }
                                if (tbodyColumnSet.size() == 0) {
                                    Map<String, List<ScOperationPrinterSystemComponent>> tbodyColumnSetItem = new HashMap<>(16);
                                    tbodyColumnSetItem.put("columnSet", tbodyColumnSet);
                                    tbodyTableRowSet.add(tbodyColumnSetItem);
                                }
                                tbodyColumnSet.add(systemComponent);
                            default:
                        }
                    }
                }
            }
            moduleComponentMap.put(systemComponent.getModuleId(), components);
        }
        return moduleComponentMap;
    }

    /**
     * 票据样式-预览组件数据结构
     * @param customComponentList 组件列表
     * @return 按模块划分组件，K -> 模块名 V -> components
     */
    private Map<Integer, List<Object>> buildPreviewComponentStructure(List<ScOperationPrinterCustomComponent> customComponentList) {
        Map<Integer, List<Object>> moduleComponentMap = new HashMap<>(10);
        for (ScOperationPrinterCustomComponent customComponent : customComponentList) {
            List<Object> components = new ArrayList<>();
            // 1.判断所属模块
            if (moduleComponentMap.containsKey(customComponent.getModuleId())) {
                components = moduleComponentMap.get(customComponent.getModuleId());
            }
            // 2.判断父子组件
            if (customComponent.getParentId() == 0) {
                if (SystemComponentTypeEnum.TEXT.getName().equals(customComponent.getType())) {
                    components.add(customComponent);
                }
                if (SystemComponentTypeEnum.HIDDEN.getName().equals(customComponent.getType())) {
                    components.add(customComponent);
                }
                if (SystemComponentTypeEnum.GRID.getName().equals(customComponent.getType())) {
                    Map<String, Object> componentItem = new HashMap<>(16);
                    componentItem.put("component", customComponent);
                    components.add(componentItem);
                }
            } else {
                // 3.找到父组件
                Map<String, Object> componentItem = new HashMap<>(16);
                for (Object item : components) {
                    if (item instanceof Map) {
                        Map<String, Object> oldComponentItem = (Map<String, Object>) item;
                        ScOperationPrinterCustomComponent component = (ScOperationPrinterCustomComponent) oldComponentItem.get("component");
                        if (component.getSystemComponentId().equals(customComponent.getParentId())) {
                            componentItem = oldComponentItem;
                        }
                    }
                }
                if (componentItem.size() == 0) {
                    components.add(componentItem);
                }
                // 4.判断所属分类
                for (SystemComponentTypeEnum typeEnum : SystemComponentTypeEnum.values()) {
                    if (typeEnum.getName().equals(customComponent.getType())) {
                        switch (typeEnum) {
                            case TITLE:
                                componentItem.put("tableTitle", customComponent);
                                break;
                            case CATEGORY:
                                componentItem.put("category", customComponent);
                                break;
                            case TABLE_HEAD:
                                // 5.表格标题（单行）
                                Map<String, Object> thead = new HashMap<>(16);
                                if (componentItem.containsKey("thead")) {
                                    thead = (Map<String, Object>) componentItem.get("thead");
                                } else {
                                    componentItem.put("thead", thead);
                                }
                                Map<String, Object> tableRowSet = new HashMap<>(16);
                                if (thead.containsKey("tableRowSet")) {
                                    tableRowSet = (Map<String, Object>) thead.get("tableRowSet");
                                } else {
                                    thead.put("tableRowSet", tableRowSet);
                                }
                                List<ScOperationPrinterCustomComponent> columnSet = new ArrayList<>(10);
                                if (tableRowSet.containsKey("columnSet")) {
                                    columnSet = (List<ScOperationPrinterCustomComponent>) tableRowSet.get("columnSet");
                                } else {
                                    tableRowSet.put("columnSet", columnSet);
                                }
                                columnSet.add(customComponent);
                                break;
                            case TEXT:
                                // 6.表格体（多行）
                                Map<String, Object> tbody = new HashMap<>(16);
                                if (componentItem.containsKey("tbody")) {
                                    tbody = (Map<String, Object>) componentItem.get("tbody");
                                } else {
                                    componentItem.put("tbody", tbody);
                                }
                                List<Map<String, List<ScOperationPrinterCustomComponent>>> tbodyTableRowSet = new ArrayList<>(10);
                                if (tbody.containsKey("tableRowSet")) {
                                    tbodyTableRowSet = (List<Map<String, List<ScOperationPrinterCustomComponent>>>) tbody.get("tableRowSet");
                                } else {
                                    tbody.put("tableRowSet", tbodyTableRowSet);
                                }
                                List<ScOperationPrinterCustomComponent> tbodyColumnSet = new ArrayList<>(10);
                                for (Map<String, List<ScOperationPrinterCustomComponent>> tbodyColumnSetItem : tbodyTableRowSet) {
                                    List<ScOperationPrinterCustomComponent> oldTbodyColumnSet = tbodyColumnSetItem.get("columnSet");
                                    for (ScOperationPrinterCustomComponent anOldTbodyColumnSet : oldTbodyColumnSet) {
                                        if (anOldTbodyColumnSet.getRow().equals(customComponent.getRow())) {
                                            tbodyColumnSet = oldTbodyColumnSet;
                                        }
                                    }
                                }
                                if (tbodyColumnSet.size() == 0) {
                                    Map<String, List<ScOperationPrinterCustomComponent>> tbodyColumnSetItem = new HashMap<>(16);
                                    tbodyColumnSetItem.put("columnSet", tbodyColumnSet);
                                    tbodyTableRowSet.add(tbodyColumnSetItem);
                                }
                                tbodyColumnSet.add(customComponent);
                            default:
                        }
                    }
                }
            }
            moduleComponentMap.put(customComponent.getModuleId(), components);
        }

        Map<Integer, List<Object>> previewRowsMap = new HashMap<>(16);
        Set<Map.Entry<Integer, List<Object>>> entries = moduleComponentMap.entrySet();
        for (Map.Entry<Integer, List<Object>> entry : entries) {
            Integer moduleId = entry.getKey();
            List<Object> previewRowsList = new ArrayList<>(10);
            if (previewRowsMap.containsKey(moduleId)) {
                previewRowsList = previewRowsMap.get(moduleId);
            }
            for (Object item : entry.getValue()) {
                ScOperationPrinterCustomComponent customComponent;
                if (item instanceof Map) {
                    Map<String, Object> component = (Map<String, Object>) item;
                    customComponent = (ScOperationPrinterCustomComponent) component.get("component");
                } else {
                    customComponent = (ScOperationPrinterCustomComponent) item;
                    // 合并行
                    List<Object> oldComponents = new ArrayList<>(10);
                    for (Object previewRowItem : previewRowsList) {
                        Map<String, Object> previewRow = (Map<String, Object>) previewRowItem;
                        List<Object> components = (List<Object>) previewRow.get("components");
                        for (Object componentItem : components) {
                            if (componentItem instanceof ScOperationPrinterCustomComponent) {
                                ScOperationPrinterCustomComponent printerCustomComponent = (ScOperationPrinterCustomComponent) componentItem;
                                if (printerCustomComponent.getRow().equals(customComponent.getRow())) {
                                    oldComponents = components;
                                    break;
                                }
                            }
                        }
                    }
                    if (oldComponents.size() > 0) {
                        oldComponents.add(customComponent);
                        continue;
                    }
                }
                List<Object> componetsList = new ArrayList<>();
                componetsList.add(item);
                Map<String, Object> previewRow = new HashMap<>(16);
                previewRow.put("type", customComponent.getType());
                previewRow.put("sort", customComponent.getSort());
                previewRow.put("components", componetsList);
                previewRowsList.add(previewRow);
            }
            previewRowsMap.put(moduleId, previewRowsList);
        }
        return previewRowsMap;
    }

    /**
     * 票据样式-获取预览组件
     * @param param {@link Param}
     *          templateId：模板id
     *          documentType: 票据类型
     * @return {@link Result}
     */
    @PostMapping("getPreviewComponents")
    public Result getPreviewComponents (@RequestAttribute("param") Param param) {
        Result result = new Result(Result.RESULT_FAILURE);
        JSONObject jsonObject = JSON.parseObject(param.getData().toString());
        Integer templateId = jsonObject.getInteger("templateId");
        Integer documentType = jsonObject.getInteger("documentType");
        if (templateId == null || documentType == null) {
            result.setMsg("缺少参数！");
            return result;
        }
        ScOperationPrinterTemplateDocument templateDocument = scShopPrinterTemplateDocumentService.getShopPrinterTemplateDocumentByPrimaryId(templateId);
        if (templateDocument == null || !documentType.equals(templateDocument.getDocumentType())) {
            result.setMsg("模板不存在！");
            return result;
        }

        List<ScOperationPrinterCustomComponent> customComponentList = scShopPrinterCustomComponentService.listPrinterTemplateCustomComponentByTemplateId(templateId);
        Map<Integer, List<Object>> previewRowsMap = buildPreviewComponentStructure(customComponentList);
        List<ScOperationPrinterTemplateModule> moduleList = scShopPrinterTemplateModuleService.listPrinterTemplateModuleByDocumentId(templateDocument.getDocumentType());
        List<Map<String, Object>> modules = new ArrayList<>(10);
        for (ScOperationPrinterTemplateModule templateModule : moduleList) {
            Map<String, Object> module = new HashMap<>(10);
            module.put("moduleId", templateModule.getId());
            module.put("moduleName", templateModule.getModuleName());
            module.put("moduleDescribe", templateModule.getModuleDescribe());
            module.put("sort", templateModule.getSort());
            module.put("rows", previewRowsMap.get(templateModule.getId()));
            modules.add(module);
        }
        result.setCode(Result.RESULT_SUCCESS);
        result.setMsg("查询成功");
        result.setData(modules);
        return result;
    }

    /**
     * 检查模板是否应用门店（默认模板不能应用于门店）
     * @param param {@link Param}
     *          templateId：模板id
     * @return {@link Result}
     */
    @PostMapping("checkIsSwitched")
    public Result checkIsSwitched (@RequestAttribute("param") Param param) {
        JSONObject jsonObject = JSON.parseObject(param.getData().toString());
        Integer templateId = jsonObject.getInteger("templateId");
        if (templateId == null) {
            return new Result(Result.RESULT_FAILURE,"缺少参数！");
        }
        ScOperationPrinterTemplateDocument templateDocument = scShopPrinterTemplateDocumentService.getShopPrinterTemplateDocumentByPrimaryId(templateId);
        if (templateDocument == null || Integer.valueOf(0).equals(templateDocument.getStatus()) || Integer.valueOf(0).equals(templateDocument.getShopId())) {
            return new Result(Result.RESULT_FAILURE, "模板不存在！");
        }
        Result result = new Result();
        Map<String, Object> data = new HashMap<>(2);
        data.put("isSwitched", templateDocument.getStatus());
        result.setData(data);
        return result;
    }

    /**
     * 检查模板名称是否唯一
     * @param param {@link Param}
     *          templateId：模板id（新建模板 模板id传 0）
     *          name：模板名称
     * @return {@link Result}
     */
    @PostMapping("checkName")
    public Result checkName (@RequestAttribute("param") Param param) {
        Result result = new Result(Result.RESULT_FAILURE);
        JSONObject jsonObject = JSON.parseObject(param.getData().toString());
        Integer templateId = jsonObject.getInteger("templateId");
        String name = jsonObject.getString("name");
        if (templateId == null || StringUtils.isBlank(name)) {
            result.setMsg("缺少参数！");
            return result;
        }
        ScOperationPrinterTemplateDocument templateDocumentInfo = scShopPrinterTemplateDocumentService.getShopPrinterTemplateDocumentByName(name.trim());
        if (templateDocumentInfo != null) {
            if (Integer.valueOf(0).equals(templateId)) {
                result.setMsg("模板名称已存在，请更换模板名称！");
                return result;
            } else {
                if (!templateId.equals(templateDocumentInfo.getId())) {
                    result.setMsg("模板名称已存在，请更换模板名称！");
                    return result;
                }
            }
        }
        result.setCode(Result.RESULT_SUCCESS);
        result.setMsg("查询成功");
        return result;
    }

    /**
     * 新增模板
     * @param param {@link Param}
     * @return {@link Result}
     */
    @PostMapping("save")
    public Result save (@RequestAttribute("param") Param param) {
        CustomTemplateDocumentForm customTemplateDocumentForm = JSONObject.parseObject(param.getData().toString(), CustomTemplateDocumentForm.class);
        Result result = customTemplateDocumentForm.validateParam();
        if (Result.RESULT_FAILURE.equals(result.getCode())) {
            return result;
        }
        ScOperationPrinterTemplateDocument printerTemplateDocument = customTemplateDocumentForm.convertToScShopPrinterTemplateDocument();

        // 新增模板，处理模板组件
        Integer templateId = scShopPrinterTemplateDocumentService.saveShopPrinterTemplateDocument(printerTemplateDocument);
        try {
            handleCustomTemplateComponent(templateId, customTemplateDocumentForm.getModules());
        } catch (Exception e) {
            // 回滚模板
            scShopPrinterTemplateDocumentService.removeShopPrinterTemplateDocumentByPrimaryId(templateId);
            logger.error("【票据样式管理-新增模板】", e);
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("系统繁忙！");
            return result;
        }
        Map<String, Object> data = new HashMap<>(10);
        data.put("templateId", templateId);
        result.setMsg("保存成功");
        result.setData(data);
        return result;
    }

    /**
     * 处理自定义模板，提取自定义组件
     * @param templateId 自定义模板id
     * @param modulesStr 模板组件
     */
    private void handleCustomTemplateComponent (Integer templateId, String modulesStr) {
        JSONArray modules = JSONObject.parseArray(modulesStr);
        List<ScOperationPrinterCustomComponent> customComponentList = new ArrayList<>(100);
        for (Object item : modules) {
            JSONArray rows = ((JSONObject) item).getJSONArray("rows");
            for (Object row : rows) {
                JSONArray components = ((JSONObject) row).getJSONArray("components");
                JSONObject o = (JSONObject) components.get(0);

                String componentStr = o.getString("component");
                if (StringUtils.isBlank(componentStr)) {
                    for (Object component : components) {
                        convertCustomComponent(templateId, JSONObject.toJSONString(component), customComponentList);
                    }
                    continue;
                } else {
                    convertCustomComponent(templateId, componentStr, customComponentList);
                }
                String tableTitleStr = o.getString("tableTitle");
                if (StringUtils.isNotBlank(tableTitleStr)) {
                    convertCustomComponent(templateId, tableTitleStr, customComponentList);
                }
                String categoryStr = o.getString("category");
                if (StringUtils.isNotBlank(categoryStr)) {
                    convertCustomComponent(templateId, categoryStr, customComponentList);
                }
                String theadStr = o.getString("thead");
                if (StringUtils.isNotBlank(theadStr)) {
                    JSONObject thead = JSON.parseObject(theadStr);
                    JSONObject tableRowSet = (JSONObject) thead.get("tableRowSet");
                    JSONArray columnSet = tableRowSet.getJSONArray("columnSet");
                    for (Object columnItem : columnSet) {
                        convertCustomComponent(templateId, JSONObject.toJSONString(columnItem), customComponentList);
                    }
                }
                String tbodyStr = o.getString("tbody");
                if (StringUtils.isNotBlank(tbodyStr)) {
                    JSONObject tbody = JSON.parseObject(tbodyStr);
                    JSONArray tableRowSet = tbody.getJSONArray("tableRowSet");
                    for (Object tableRowSetItem : tableRowSet) {
                        JSONArray columnSet = ((JSONObject) tableRowSetItem).getJSONArray("columnSet");
                        for (Object columnSetItem : columnSet) {
                            convertCustomComponent(templateId, JSONObject.toJSONString(columnSetItem), customComponentList);
                        }
                    }
                }
            }
        }
        scShopPrinterCustomComponentService.saveMultiplePrinterCustomComponent(customComponentList);
    }

    /**
     * 转换模板自定义组件
     * @param templateId   模板id
     * @param componentStr 系统组件
     * @param customComponentList 自定义组件列表
     */
    private void convertCustomComponent (Integer templateId, String componentStr, List<ScOperationPrinterCustomComponent> customComponentList) {
        ScOperationPrinterSystemComponent templateComponent = JSONObject.parseObject(componentStr, ScOperationPrinterSystemComponent.class);
        ScOperationPrinterCustomComponent customComponent = new ScOperationPrinterCustomComponent();
        customComponent.setDocumentTemplateId(templateId);
        customComponent.setSystemComponentId(templateComponent.getId());
        customComponent.setValueStyle(templateComponent.getValueStyle());
        customComponentList.add(customComponent);
    }

    /**
     * 更新模板
     * @param param {@link Param}
     * @return {@link Result}
     */
    @PostMapping("update")
    public Result update (@RequestAttribute("param") Param param) {
        UpdateCustomTemplateDocumentForm documentForm = JSON.parseObject(param.getData().toString(), UpdateCustomTemplateDocumentForm.class);
        Result result = documentForm.validateParam();
        if (Result.RESULT_FAILURE.equals(result.getCode())) {
            return result;
        }
        ScOperationPrinterTemplateDocument oldTemplateDocumentInfo = scShopPrinterTemplateDocumentService.getShopPrinterTemplateDocumentByPrimaryId(documentForm.getTemplateId());
        if (oldTemplateDocumentInfo == null || !documentForm.getShopId().equals(oldTemplateDocumentInfo.getShopId()) || Integer.valueOf(0).equals(oldTemplateDocumentInfo.getStatus())) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("模板不存在！");
            return result;
        }
        if (Integer.valueOf(0).equals(oldTemplateDocumentInfo.getShopId()) || Integer.valueOf(1).equals(oldTemplateDocumentInfo.getStatus())) {
            result.setCode(Result.RESULT_FAILURE);
            result.setMsg("该模板为默认模板或者使用中，不允许修改！");
            return result;
        }
        // 更新模板信息
        oldTemplateDocumentInfo.setName(documentForm.getName());
        oldTemplateDocumentInfo.setUrl(documentForm.getUrl());
        scShopPrinterTemplateDocumentService.updateShopPrinterTemplateDocumentByPrimaryId(oldTemplateDocumentInfo);
        // 更新模板组件
        scShopPrinterCustomComponentService.removeMultiplePrinterCustomComponentByTemplateId(documentForm.getTemplateId());
        handleCustomTemplateComponent(documentForm.getTemplateId(), documentForm.getModules());
        result.setMsg("保存成功");
        return result;
    }

    /**
     * 应用模板
     * @param param {@link Param}
     *          shopId：店铺id
     *          templateId：模板id
     *          documentType：票据类型
     * @return {@link Result}
     */
    @PostMapping("enable")
    public Result enable (@RequestAttribute("param") Param param) {
        Result result = new Result(Result.RESULT_FAILURE);
        JSONObject jsonObject = JSON.parseObject(param.getData().toString());
        Integer shopId = jsonObject.getInteger("shopId");
        Integer templateId = jsonObject.getInteger("templateId");
        Integer documentType = jsonObject.getInteger("documentType");
        if (shopId == null || templateId == null || documentType == null) {
            result.setMsg("缺少参数！");
            return result;
        }
        ScOperationPrinterTemplateDocument templateDocument = scShopPrinterTemplateDocumentService.getShopPrinterTemplateDocumentByPrimaryId(templateId);
        if (templateDocument == null || !shopId.equals(templateDocument.getShopId())) {
            result.setMsg("该模板为默认模板或者模板不存在！");
            return result;
        }
        scShopPrinterTemplateDocumentService.enableShopPrinterTemplate(shopId, documentType, templateId);
        result.setCode(Result.RESULT_SUCCESS);
        result.setMsg("操作成功");
        return result;
    }

    /**
     * 删除模板
     * @param param {@link Param}
     *          shopId：店铺id
     *          templateId：模板id
     * @return {@link Result}
     */
    @PostMapping("remove")
    public Result remove (@RequestAttribute("param") Param param) {
        Result result = new Result(Result.RESULT_FAILURE);
        JSONObject jsonObject = JSON.parseObject(param.getData().toString());
        Integer shopId = jsonObject.getInteger("shopId");
        Integer templateId = jsonObject.getInteger("templateId");
        if (shopId == null || templateId == null) {
            result.setMsg("缺少参数！");
            return result;
        }
        ScOperationPrinterTemplateDocument templateDocumentInfo = scShopPrinterTemplateDocumentService.getShopPrinterTemplateDocumentByPrimaryId(templateId);
        if (templateDocumentInfo == null || Integer.valueOf(0).equals(templateDocumentInfo.getStatus()) || !shopId.equals(templateDocumentInfo.getShopId())) {
            result.setMsg("该模板为默认模板或者模板不存在！");
            return result;
        }
        if (Integer.valueOf(0).equals(templateDocumentInfo.getShopId()) || Integer.valueOf(1).equals(templateDocumentInfo.getStatus())) {
            result.setMsg("该模板为默认模板或者使用中，不允许删除！");
            return result;
        }
        scShopPrinterTemplateDocumentService.removeShopPrinterTemplateDocumentByPrimaryId(templateId);
        scShopPrinterCustomComponentService.removeMultiplePrinterCustomComponentByTemplateId(templateId);
        result.setCode(Result.RESULT_SUCCESS);
        result.setMsg("删除成功");
        return result;
    }
}
