package com.example.yousavebackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "blood_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private int donationCount;
    private int userCount;

    @OneToMany(mappedBy = "bloodType")
    private List<User> users;

    public void incrementDonationCount(int amount) {
        this.donationCount += amount;
    }

    public void incrementUserCount() {
        this.userCount++;
    }
}