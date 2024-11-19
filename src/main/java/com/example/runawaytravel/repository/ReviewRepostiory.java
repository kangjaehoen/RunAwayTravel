package com.example.runawaytravel.repository;

import com.example.runawaytravel.dto.ReviewRatingDTO;
import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.Review;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepostiory extends JpaRepository<Review,Integer> {
    public Page<Review> findByAccomNum(Accom accomNum, PageRequest pageRequest);

    @Query(" SELECT new com.example.runawaytravel.dto.ReviewRatingDTO( " +
            " COUNT(r) AS reviewCount, " +
            " COUNT(CASE WHEN r.satisfy = 5 THEN 1 END) AS count5, " +
            " COUNT(CASE WHEN r.satisfy = 4 THEN 1 END) AS count4, " +
            " COUNT(CASE WHEN r.satisfy = 3 THEN 1 END) AS count3, " +
            " COUNT(CASE WHEN r.satisfy = 2 THEN 1 END) AS count2, " +
            " COUNT(CASE WHEN r.satisfy = 1 THEN 1 END) AS count1, " +
            " ROUND(AVG(r.satisfy),1) AS satisAvg, " +
            " ROUND(AVG(r.accuracy),1) AS accuracyAvg, " +
            " ROUND(AVG(r.clean),1) AS cleanAvg, " +
            " ROUND(AVG(r.scp),1) AS scpAvg ) " +
            " FROM Review r " +
            " JOIN r.accomNum a " +
            " WHERE a.accomNum = :accomNum ")
    public ReviewRatingDTO rating(@Param("accomNum") int accomNum);

    public Page<Review> findByAccomNumAndRevContentContaining(Accom accomNum, String search,PageRequest pageRequest);
}
