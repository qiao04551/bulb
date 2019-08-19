package com.maxzuo.graphql.vo;

/**
 * 日记 视图对象
 * <p>
 * Created by zfh on 2019/08/19
 */
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

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(Integer diaryId) {
        this.diaryId = diaryId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRichText() {
        return richText;
    }

    public void setRichText(String richText) {
        this.richText = richText;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "DiaryVO{" +
                "cardId=" + cardId +
                ", diaryId=" + diaryId +
                ", summary='" + summary + '\'' +
                ", richText='" + richText + '\'' +
                ", publishTime='" + publishTime + '\'' +
                '}';
    }
}
