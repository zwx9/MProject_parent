package com.zwx.base.dao;

import com.zwx.base.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String>, JpaSpecificationExecutor<Friend> {

    //查找是否是好友(1:是好友     2:不是好友)
    @Query(nativeQuery = true,value = "select count(*) from tb_friend where user_id = ? and friend_id = ?")
    public int selectIsFriend(String userId,String friendId);

    //查找本人与该朋友星标为1的行数
    @Query(nativeQuery = true,value = "select count(*) from tb_friend where user_id = ? and friend_id = ? and is_star = 1")
    public int selectIsStarByFriendId(String userId,String friendId);

    //更新关注状态
    @Modifying
    @Query(nativeQuery = true,value = "update tb_friend set is_star = ? where user_id = ? and friend_id = ?")
    public int updateIsStar(String isStar,String userId,String friendId);

    //删除好友
    @Modifying
    @Query(nativeQuery = true,value = "delete from tb_friend where user_id = ? and friend_id = ?")
    public int deleteFriendByFriendId(String userId,String friendId);


}
