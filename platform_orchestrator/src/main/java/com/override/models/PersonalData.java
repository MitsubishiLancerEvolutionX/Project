package com.override.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PersonalData extends PlatformUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "actNumber")
    private Long actNumber;

    @Column(name = "contractNumber")
    private String contractNumber;

    @Column(name = "date")
    private Date date;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "passportSeries")
    private Long passportSeries;

    @Column(name = "passportNumber")
    private Long passportNumber;

    @Column(name = "passportIssued")
    private String passportIssued;

    @Column(name = "issueDate")
    private Date issueDate;

    @Column(name = "birthDate")
    private Date birthDate;

    @Column(name = "registration")
    private String registration;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private Long phoneNumber;

    @JsonIgnore
    @OneToOne(mappedBy = "personalData")
    private PlatformUser user;
}
