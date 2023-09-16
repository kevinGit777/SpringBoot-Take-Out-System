package com.myProject.reggie.common;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;

//No need to constrain it to employee? why
@Component
@Slf4j
public class ReggieMetaObjectHandler implements MetaObjectHandler {


	@Override
	public void insertFill(MetaObject metaObject) {
		
		log.info("inserting " + metaObject.getOriginalObject().toString());
		LocalDateTime nowDateTime = LocalDateTime.now();
		metaObject.setValue("createTime", nowDateTime);
		metaObject.setValue("updateTime", nowDateTime);
		
		Long cur_userLong = Util.getCurEmployeeId();
		
		if(cur_userLong == null)
		{
			cur_userLong = Util.getCurUserId();
		}
		
		metaObject.setValue("createUser", cur_userLong);
		metaObject.setValue("updateUser", cur_userLong);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		
		log.info("updateing " + metaObject.getOriginalObject().toString());
		metaObject.setValue("updateTime", LocalDateTime.now());

		metaObject.setValue("updateUser", Util.getCurEmployeeId() == null ? Util.getCurUserId() : Util.getCurEmployeeId() );
		
		
	}

}
