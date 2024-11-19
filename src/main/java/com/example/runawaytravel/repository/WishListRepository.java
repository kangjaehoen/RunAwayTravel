package com.example.runawaytravel.repository;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.User;
import com.example.runawaytravel.entity.WishList;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList,Integer> {
    public List<WishList> findByUserName(User user);

    @Query( " SELECT w.checkStatus " +
            " FROM WishList w " +
            " WHERE w.accomNum = :accomNum")
    public int findCheckWishList(@Param("accomNum") Accom accomNum);

    public int  deleteByUserNameAndAccomNum(User user, Accom accom);



}
