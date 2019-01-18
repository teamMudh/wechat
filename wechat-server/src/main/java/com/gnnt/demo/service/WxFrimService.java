package com.gnnt.demo.service;

import com.gnnt.demo.entity.WxFrim;
import com.gnnt.demo.entity.NewYearEvent;
import com.gnnt.demo.repositoty.WxFrimRepositoty;
import com.gnnt.demo.repositoty.EventRepositoty;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


/**
 * Created by Song on 2017/2/15.
 * User
 */
@Service
@Transactional
public class WxFrimService {
	
	@Autowired
    private WxFrimRepositoty wxFrimRepositoty;

    @Autowired
    private EventRepositoty eventRepositoty;


    public void saveWxFrim(WxFrim frim){
        try{
            wxFrimRepositoty.save(frim);


        }catch (Exception e){}
    }

    public boolean existsWxFrimByOpenId(String openid){
        boolean isFlag = false;
        try{
            isFlag = wxFrimRepositoty.exists(openid);
//            wxFrimRepositoty.


        }catch (Exception e){}
        return isFlag;

    }




    public Long countFrim(){
        Long count = 0L;
        try{
            count = eventRepositoty.count(new Specification<NewYearEvent>() {
                @Override
                public Predicate toPredicate(Root<NewYearEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                    List<Predicate> list = new ArrayList<Predicate>();
                    list.add(cb.equal(root.get("type").as(Integer.class), 0));


                    Predicate[] p = new Predicate[list.size()];
                    return cb.and(list.toArray(p));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }


    public Long eventAdd(NewYearEvent event){
        Long eventId = 0L;
        try{
            NewYearEvent  eventFlush = eventRepositoty.saveAndFlush(event);
            eventId = eventFlush.getId();
            eventRepositoty.updateEventParent(eventId,eventId);
//            eventRepositoty.f
        }catch (Exception e){
            e.printStackTrace();
        }

        return eventId;
    }

    /**
     * 通过类型添加  只有 邀请者和接受者
     * @param type
     * @param openId
     * @param parentId
     * @return
     */
    public NewYearEvent eventAddByType(Integer type,String openId,final Long parentId,String context){
        Long ret =0L;
        WxFrim frim = wxFrimRepositoty.findOne(openId);
        NewYearEvent eventParent = eventRepositoty.findOne(parentId);
        NewYearEvent event = new NewYearEvent();
        event.setParentId(parentId);
        event.setType(type);
        event.setOpenId(openId);
        event.setTitle(eventParent.getTitle());
        event.setNickName(frim.getNickName());
        event.setAvatarUrl(frim.getAvatarUrl());
        event.setContent(context);
        event.setParentName(eventParent.getNickName());
        event.setCreateTime( new Date());

        if(type == 1){
            event.setStatus(1);//当类型为邀请者时  状态为初始化；   类型为接收者时 状态为结束

        }else{
            event.setStatus(4);//   类型为接收者时 状态为结束
            eventRepositoty.updateEventStatus(4 ,parentId);
        }


        event= eventRepositoty.saveAndFlush(event);



        return  event;
    }

    public void eventUpdateByStatus(NewYearEvent event){

        try{
            eventRepositoty.updateEventStatus(event.getStatus(),event.getId());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  添加内容只有 发起者 和邀请者 返回父ID
     * @param event
     */
    public int eventUpdateByContext(final NewYearEvent event){
        int status = -1;
        try{
            eventRepositoty.updateEventContent(event.getContent(),event.getId());
            NewYearEvent eventEntity = eventRepositoty.findOne(event.getId());

            //如果子事件存在结束状态 则直接修改为发送祝福
            long countEnd =  eventRepositoty.count(new Specification<NewYearEvent>() {
                @Override
                public Predicate toPredicate(Root<NewYearEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                    List<Predicate> list = new ArrayList<Predicate>();
                    list.add(cb.equal(root.get("parentId").as(Long.class), event.getId()));
                    list.add(cb.equal(root.get("status").as(Integer.class), 4));

                    Predicate[] p = new Predicate[list.size()];
                    return cb.and(list.toArray(p));
                }
            });


            if(eventEntity.getType() == 0){//当类型为发起者，修改发起者状态 为1待加入
                if(countEnd > 0){ //如果存在则直接修改状态为 待发送祝福
                    eventRepositoty.updateEventStatus(3 , event.getId());
                    status = 3;
                }else{//如果不存在则直接修改为 待加入
                    eventRepositoty.updateEventStatus(2 , event.getId());
                    status = 2;
                }

            }else{
                status = 4 ;
                eventRepositoty.updateEventStatus(4 , event.getId());
                NewYearEvent eventParent = eventRepositoty.findOne(eventEntity.getParentId());
                //当父事件状态为带加入 时才修改为  发送祝福
                if(eventParent.getStatus() == 2){
                    eventRepositoty.updateEventStatus(3 , eventEntity.getParentId());
                }

                if(eventParent.getStatus() >= 2 && eventParent.getStatus() !=4){
                    countEnd++;
                }

            }
            countEnd++;


            eventRepositoty.updateEventCount(countEnd,eventEntity.getParentId());





        }catch (Exception e){
            e.printStackTrace();
        }
        return  status;

    }

    public List<NewYearEvent> findSerach(final NewYearEvent event){
        Assert.notNull(event);
        Sort sort = new Sort(Sort.Direction.ASC, "id");

        List<NewYearEvent> result = eventRepositoty.findAll(new Specification<NewYearEvent>() {
            @Override
            public Predicate toPredicate(Root<NewYearEvent> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<Predicate>();
                if (StringUtils.isNotBlank(event.getOpenId())) {
                    list.add(cb.equal(root.get("openId").as(String.class), event.getOpenId() ));
                }

                if(event.getParentId()!=null && event.getParentId() != 0L){
                    list.add(cb.equal(root.get("parentId").as(Long.class), event.getParentId()));
                }
                //类型为接受者则剔除他
                list.add(cb.notEqual(root.get("type").as(Integer.class), 3));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        }, sort);

        return result;
    }

}
