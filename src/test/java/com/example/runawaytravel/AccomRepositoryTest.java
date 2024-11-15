package com.example.runawaytravel;

import com.example.runawaytravel.entity.Accom;
import com.example.runawaytravel.repository.AccomRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class AccomRepositoryTest {

    @Autowired
    AccomRepository accomRepository;

    @Test
    void test1(){
        List<Accom> list = accomRepository.findAll();
        list.stream().forEach(System.out::println);
        System.out.println(list);
    }

    @Test
    void test2(){
        Optional<Accom> list=accomRepository.findById(40);
        if(list == null) {
            System.out.println("존재하지 않음!");
        }else{
            System.out.println(list);
        }
    }
}
