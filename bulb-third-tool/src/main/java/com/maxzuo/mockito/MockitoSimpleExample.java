package com.maxzuo.mockito;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Mockito 的简单使用
 * <p>
 * Created by zfh on 2019/08/01
 */
public class MockitoSimpleExample {

    /**
     * 验证行为发生
     */
    @Test
    public void verifyBehaviour() {
        List mock = mock(List.class);
        mock.add(1);
        mock.clear();

        // 验证 add(1)和clear()行为是否发生
        verify(mock).add(1);
        verify(mock).clear();
    }

    /**
     * 模拟期望结果
     */
    @Test
    public void mockThenReturn () {
        Iterator mock = mock(Iterator.class);
        // 预设第一次返回hello，第n此都返回world
        when(mock.next()).thenReturn("hello").thenReturn("world");

        Assert.assertEquals("helloworldworld",  "" + mock.next() + mock.next() + mock.next());
    }

    /**
     * 参数匹配
     */
    @Test
    public void withArguments () {
        Comparable comparable = mock(Comparable.class);
        // 预设不同的参数返回不同的结果
        when(comparable.compareTo("hello")).thenReturn(1);
        when(comparable.compareTo("world")).thenReturn(2);

        Assert.assertEquals(1, comparable.compareTo("hello"));
        Assert.assertEquals(2, comparable.compareTo("world"));

        // 没有预设的情况会返回默认值
        Assert.assertEquals(0, comparable.compareTo("dazuo"));
    }

    /**
     * 验证确切的调用次数
     */
    @Test
    public void verifyingNumberOfInvocations () {
        List mock = mock(List.class);
        mock.add(1);
        mock.add(2);
        mock.add(2);
        mock.add(3);
        mock.add(3);

        // 验证是否调用了一次
        verify(mock, times(1)).add(1);
        // 验证是否调用了2次
        verify(mock, times(2)).add(2);
        // 验证是否调用了2次
        verify(mock, times(2)).add(3);
        // 从未调用过
        verify(mock, never()).add(4);
        // 最少调用了一次
        verify(mock, atLeastOnce()).add(1);
        // 最多调用了2次
        verify(mock, atMost(2)).add(2);
    }

    /**
     * 模拟方法体抛出异常
     */
    @Test(expected = RuntimeException.class)
    public void doThrowWhen () {
        List mock = mock(List.class);
        // 预设：add(1)的时候抛出异常
        doThrow(new RuntimeException()).when(mock).add(1);

        mock.add(1);
    }

    /**
     * 验证执行顺序
     */
    @Test
    public void verificationInOrder () {
        List mock = mock(List.class);
        mock.add(1);
        mock.add("hello");
        mock.add(23);

        InOrder inOrder = inOrder(mock);
        inOrder.verify(mock).add(1);
        inOrder.verify(mock).add("hello");
        inOrder.verify(mock).add(23);
    }

    /**
     * 模拟对象上无互动发生
     */
    @Test
    public void verifyInteraction () {
        List mock = mock(List.class);
        // 检测add(1)互动行为
        verify(mock, never()).add(1);

        // 检测0互动行为
        verifyZeroInteractions(mock);
    }

    /**
     * 找出未被验证到的互动行为
     */
    @Test
    public void findRedundantInteraction () {
        List list = mock(List.class);
        list.add(1);
        list.add(2);
        verify(list,times(2)).add(anyInt());
        //检查是否有未被验证的互动行为，因为add(1)和add(2)都会被上面的anyInt()验证到，所以下面的代码会通过
        verifyNoMoreInteractions(list);

        List list2 = mock(List.class);
        list2.add(1);
        list2.add(2);
        verify(list2).add(1);
        //检查是否有未被验证的互动行为，因为add(2)没有被验证，所以下面的代码会失败抛出异常
        verifyNoMoreInteractions(list2);
    }
}
