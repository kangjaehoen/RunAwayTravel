package com.example.runawaytravel;

import com.example.runawaytravel.dto.ReviewRatingDTO;
import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.Review;
import com.example.runawaytravel.entity.User;
import com.example.runawaytravel.repository.ReviewRepostiory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class ReviewRepsoitoryTest {
    @Autowired
    private ReviewRepostiory reviewRepostiory;

   @Test
    public void findByAccomNum(){
        Accom accom = new Accom();
        accom.setAccomNum(8);
        PageRequest pageRequest = PageRequest.of(0,4);
        Page<Review> list = reviewRepostiory.findByAccomNum(accom, pageRequest);
        list.stream().forEach(System.out::println);
    }

    @Test
    @Rollback(value = false)
    @Transactional
    public void saveReply(){
        Review review = new Review();

        Accom accom = new Accom();
        accom.setAccomNum(8);

        User user = new User();
        user.setUsername("wogjsWkd");

        LocalDate date = LocalDate.now();
        String now = String.valueOf(date);

        review.setSatisfy(5);
        review.setAccuracy(4);
        review.setClean(5);
        review.setScp(5);
        review.setRevContent("안녕하세요 적당히 바람이 시원해 기분이 너무 좋아요 유후");
        review.setAccomNum(accom);
        review.setUserName(user);
        review.setHiredate(now);

        reviewRepostiory.save(review);
    }

    @Test
    @Rollback(value = false)
    @Transactional
    public void saveUpdateReview(){
        Review review = new Review();
        Accom accom = new Accom();
        accom.setAccomNum(8);

        User user = new User();
        user.setUsername("wogjsWkd");

        LocalDate date = LocalDate.now();
        String now = String.valueOf(date);


        review.setRnum(69);
        review.setSatisfy(4);
        review.setAccuracy(3);
        review.setClean(2);
        review.setScp(5);
        review.setRevContent("안녕하세요 적당히 바람이 시원해 기분이 너무 좋아요 유후");
        review.setAccomNum(accom);
        review.setUserName(user);
        review.setHiredate(now);

        reviewRepostiory.save(review);
    }

    @Test
    public void findAllReview(){
        List<Review> list = reviewRepostiory.findAll();
        list.stream().forEach(System.out::println);
    }

    @Test
    public void rate(){
        System.out.println(reviewRepostiory.rating(75));
    }

/*    @Test
    public void searchList(){
        Accom accom = new Accom();
        accom.setAccomNum(75);

        String search = "하이";

      List<Review> list =  reviewRepostiory.findByAccomNumAndRevContentContaining(accom,"강아지");
      list.stream().forEach(System.out::println);
    }*/
}
