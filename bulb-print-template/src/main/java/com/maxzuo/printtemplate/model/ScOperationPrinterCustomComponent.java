package com.maxzuo.printtemplate.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 打印模板-自定义模板组件
 * Created by zfh on 2018/12/27
 */
public class ScOperationPrinterCustomComponent implements Serializable {

    private static final long serialVersionUID = -5596577451287093325L;

    /** 主键 */
    private Integer id;

    /** 模板id */
    private Integer documentTemplateId;

    /** 系统组件id */
    private Integer systemComponentId;

    /** 自定义组件样式 */
    private String valueStyle;

    /** 模块id */
    private Integer moduleId;

    /** 父组件id */
    private Integer parentId;

    /** 引用id */
    private Integer refId;

    /** 组件名称 */
    private String label;

    /** 组件值 */
    private String value;

    /** 占位符 */
    private String placeholder;

    /** 组件类型 */
    private String type;

    /** 行号 */
    private Integer row;

    /** 列号 */
    private Integer column;

    /** 宽度，百分比 */
    private Integer width;

    /** 序号 */
    private Integer sort;

    /** 启用状态：0 未启用 1已启用 */
    private Integer enable;

    /** 是否删除：0-未删除 1-删除 */
    private Integer delete;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDocumentTemplateId() {
        return documentTemplateId;
    }

    public void setDocumentTemplateId(Integer documentTemplateId) {
        this.documentTemplateId = documentTemplateId;
    }

    public Integer getSystemComponentId() {
        return systemComponentId;
    }

    public void setSystemComponentId(Integer systemComponentId) {
        this.systemComponentId = systemComponentId;
    }

    public String getValueStyle() {
        return valueStyle;
    }

    public void setValueStyle(String valueStyle) {
        this.valueStyle = valueStyle;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ScOperationPrinterCustomComponent{" +
                "id=" + id +
                ", documentTemplateId=" + documentTemplateId +
                ", systemComponentId=" + systemComponentId +
                ", valueStyle='" + valueStyle + '\'' +
                ", moduleId=" + moduleId +
                ", parentId=" + parentId +
                ", refId=" + refId +
                ", label='" + label + '\'' +
                ", value='" + value + '\'' +
                ", placeholder='" + placeholder + '\'' +
                ", type='" + type + '\'' +
                ", row=" + row +
                ", column=" + column +
                ", width=" + width +
                ", sort=" + sort +
                ", enable=" + enable +
                ", delete=" + delete +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
