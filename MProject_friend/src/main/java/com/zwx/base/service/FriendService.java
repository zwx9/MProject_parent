package com.zwx.base.service;

import com.zwx.base.client.UserClient;
import com.zwx.base.dao.BlackListDao;
import com.zwx.base.dao.FriendDao;
import com.zwx.base.entity.BlackList;
import com.zwx.base.entity.Friend;
import com.zwx.base.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service

public class FriendService {
    @Autowired
    private FriendDao friendDao;

    @Autowired
    private BlackListDao blackListDao;

    @Autowired
    private UserClient userClient;



    /**
     * 添加好友
     *      0:不能本人添加本人
     *      1：已经是好友
     *      2：添加成功
     */
    @Transactional
    public int addFriend(String userId,String friendId){
        //判断是否是本人
        if(userId.equals(friendId)){
            return 0;
        }
        //查询判断是否已经是好友
        if(friendDao.selectIsFriend(userId,friendId) == 1){
            return 1;
        }
        //以上二者均不成立即可添加成功(将本人id，朋友id，是否星标添加进来)
        Friend friend = new Friend(userId,friendId,  "0") ;
        friendDao.save(friend ) ;
        return 2 ;
    }

    /**
     * 设置关注
     *      0: 添加关注成功！
     *      -1: 出现异常
     *      1: 取消关注！
     *      (添加关注时，先判断是否是对方好友，再关注，不是则请先添加)
     * */
    @Transactional
    public int setIsStar(String userId,String friendId){
        if (friendDao.selectIsFriend(userId,friendId) == 1){

            if (friendDao.selectIsStarByFriendId(userId, friendId) == 1 ){
                friendDao.updateIsStar("0",userId, friendId);
                userClient.updateFansDecrease(friendId);
                return 1;
            }
            if (friendDao.selectIsStarByFriendId(userId, friendId) == 0) {
                friendDao.updateIsStar("1", userId, friendId);
                userClient.updateFansIncrease(friendId);
                return 0;
            }
        }
        return -1;


    }

    /**
     * 设置黑名单:
     *      1.拉进黑名单时首先必须是好友
     *           a.删除之前先判断是否为星标好友，
     *           b.如果是，则在删除时也将对方粉丝减一
     *           c.如果不是，则直接删除即可
     *      2.是好友再放入黑名单(controller中只传入friendid)
     * */
    @Transactional
    public void setFriendBlack(String userId,String friendId){
        BlackList blackList = new BlackList(userId, friendId);
        //判断是否关注，已关注则取消关注
        if (friendDao.selectIsStarByFriendId(userId, friendId)==1){
            //取消关注
            friendDao.updateIsStar("0", userId, friendId);
            //减粉
            userClient.updateFansDecrease(friendId);
        }
        blackListDao.save(blackList);
    }


    /**
     * 移除黑名单:
     *      1.能进黑名单肯定是好友，直接从黑名单中删除即可
     * */
    @Transactional
    public void deleteFromBlack(String userId,String friendId){
        BlackList blackList = new BlackList(userId, friendId);
        blackListDao.delete(blackList);
    }

    /**
     * 删除好友并拉进黑名单:
     *      1.在friend中删除好友
     *      2.在blacklist中将删除好友添加进去(要判断此人是否已存在黑名单中)
     *          0:不在黑名单       1:已在黑名单中
     * */
    @Transactional
    public int deleteAndBlack(String userId,String friendId){
        friendDao.deleteFriendByFriendId(userId, friendId);
        if (blackListDao.selectFriendBlack(userId, friendId) == 1){
            return 1;
        }
        BlackList blackList = new BlackList(userId, friendId);
        blackListDao.save(blackList);
        return 0;
    }

    /**
     * 删除之前先判断是否为星标好友，
     *      如果是，则在删除时也将对方粉丝减一
     *      如果不是，则直接删除即可
     *
     * */

}
