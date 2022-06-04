package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "captcha_codes")
@Getter
@Setter
@NoArgsConstructor
public class CaptchaCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "time", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date time;

    @Column(name = "code", columnDefinition = "TINYTEXT NOT NULL")
    private String code;

    @Column(name = "secret_code", columnDefinition = "TINYTEXT NOT NULL")
    private String secretCode;


}
