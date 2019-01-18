package com.gnnt.demo.repositoty;

import com.gnnt.demo.entity.WxFrim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Created by Song on 2017/2/15.
 * User
 */
@Repository
public interface WxFrimRepositoty extends JpaRepository<WxFrim,String>{

    @Query("select t from WxFrim t where t.nickName = :nickName")
    WxFrim findByFirmName(@Param("nickName") String nickName);
}
