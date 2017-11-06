/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.mapper.project;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.fea.entity.project.FeaProject;

/**
 * 项目登记MAPPER接口
 * @author zp
 * @version 2017-11-05
 */
@MyBatisMapper
public interface FeaProjectMapper extends TreeMapper<FeaProject> {
	
}