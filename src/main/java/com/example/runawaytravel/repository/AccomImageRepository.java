package com.example.runawaytravel.repository;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.AccomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccomImageRepository extends JpaRepository<AccomImage, Integer> {
    @Query("select t from AccomImage t join fetch t.accom a where a.accomNum = :accomNum")
    public List<AccomImage> oneacc(int accomNum);

    AccomImage findTopByAccom_AccomNum(int accomNum);
}