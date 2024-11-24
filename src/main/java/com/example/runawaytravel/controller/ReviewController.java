package com.example.runawaytravel.controller;

import com.example.runawaytravel.dto.ReviewRatingDTO;
import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.Review;
import com.example.runawaytravel.entity.User;
import com.example.runawaytravel.repository.ReviewRepostiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewRepostiory reviewRepostiory;


    @GetMapping
    public ResponseEntity<List<Review>> findAllReview(){
        List<Review> entitys =  reviewRepostiory.findAll();
        System.out.println(entitys);
        return new ResponseEntity<>(entitys, HttpStatus.OK);
    }

    @GetMapping("/{num}")
    public ResponseEntity<HashMap> findByAccomReview(@PathVariable int num,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "4") int size){
        Accom accom = new Accom();
        accom.setAccomNum(num);
        PageRequest pageRequest = PageRequest.of(page ,size);
        Page<Review> reviewPage = reviewRepostiory.findByAccom(accom, pageRequest);// accomNum
        System.out.println(reviewPage);
        List<Review> list = reviewPage.toList();

        HashMap map = new HashMap();
            map.put("list",list);
            map.put("currentPage",page);
            map.put("totalPage",reviewPage.getTotalPages());
            map.put("pageSize",size);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/rate/{accomNum}")
    public ResponseEntity<ReviewRatingDTO> findByAccomReviewRate(@PathVariable int accomNum){
       ReviewRatingDTO dto = reviewRepostiory.rating(accomNum); // accomNum
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

   @PostMapping
       public ResponseEntity<String> saveReview(@RequestBody Map<String,Object>review, Principal principal){
       LocalDate date = LocalDate.now();
       String now = String.valueOf(date);

       System.out.println(principal);
       if (principal == null && principal.getName() == null && principal.getName().trim().isEmpty()) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       }
       String userName = principal.getName();
       System.out.println(userName);

       Accom accom = new Accom();
       String accomNumStr = (String) review.get("accomNum");  // Map에서 가져온 값은 Object 타입이므로, String으로 캐스팅
       int accomNum = Integer.parseInt(accomNumStr);
       accom.setAccomNum(accomNum);

       User user = new User();
       user.setUsername(userName);

       Review review1 = new Review();
       review1.setAccom(accom);
       review1.setUser(user);
       review1.setSatisfy((Integer) review.get("satisfy"));
       review1.setAccuracy((Integer) review.get("accuracy"));
       review1.setClean((Integer) review.get("clean"));
       review1.setScp((Integer) review.get("scp"));
       review1.setRevContent((String) review.get("revContent"));
       review1.setHiredate(now);

       Review entity =  reviewRepostiory.save(review1);
       System.out.println(review.get("accomNum"));
       return new ResponseEntity<>("성공",HttpStatus.OK);
    }


  /*  @PutMapping
    public ResponseEntity<Review> saveUpdateReview(@RequestBody Review review){
        LocalDate date = LocalDate.now();
        String now =  String.valueOf(date);
        review.setHiredate(now);
        Review entity = reviewRepostiory.save(review);
        return new ResponseEntity<>(entity,HttpStatus.OK);
    }

    @DeleteMapping("/{num}")
    public ResponseEntity<HashMap> deleteReview(@PathVariable int num){
        reviewRepostiory.deleteById(num); // rnum
        HashMap map = new HashMap();

        List<Review> entitys = reviewRepostiory.findAll();
        map.put("list", entitys);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }*/

   @GetMapping("/{num}/{search}")
    public ResponseEntity<HashMap> searchReview(@PathVariable int num,
                                                     @PathVariable String search,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "4") int size){
        PageRequest pageRequest = PageRequest.of(page, size);

        Accom accom = new Accom();
        accom.setAccomNum(num);


        Page<Review> reviewPage =  reviewRepostiory.findByAccomAndRevContentContaining(accom,search,pageRequest);
        List<Review> list = reviewPage.toList();

        HashMap map = new HashMap();
           map.put("list",list);
           map.put("currentPage",page);
           map.put("totalPage",reviewPage.getTotalPages());
           map.put("pageSize",size);

       return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
