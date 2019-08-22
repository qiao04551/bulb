package com.maxzuo.graphql.vo;

import lombok.Data;

/**
 * 日记 视图对象
 * <p>
 * Created by zfh on 2019/08/19
 */
@Data
public class DiaryVO {

    /**
     * 名片ID
     */
    private Integer cardId;

    /**
     * 日记ID
     */
    private Integer diaryId;

    /**
     * 总结
     */
    private String summary;

    /**
     * 富文本内容
     */
    private String richText;

    /**
     * 发布时间
     */
    private String publishTime;
}
