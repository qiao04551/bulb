package com.maxzuo.graphql.vo;

import lombok.Data;

/**
 * 浏览的名片 视图对象
 * <p>
 * Created by zfh on 2019/08/19
 */
@Data
public class BrowsingHistoryCardVO {

    /**
     * 名片ID
     */
    private Integer cradId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 姓名
     */
    private String name;

    /**
     * 姓名全拼
     */
    private String namePinyin;

    /**
     * 职位
     */
    private String position;

    /**
     * 公司
     */
    private String company;

    /**
     * 浏览时间，格式：yyyy-MM-dd HH:mm:ss
     */
    private String viewTime;
}
