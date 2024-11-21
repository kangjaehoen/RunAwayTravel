package com.example.runawaytravel.dto2;

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
