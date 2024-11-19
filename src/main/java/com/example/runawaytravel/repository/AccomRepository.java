package com.example.runawaytravel.repository;

import com.example.runawaytravel.entity.Accom;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccomRepository extends JpaRepository<Accom, Integer> {
    //리스트불러오기:조건
    @Query("select t from Accom t join fetch t.user u where u.username = :username and t.onSale < 2 and" +
            "(t.accName like concat('%', :key, '%') or " +
            "t.address like concat('%', :key, '%') or " +
            "t.detailAddress like concat('%', :key, '%') or " +
            "t.category like concat('%', :key, '%') or " +
            "t.accType like concat('%', :key, '%'))")
    public Page<Accom> searchmine(@Param("username") String username,
                                  @Param("key") String key,
                                  Pageable pageRequest);
    //판매중리스트불러오기
    @Query("select t from Accom t where t.user.username = :username and t.onSale =1")
    public  Page<Accom> searchmineonsale(String username, PageRequest pageRequest);
    //숙소 한개 정보 가지고 오기
    @Query("select t from Accom t join fetch t.user where t.accomNum = :accomNum")
    public Accom oneacc(int accomNum);
    //숙소 여러개 정보 가지고 오기
    @Query("select t from Accom t join fetch t.user where t.accomNum in :accomNum")
    public List<Accom> manyacc(List<Integer> accomNum);
}
