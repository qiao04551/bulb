package com.maxzuo.elastic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.get.GetResponse;
import org.junit.Assert;
import org.junit.Test;

/**
 * ES客户端基本使用
 * Created by zfh on 2019/03/11
 */
public class ESClientTest {

    @Test
    public void queryByPrimaryKey() {
        GetResponse response = ElasticSearchUtils.getRecordByPrimaryKey("zxcity_elk", "file", "AWlq3_I-P9zaf8kQeL0a");
        System.out.println("result：" + response.getSource());
    }

    @Test
    public void saveRecord() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "dazuo");
        jsonObject.put("profession", "java工程师");
        Boolean aBoolean = ElasticSearchUtils.saveRecord("zxcity_test", "file", JSON.toJSONString(jsonObject));
        Assert.assertTrue(aBoolean);
    }
}
