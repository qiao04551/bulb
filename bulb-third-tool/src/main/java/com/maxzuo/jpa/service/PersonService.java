package com.maxzuo.jpa.service;

import com.maxzuo.jpa.entity.Person;
import com.maxzuo.jpa.repositorys.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Person服务层
 * <p>
 * Created by zfh on 2019/04/21
 */
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    /**
     * 通过ID更新用户信息
     */
    @Transactional
    public void updateByPrimaryKey (Integer id, String username) {
        personRepository.updatePerson(id, username);
    }

    @Transactional
    public void savePerson(Person person) {
        Person result = personRepository.save(person);
        System.out.println("result: " + result);
    }
}
