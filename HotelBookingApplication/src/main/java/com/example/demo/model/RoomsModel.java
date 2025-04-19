package com.example.demo.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
@Data
public class RoomsModel {

    private int room_id;
    private int hotel_id;
    private int type_id;
    private BigDecimal price;
    private String availability_status;
    private String image_url;
    private Timestamp created_at;
   
}
