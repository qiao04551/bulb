package com.maxzuo.printtemplate.vo;

/**
 * 打印文档类型 视图VO
 * Created by zfh on 2018/12/12
 */
public class PrinterDocumentTypeVO {

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PrinterDocumentTypeVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
