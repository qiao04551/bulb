package com.maxzuo.chain;

/**
 * 责任链模式（Chain of Responsibility Pattern）
 *
 * <p>使多个对象都有处理请求的机会，从而避免了请求的发送者和接收者之间的耦合关系。将这些对象串成一条链，
 * 并沿着这条链一直传递该请求，直到有对象处理它为止。
 *
 * <p>角色：
 * - 抽象处理者（Handler）：该角色对请求进行抽象，并定义一个方法来设定和返回对下一个处理者的引用。
 * - 具体处理者（Concrete Handler）：该角色接到请求后，可以选择将请求处理掉，或者将请求传给下一个处理者。
 *
 * <p>优点：
 * - 责任链模式将请求和处理分开，请求者不知道是谁处理的，处理者可以不用知道请求的全貌。
 * - 提高系统的灵活性。
 *
 * <p>应用场景：
 * - 一个请求需要一系列的处理工作。
 * - 业务流的处理，例如文件审批。
 * - 对系统进行扩展补充。
 *
 * Created by zfh on 2019/11/13
 */
public class ChainOfResponsibilityExample {

    public static void main(String[] args) {
        Handler handler1 = new ConcreteHandler();
        Handler handler2 = new ConcreteHandler();
        handler1.setNextHandler(handler2);
        handler1.handleRequest();
    }
}
