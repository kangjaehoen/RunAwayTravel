package com.example.runawaytravel.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDTO {
    private String impUid;
    private String merchantUid;
    private int resNum;
    private String amount;
    private String name;
    private String apply_num;
    private Character pay_status;
    private int accomNum;
    private LocalDate payDate;
    private String username;
}
