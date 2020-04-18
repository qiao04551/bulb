package com.maxzuo.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * 使用Dom4j 解析XML文件
 * Created by zfh on 2019/07/02
 */
public class Dom4jParseXmlExample {

    public static void main(String[] args) {
        SAXReader reader = new SAXReader();
        URL resource = Dom4jParseXmlExample.class.getResource("/xml-xsd/custom-xsd-impl.xml");
        try {
            // 读取文档
            Document document = reader.read(resource.getFile());
            // 取得root节点
            Element rootElement = document.getRootElement();
            // 遍历XML树
            Iterator iterator = rootElement.elementIterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                System.out.println("name: " + element.getQName().getName());
                System.out.println("value: " + element.getText());
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
