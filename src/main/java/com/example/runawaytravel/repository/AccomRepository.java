package com.example.runawaytravel.repository;

import com.example.runawaytravel.entity.Accom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccomRepository extends JpaRepository<Accom, Integer> {
    //리스트불러오기:조건
    @Query("select t from Accom t where t.username.username = :username and (t.accName like concat('%', :key, '%') or t.address like concat('%', :key, '%') or t.detailAddress like concat('%', :key, '%') or t.category like concat('%', :key, '%') or t.accType like concat('%', :key, '%')) ")
    public Page<Accom> searchmine(String username, String key, PageRequest pageRequest);
    @Query("select count(t) from Accom t where t.username.username = :username and (t.accName like concat('%', :key, '%') or t.address like concat('%', :key, '%') or t.detailAddress like concat('%', :key, '%') or t.category like concat('%', :key, '%') or t.accType like concat('%', :key, '%'))")
    long countmylist(String username,String key);
    //판매중리스트불러오기
    @Query("select t from Accom t where t.username.username = :username and t.onSale =1")
    public  Page<Accom> searchmineonsale(String username, PageRequest pageRequest);
    @Query("select count(t) from Accom t where t.username.username = :username and t.onSale=1")
    long countmyonsalelist(String username);
}
