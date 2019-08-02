package com.maxzuo.mockito;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * 使用注解来快速模拟对象
 * <p>
 * Created by zfh on 2019/08/01
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoAnnotationExample {

    @Mock
    private List mockList;

    /**
     * 初始化，或者使用 @RunWith(MockitoJUnitRunner.class)
     */
    // public MockitoAnnotationExample () {
    //     MockitoAnnotations.initMocks(this);
    // }

    @Test
    public void shorthand () {
        mockList.add("hello");
        verify(mockList).add("hello");
    }

    /**
     * 连续调用
     */
    @Test(expected = RuntimeException.class)
    public void consecutiveCalls () {
        //模拟连续调用返回期望值，如果分开，则只有最后一个有效
        when(mockList.get(0)).thenReturn(0);
        when(mockList.get(0)).thenReturn(1);
        when(mockList.get(0)).thenReturn(2);
        Assert.assertEquals(2,mockList.get(0));

        when(mockList.get(1)).thenReturn(0).thenReturn(1).thenThrow(new RuntimeException());
        Assert.assertEquals(0,mockList.get(1));
        Assert.assertEquals(1,mockList.get(1));
        // 第三次或更多调用都会抛出异常
        mockList.get(1);
    }

    /**
     * 使用回调生成期望值
     */
    @Test
    public void answerWithCallback () {
        // 使用Answer来生成我们我们期望的返回
        when(mockList.get(anyInt())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return "hello:" + args[0];
            }
        });
        Assert.assertEquals("hello:0", mockList.get(0));
        Assert.assertEquals("hello:999", mockList.get(999));

        // mock对象使用Answer来对未预设的调用返回默认期望值
        List mock = mock(List.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return 999;
            }
        });
        // 下面的get(1)没有预设，通常情况下会返回NULL，但是使用了Answer改变了默认期望值
        Assert.assertEquals(999, mock.get(1));
        // 下面的size()没有预设，通常情况下会返回0，但是使用了Answer改变了默认期望值
        Assert.assertEquals(999, mock.size());
    }

    /**
     * 监控真实对象
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void spyOnRealObjects () {
        List list = new LinkedList();
        List spy = spy(list);
        //下面预设的spy.get(0)会报错，因为会调用真实对象的get(0)，所以会抛出越界异常
        //when(spy.get(0)).thenReturn(3);

        //使用doReturn-when可以避免when-thenReturn调用真实对象api
        doReturn(999).when(spy).get(999);
        //预设size()期望值
        when(spy.size()).thenReturn(100);
        //调用真实对象的api
        spy.add(1);
        spy.add(2);
        Assert.assertEquals(100,spy.size());
        Assert.assertEquals(1,spy.get(0));
        Assert.assertEquals(2,spy.get(1));
        verify(spy).add(1);
        verify(spy).add(2);
        Assert.assertEquals(999,spy.get(999));
        spy.get(2);
    }

    /**
     * 真实的部分mock
     */
    @Test
    public void realPartialMock () {
        // 通过spy来调用真实的api
        List<String> spy = spy(new ArrayList<>(10));
        Assert.assertEquals(0, spy.size());

        A mock = mock(A.class);
        // 通过thenCallRealMethod来调用真实的api
        when(mock.doSomething(anyInt())).thenCallRealMethod();
        Assert.assertEquals(999, mock.doSomething(999));
    }

    class A {
        public int doSomething(int i){
            return i;
        }
    }

    /**
     * 重置mock
     */
    @Test
    public void resetMock () {
        List list = mock(List.class);
        when(list.size()).thenReturn(10);
        list.add(1);
        Assert.assertEquals(10,list.size());
        //重置mock，清除所有的互动和预设
        reset(list);
        Assert.assertEquals(0,list.size());
    }
}
