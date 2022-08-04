package com.example.demo.dao;

import com.example.demo.model.CaptchaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CaptchaRepository extends JpaRepository<CaptchaCode, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM captcha_codes WHERE time < DATE_SUB(NOW(), interval :lifeTime second);", nativeQuery = true)
    public void deleteByLifeTime(@Param("lifeTime") Integer lifeTime);
}
