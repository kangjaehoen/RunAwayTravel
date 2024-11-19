package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.User;
import com.example.runawaytravel.entity.WishList;
import com.example.runawaytravel.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wish")
@CrossOrigin(origins = "*")
public class WishListController {
    @Autowired
    private WishListRepository wishListRepository;

    @GetMapping
    public ResponseEntity<List<WishList>> findByUserName(/*@AuthenticationPrincipal User user*/){
        User userName = new User();
        // userName = user.getUserName();
        //임시 아이디 설정
        userName.setUsername("testID");
        List<WishList> list =  wishListRepository.findByUserName(userName);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WishList> saveWishList(@RequestBody WishList wishList){
        WishList wishList1 = wishListRepository.save(wishList);
        return new ResponseEntity<>(wishList1, HttpStatus.OK);
    }

    @GetMapping("{accomNum}")
    public ResponseEntity<Boolean> checkWishList(@PathVariable int accomNum){
        Accom accom = new Accom();
        accom.setAccomNum(accomNum);
        Boolean check;
        int num =  wishListRepository.findCheckWishList(accom);
           if(num == 1){
               check = true;
           }else{
               check= false;
                //예외처리
           }
       return new ResponseEntity<>(check,HttpStatus.OK);
    }

}
