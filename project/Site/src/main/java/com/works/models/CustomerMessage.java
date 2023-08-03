package com.works.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerMessage {

    private String mid;
    private Long cid;
    private String title;
    private String detail;
    private String color;

}
