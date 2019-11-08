package com.maxzuo.karate;

import com.intuit.karate.junit4.Karate;
import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Karate 入门示例
 * <p>
 * Created by zfh on 2019/11/08
 */
@RunWith(Karate.class)
@CucumberOptions(features = "classpath:greeting.feature")
public class CatsRunner {

}
