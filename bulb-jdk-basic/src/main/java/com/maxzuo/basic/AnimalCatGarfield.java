package com.maxzuo.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * List<T> 只能放置一种类型，如果随意转换类型的话，就是“破窗理论”，泛型失去泛型安全的意义。
 * 如果需要放置多种受泛型约束的类型呢？ JDK的开发者，实现了<? extends T>和<? super T>两种语法
 *
 * <? extends T> 是Get First，适用于消费集合为主的场景；<? super T> 是Put First，适用于，生产集合为主的场景
 * <pre>
 *   1. <? extends T> 可以赋值给任何 T 及 T 子类的集合，上界 T，取出的类型带有泛型限制，向上强制转型为 T。null可以表示任何类型，
 *      所以除 null 外，任何元素都不得添加进 <? extends T> 集合内。
 *   2. <? super T> 可以赋值给任何 T 及 T 的父类集合，下界为 T。在生活中投票选举类似于<? super T> 的操作。选举代表时，
 *      可以往里面投票，取数据时，根本不知道是谁的票，相当于泛型丢失。
 *   3. extends 场景是put功能受限，而 super 的场景是get功能受限。
 * </pre>
 *
 * Created by zfh on 2019/04/28
 */
public class AnimalCatGarfield {

    public static void main(String[] args) {
        // 第一段：继承关系
        List<Animal> animal = new ArrayList<>(10);
        List<Cat> cat = new ArrayList<>(10);
        List<Garfield> garfield = new ArrayList<>(10);

        animal.add(new Animal());
        cat.add(new Cat());
        garfield.add(new Garfield());

        // 第二段：测试赋值操作
        // 下行编译报错，只能赋值 Cat及Cat子类的 集合引用
        //List<? extends Cat> extendsCatFormAnimal = animal;
        List<? super Cat> superCatFormAnimal = animal;

        List<? extends Cat> extendsCatFormCat = cat;
        List<? super Cat> superCatFormCat = cat;

        List<? extends Cat> extendsCatFormGarField = garfield;
        // 下行编译报错，只能赋值 Cat及Cat父类的“集合引用”
        //List<? super Cat> superCatFormGarfield = garfield;

        // 第三段：测试add方法
        // 所有的 <? extends T> 都无法进行add操作
        //extendsCatFormCat.add(new Animal());
        //extendsCatFormCat.add(new Cat());
        //extendsCatFormCat.add(new Garfield());

        // 下行编译报错，只能添加Cat或Cat子类的集合
        //superCatFormCat.add(new Animal());
        superCatFormCat.add(new Cat());
        superCatFormCat.add(new Garfield());

        // 第四段：测试get方法
        // 所有的 <? super T> 操作能够放回元素，但是泛型丢失，只能返回Object对象

        // <? extends Cat>可以返回带类型的元素，但只能返回Cat自身及其父类对象，因为子类泛型给擦除了
        Object cat0 = extendsCatFormCat.get(0);
        Cat cat1 = extendsCatFormCat.get(0);
        Cat cat2 = extendsCatFormGarField.get(0);
        // 下行编译出错。虽然Cat集合从Carfield赋值而来，但类型擦除后，是不知道的
        //Garfield cat3 = extendsCatFormGarField.get(0);
    }
}

class Animal {

}

class Cat extends Animal {

}

class Garfield extends Cat {

}