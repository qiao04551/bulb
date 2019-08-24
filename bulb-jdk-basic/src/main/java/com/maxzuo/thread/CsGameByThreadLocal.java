package com.maxzuo.thread;

import java.util.concurrent.ThreadLocalRandom;

/**
 * CS杀敌游戏（ThreadLocal）
 * Created by zfh on 2019/01/23
 */
public class CsGameByThreadLocal {

    /** 子弹数 */
    private static final Integer              BULLEF_NUMBER              = 1500;

    /** 杀敌数 */
    private static final Integer              KILLED_ENEMIES             = 0;

    /** 生命数 */
    private static final Integer              LIFE_VALUES                = 10;

    /** 玩家数 */
    private static final Integer              TOTAL_PLAYERS              = 10;

    /**
     * 使用ThreadLocalRandom生成单独Random实例。此类在JDK7中引入，它使得每个线程都可以有自己的随机数生成器。
     * 我们要避免Random实例被多线程使用，虽然共享该实例是线程安全的，但会因竞争同一个seed而导致性能下降。
     * 注意：ThreadLocalRandom在每个线程第n次随机数都一样。
     */
    private static final ThreadLocalRandom    RANDOM                     = ThreadLocalRandom.current();

    private static final ThreadLocal<Integer> BULLET_NUMBER_THREADLOCAL  = new ThreadLocal<Integer>() {
                                                                             @Override
                                                                             protected Integer initialValue() {
                                                                                 return BULLEF_NUMBER;
                                                                             }
                                                                         };

    private static final ThreadLocal<Integer> KILLED_ENEMIES_THREADLOCAL = new ThreadLocal<Integer>() {
                                                                             @Override
                                                                             protected Integer initialValue() {
                                                                                 return KILLED_ENEMIES;
                                                                             }
                                                                         };

    private static final ThreadLocal<Integer> LIFE_VALUES_THREADLOCAL    = new ThreadLocal<Integer>() {
                                                                             @Override
                                                                             protected Integer initialValue() {
                                                                                 return LIFE_VALUES;
                                                                             }
                                                                         };

    private static class Player extends Thread {
        @Override
        public void run() {
            Integer bullets = BULLET_NUMBER_THREADLOCAL.get() - RANDOM.nextInt(BULLEF_NUMBER);
            Integer killEnemies = KILLED_ENEMIES_THREADLOCAL.get() + RANDOM.nextInt(TOTAL_PLAYERS / 2);
            Integer lifeValues = LIFE_VALUES_THREADLOCAL.get() - RANDOM.nextInt(LIFE_VALUES);

            System.out.println(getName() + " bullets = " + bullets);
            System.out.println(getName() + " killEnemies = " + killEnemies);
            System.out.println(getName() + " lifeValues = " + lifeValues);

            BULLET_NUMBER_THREADLOCAL.remove();
            KILLED_ENEMIES_THREADLOCAL.remove();
            LIFE_VALUES_THREADLOCAL.remove();
        }
    }

    /**
     * 每个玩家初始持有相同子弹数、杀敌数、生命数；经过一场游戏过后各自的剩余子弹数、杀敌数、剩余生命数 互不影响。
     */
    public static void main(String[] args) {
        for (int i = 0; i < TOTAL_PLAYERS; i++) {
            new Player().start();
        }
    }
}
