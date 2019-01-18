package com.gnnt.demo.repositoty;

import com.gnnt.demo.entity.NewYearEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Created by Song on 2017/2/15.
 * User JpaSpecificationExecutor
 */
@Repository
public interface EventRepositoty extends JpaRepository<NewYearEvent,Long>,JpaSpecificationExecutor<NewYearEvent> {


    @Modifying
    @Query("update NewYearEvent t set t.parentId = :parentId where t.id = :id")
    public void updateEventParent(@Param(value = "parentId") Long parentId,@Param(value = "id") Long id);


    @Modifying
    @Query("update NewYearEvent t set t.status = :status where t.id = :id")
    public void updateEventStatus(@Param(value = "status") Integer status,@Param(value = "id") Long id);

    @Modifying
    @Query("update NewYearEvent t set t.content = :content where t.id = :id")
    public void updateEventContent(@Param(value = "content") String content,@Param(value = "id") Long id);

    @Modifying
    @Query("update  NewYearEvent t set t.contFrim = :contFrim where t.parentId = :parentId")
    public void updateEventCount(@Param(value = "contFrim") Long contFrim,@Param(value = "parentId") Long parentId);
}
