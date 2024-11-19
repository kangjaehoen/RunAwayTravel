package com.example.runawaytravel.repository;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.Dayoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DayoffRepository extends JpaRepository<Dayoff, Integer> {
    @Query("select t from Dayoff t" +
            " join fetch t.accom a" +
            " join fetch t.accom.user" +
            " where a.accomNum = :accomNum")
    public List<Dayoff> oneacc(int accomNum);
    @Query("select t from Dayoff t join fetch t.accom a where a.accomNum in :accomNum")
    public List<Dayoff> manyacc(List<Integer> accomNum);
}
