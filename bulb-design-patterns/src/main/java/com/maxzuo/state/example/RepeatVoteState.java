package com.maxzuo.state.example;

/**
 * 重复投票
 *
 * Created by zfh on 2019/11/07
 */
public class RepeatVoteState implements VoteState {

    @Override
    public void vote(String user, String voteItem, VoteManager voteManager) {
        // 重复投票，暂时不做处理
        System.out.println("请不要重复投票");
    }
}
