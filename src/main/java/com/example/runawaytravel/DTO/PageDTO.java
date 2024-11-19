package com.example.runawaytravel.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class PageDTO {
    private String key;
    private int page;
    PageDTO(){
        key = "";
        page = 0;
    }
}