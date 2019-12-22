package com.maxzuo.sofa;

import org.springframework.stereotype.Service;

/**
 * Created by zfh on 2019/12/22
 */
@Service("scMemberService")
public class ScMemberServiceImpl implements IScMemberService {

    @Override
    public String sayName(String name, Integer age) {
        System.out.println("IScMemberService.sayName被调用：name = " + name + " age = " + age);
        return name;
    }

    @Override
    public String info() {
        System.out.println("IScMemberService.info被调用");
        return "helle print";
    }
}
