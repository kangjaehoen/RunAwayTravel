package com.example.runawaytravel;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.entity.User;
import com.example.runawaytravel.entity.WishList;
import com.example.runawaytravel.repository.WishListRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    @Test
    public void findAllByUsername(){
        User user = new User();
        user.setUsername("testID");
        List<WishList> list =  wishListRepository.findByUserName(user);
        list.stream().forEach(System.out::println);

    }

    @Test
    @Rollback(value = false)
    @Transactional
    public void saveWishList(){
        WishList wish = new WishList();
        wish.setCheckStatus(1);

        User user = new User();
        user.setUsername("testID");
        wish.setUserName(user);

        Accom accom = new Accom();
        accom.setAccomNum(9);
        wish.setAccomNum(accom);

        wishListRepository.save(wish);
    }

    @Test
    public void checkWishList(){
        Accom accom = new Accom();
        accom.setAccomNum(9);
        int num = wishListRepository.findCheckWishList(accom);
        System.out.println(num);
    }


    @Test
    @Rollback(value = false)
    @Transactional
    public void deleteByIdANDAccom(){
        Accom accom = new Accom();
        accom.setAccomNum(10);
        User user = new User();
        user.setUsername("testID");
        int num = wishListRepository.deleteByUserNameAndAccomNum(user,accom);
        System.out.println(num);
    }

}
