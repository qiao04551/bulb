package com.maxzuo.state.example;

/**
 * 黑名单
 *
 * Created by zfh on 2019/11/07
 */
public class BlackVoteState implements VoteState {

    @Override
    public void vote(String user, String voteItem, VoteManager voteManager) {
        // 记录黑名单中，禁止登录系统
        System.out.println("进入黑名单，将禁止登录和使用本系统");
    }
}
