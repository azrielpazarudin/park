package com.park.park.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String nim;
    private String password;
    private String plat;
    private String jurusan;
    @Column(columnDefinition = "BLOB")
    private byte[] image;
}
