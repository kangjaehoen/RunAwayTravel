package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.Review;
import com.example.runawaytravel.repository.ReviewRepostiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<HashMap> findByAccomNumReview(@PathVariable int num,
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

/*    @GetMapping("/rate/{num}")
    public ResponseEntity<ReviewRatingDTO> findByAccomReviewRate(@PathVariable int num){
       ReviewRatingDTO dto = reviewRepostiory.rating(num); // accomNum
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }*/

   @PostMapping
    public ResponseEntity<String> saveReview(@RequestBody Map<String,Object> review){

//       LocalDate date = LocalDate.now();
//       String now = String.valueOf(date);
//       review.setHiredate(now);
//
//       Review entity =  reviewRepostiory.save(review);
       return new ResponseEntity<>("성공",HttpStatus.OK);
    }


    @PutMapping
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
    }

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
