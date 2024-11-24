package com.example.runawaytravel.controller;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.User;
import com.example.runawaytravel.entity.WishList;
import com.example.runawaytravel.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wish")
public class WishListController {
    @Autowired
    private WishListRepository wishListRepository;

    @GetMapping("/{userName}")
    public ResponseEntity<List<WishList>> findByUserName(@PathVariable String userName){

        System.out.println(userName);

        User user = new User();
        user.setUsername(userName);

        System.out.println("axios 요청 들어옴.");
        List<WishList> list =  wishListRepository.findByUserName(user);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> saveWishList(@RequestBody Map<String,Object> wishList, Principal principal){
        System.out.println("wishList 객체 : "+wishList);
        System.out.println("principal 시큐리티 토큰값 : "+principal);

        //String username = principal.getName();

        WishList wishList1  = new WishList();

        User user = new User();
        user.setUsername((String) wishList.get("userName"));

        wishList1.setUserName(user);

        Accom accom = new Accom();
        accom.setAccomNum((Integer) wishList.get("accomNum"));;

        wishList1.setAccomNum(accom);

        String checkStatusString = (String) wishList.get("checkStatus");  // checkStatus가 String일 경우
        int checkStatus = Integer.parseInt(checkStatusString);  // String을 Integer로 변환
        wishList1.setCheckStatus(checkStatus);

        wishListRepository.save(wishList1);
        return new ResponseEntity<>("위시리스트 성공", HttpStatus.OK);
    }

/*    @GetMapping("{accomNum}")
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
    }*/

    @DeleteMapping("/{userName}/{accomNum}")
    @Transactional
    public ResponseEntity<String> deleteByUserNameAndAccomNum(@PathVariable String userName,
                                                                @PathVariable int accomNum,
                                                                Principal principal){
        System.out.println(userName);
        System.out.println(accomNum);
//        System.out.println(principal);
//        if (principal == null && principal.getName() == null && principal.getName().trim().isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//
//        String userName = principal.getName();
//        System.out.println(userName);

        User user = new User();
        user.setUsername(userName);

        Accom accom = new Accom();
        accom.setAccomNum(accomNum);

        wishListRepository.deleteByUserNameAndAccomNum(user, accom);
        return new ResponseEntity<>("위시리스트 삭제성공", HttpStatus.OK);
    }
}
