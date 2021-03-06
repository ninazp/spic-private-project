/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.heattrans;

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
import com.jeeplus.modules.fea.entity.heattrans.Fea_design_heattransVO;
import com.jeeplus.modules.fea.service.heattrans.Fea_design_heattransVOService;

/**
 * 换热器价格Controller
 * @author jw
 * @version 2018-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/heattrans/fea_design_heattransVO")
public class Fea_design_heattransVOController extends BaseController {

	@Autowired
	private Fea_design_heattransVOService fea_design_heattransVOService;
	
	@ModelAttribute
	public Fea_design_heattransVO get(@RequestParam(required=false) String id) {
		Fea_design_heattransVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_design_heattransVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_design_heattransVO();
		}
		return entity;
	}
	
	/**
	 * 换热器价格列表页面
	 */
	@RequiresPermissions("fea:heattrans:fea_design_heattransVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/heattrans/fea_design_heattransVOList";
	}
	
		/**
	 * 换热器价格列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:heattrans:fea_design_heattransVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_design_heattransVO fea_design_heattransVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_design_heattransVO> page = fea_design_heattransVOService.findPage(new Page<Fea_design_heattransVO>(request, response), fea_design_heattransVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑换热器价格表单页面
	 */
	@RequiresPermissions(value={"fea:heattrans:fea_design_heattransVO:view","fea:heattrans:fea_design_heattransVO:add","fea:heattrans:fea_design_heattransVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_design_heattransVO fea_design_heattransVO, Model model) {
		model.addAttribute("fea_design_heattransVO", fea_design_heattransVO);
		return "modules/fea/heattrans/fea_design_heattransVOForm";
	}

	/**
	 * 保存换热器价格
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:heattrans:fea_design_heattransVO:add","fea:heattrans:fea_design_heattransVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_design_heattransVO fea_design_heattransVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_design_heattransVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_design_heattransVOService.save(fea_design_heattransVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存换热器价格成功");
		return j;
	}
	
	/**
	 * 删除换热器价格
	 */
	@ResponseBody
	@RequiresPermissions("fea:heattrans:fea_design_heattransVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_design_heattransVO fea_design_heattransVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_design_heattransVOService.delete(fea_design_heattransVO);
		j.setMsg("删除换热器价格成功");
		return j;
	}
	
	/**
	 * 批量删除换热器价格
	 */
	@ResponseBody
	@RequiresPermissions("fea:heattrans:fea_design_heattransVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_design_heattransVOService.delete(fea_design_heattransVOService.get(id));
		}
		j.setMsg("删除换热器价格成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:heattrans:fea_design_heattransVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_design_heattransVO fea_design_heattransVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "换热器价格"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_design_heattransVO> page = fea_design_heattransVOService.findPage(new Page<Fea_design_heattransVO>(request, response, -1), fea_design_heattransVO);
    		new ExportExcel("换热器价格", Fea_design_heattransVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出换热器价格记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:heattrans:fea_design_heattransVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_design_heattransVO> list = ei.getDataList(Fea_design_heattransVO.class);
			for (Fea_design_heattransVO fea_design_heattransVO : list){
				try{
					fea_design_heattransVOService.save(fea_design_heattransVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条换热器价格记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条换热器价格记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入换热器价格失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/heattrans/fea_design_heattransVO/?repage";
    }
	
	/**
	 * 下载导入换热器价格数据模板
	 */
	@RequiresPermissions("fea:heattrans:fea_design_heattransVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "换热器价格数据导入模板.xlsx";
    		List<Fea_design_heattransVO> list = Lists.newArrayList(); 
    		new ExportExcel("换热器价格数据", Fea_design_heattransVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/heattrans/fea_design_heattransVO/?repage";
    }

}