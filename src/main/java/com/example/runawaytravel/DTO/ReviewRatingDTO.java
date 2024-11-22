package com.example.runawaytravel.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewRatingDTO {
    private int reviewCount;
    private int count5;
    private int count4;
    private int count3;
    private int count2;
    private int count1;
    private double satisAvg;
    private double accuracyAvg;
    private double cleanAvg;
    private double scpAvg;

    public ReviewRatingDTO(long reviewCount, long count5, long count4, long count3, long count2, long count1,
                           double satisAvg, double accuracyAvg, double cleanAvg, double scpAvg) {
        this.reviewCount = (int) reviewCount;
        this.count5 = (int) count5;
        this.count4 = (int) count4;
        this.count3 = (int) count3;
        this.count2 = (int) count2;
        this.count1 = (int) count1;
        this.satisAvg = satisAvg;
        this.accuracyAvg = accuracyAvg;
        this.cleanAvg = cleanAvg;
        this.scpAvg = scpAvg;
    }


}
