/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.equiplst;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.fea.entity.equiplst.Fea_design_equiplst2VO;
import com.jeeplus.modules.fea.service.equiplst.Fea_design_equiplst2VOService;

/**
 * 地热供暖项目设备清单2Controller
 * @author jw
 * @version 2018-01-17
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/equiplst/fea_design_equiplst2VO")
public class Fea_design_equiplst2VOController extends BaseController {

	@Autowired
	private Fea_design_equiplst2VOService fea_design_equiplst2VOService;
	
	@ModelAttribute
	public Fea_design_equiplst2VO get(@RequestParam(required=false) String id) {
		Fea_design_equiplst2VO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_design_equiplst2VOService.get(id);
		}
		if (entity == null){
			entity = new Fea_design_equiplst2VO();
		}
		return entity;
	}
	
	/**
	 * 地热供暖项目设备清单2列表页面
	 */
	@RequiresPermissions("fea:equiplst:fea_design_equiplst2VO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/equiplst/fea_design_equiplst2VOList";
	}
	
		/**
	 * 地热供暖项目设备清单2列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:equiplst:fea_design_equiplst2VO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_design_equiplst2VO fea_design_equiplst2VO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_design_equiplst2VO> page = fea_design_equiplst2VOService.findPage(new Page<Fea_design_equiplst2VO>(request, response), fea_design_equiplst2VO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑地热供暖项目设备清单2表单页面
	 */
	@RequiresPermissions(value={"fea:equiplst:fea_design_equiplst2VO:view","fea:equiplst:fea_design_equiplst2VO:add","fea:equiplst:fea_design_equiplst2VO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_design_equiplst2VO fea_design_equiplst2VO, Model model) {
		model.addAttribute("fea_design_equiplst2VO", fea_design_equiplst2VO);
		return "modules/fea/equiplst/fea_design_equiplst2VOForm";
	}

	/**
	 * 保存地热供暖项目设备清单2
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:equiplst:fea_design_equiplst2VO:add","fea:equiplst:fea_design_equiplst2VO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_design_equiplst2VO fea_design_equiplst2VO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_design_equiplst2VO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_design_equiplst2VOService.save(fea_design_equiplst2VO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存地热供暖项目设备清单2成功");
		return j;
	}
	
	/**
	 * 删除地热供暖项目设备清单2
	 */
	@ResponseBody
	@RequiresPermissions("fea:equiplst:fea_design_equiplst2VO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_design_equiplst2VO fea_design_equiplst2VO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_design_equiplst2VOService.delete(fea_design_equiplst2VO);
		j.setMsg("删除地热供暖项目设备清单2成功");
		return j;
	}
	
	/**
	 * 批量删除地热供暖项目设备清单2
	 */
	@ResponseBody
	@RequiresPermissions("fea:equiplst:fea_design_equiplst2VO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_design_equiplst2VOService.delete(fea_design_equiplst2VOService.get(id));
		}
		j.setMsg("删除地热供暖项目设备清单2成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:equiplst:fea_design_equiplst2VO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_design_equiplst2VO fea_design_equiplst2VO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "地热供暖项目设备清单2"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_design_equiplst2VO> page = fea_design_equiplst2VOService.findPage(new Page<Fea_design_equiplst2VO>(request, response, -1), fea_design_equiplst2VO);
    		new ExportExcel("地热供暖项目设备清单2", Fea_design_equiplst2VO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出地热供暖项目设备清单2记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:equiplst:fea_design_equiplst2VO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_design_equiplst2VO> list = ei.getDataList(Fea_design_equiplst2VO.class);
			for (Fea_design_equiplst2VO fea_design_equiplst2VO : list){
				try{
					fea_design_equiplst2VOService.save(fea_design_equiplst2VO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条地热供暖项目设备清单2记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条地热供暖项目设备清单2记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入地热供暖项目设备清单2失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/equiplst/fea_design_equiplst2VO/?repage";
    }
	
	/**
	 * 下载导入地热供暖项目设备清单2数据模板
	 */
	@RequiresPermissions("fea:equiplst:fea_design_equiplst2VO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "地热供暖项目设备清单2数据导入模板.xlsx";
    		List<Fea_design_equiplst2VO> list = Lists.newArrayList(); 
    		new ExportExcel("地热供暖项目设备清单2数据", Fea_design_equiplst2VO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/equiplst/fea_design_equiplst2VO/?repage";
    }

}