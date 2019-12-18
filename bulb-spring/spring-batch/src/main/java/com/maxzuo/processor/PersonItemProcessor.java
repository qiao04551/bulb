package com.maxzuo.processor;

import com.maxzuo.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * 单条记录处理器，对数据进行处理，如数据清洗、转换、过滤、校验等
 * <p>
 * Created by zfh on 2019/12/17
 */
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    private static final Logger logger = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(Person person) throws Exception {
        logger.info("源数据：" + person);
        return new Person(person.getFirstName().toUpperCase(), person.getLastName().toUpperCase());
    }
}
