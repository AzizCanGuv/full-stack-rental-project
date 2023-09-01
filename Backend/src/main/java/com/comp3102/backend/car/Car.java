package com.comp3102.backend.car;

import com.comp3102.backend.brand.Brand;
import com.comp3102.backend.color.Color;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car_table")
@Builder
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;
    private String description;
    private Double dailyRentPrice;
    private String enginePower;
    private String year;
    private String location;
    private Date createdAt;
    private Boolean isBooked;
    @Enumerated(EnumType.STRING)
    private Transmission transmissionType;
    @OneToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
    @OneToOne
    @JoinColumn(name = "color_id")
    private Color color;
    @Column
    private String carImageId;


    public Car(Integer carId, String description, Double dailyRentPrice, String enginePower, String year, String location, Date createdAt, Transmission transmissionType, Brand brand, Color color) {
        this.carId = carId;
        this.description = description;
        this.dailyRentPrice = dailyRentPrice;
        this.enginePower = enginePower;
        this.year = year;
        this.location = location;
        this.createdAt = createdAt;
        this.transmissionType = transmissionType;
        this.brand = brand;
        this.color = color;
    }
}
