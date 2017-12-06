/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.fecl;

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
import com.jeeplus.modules.fea.entity.fecl.Fea_costfecfVO;
import com.jeeplus.modules.fea.service.fecl.Fea_costfecfVOService;

/**
 * 财务费用及流动资金Controller
 * @author jw
 * @version 2017-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/fecl/fea_costfecfVO")
public class Fea_costfecfVOController extends BaseController {

	@Autowired
	private Fea_costfecfVOService fea_costfecfVOService;
	
	@ModelAttribute
	public Fea_costfecfVO get(@RequestParam(required=false) String id) {
		Fea_costfecfVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_costfecfVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_costfecfVO();
		}
		return entity;
	}
	
	/**
	 * 财务费用及流动资金列表页面
	 */
	@RequiresPermissions("fea:fecl:fea_costfecfVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/fecl/fea_costfecfVOList";
	}
	
		/**
	 * 财务费用及流动资金列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:fecl:fea_costfecfVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_costfecfVO fea_costfecfVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_costfecfVO> page = fea_costfecfVOService.findPage(new Page<Fea_costfecfVO>(request, response), fea_costfecfVO); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑财务费用及流动资金表单页面
	 */
	@RequiresPermissions(value={"fea:fecl:fea_costfecfVO:view","fea:fecl:fea_costfecfVO:add","fea:fecl:fea_costfecfVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_costfecfVO fea_costfecfVO, Model model) {
		model.addAttribute("fea_costfecfVO", fea_costfecfVO);
		return "modules/fea/fecl/fea_costfecfVOForm";
	}

	/**
	 * 保存财务费用及流动资金
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:fecl:fea_costfecfVO:add","fea:fecl:fea_costfecfVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_costfecfVO fea_costfecfVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_costfecfVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_costfecfVOService.save(fea_costfecfVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存财务费用及流动资金成功");
		return j;
	}
	
	/**
	 * 删除财务费用及流动资金
	 */
	@ResponseBody
	@RequiresPermissions("fea:fecl:fea_costfecfVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_costfecfVO fea_costfecfVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_costfecfVOService.delete(fea_costfecfVO);
		j.setMsg("删除财务费用及流动资金成功");
		return j;
	}
	
	/**
	 * 批量删除财务费用及流动资金
	 */
	@ResponseBody
	@RequiresPermissions("fea:fecl:fea_costfecfVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_costfecfVOService.delete(fea_costfecfVOService.get(id));
		}
		j.setMsg("删除财务费用及流动资金成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:fecl:fea_costfecfVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_costfecfVO fea_costfecfVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "财务费用及流动资金"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_costfecfVO> page = fea_costfecfVOService.findPage(new Page<Fea_costfecfVO>(request, response, -1), fea_costfecfVO);
    		new ExportExcel("财务费用及流动资金", Fea_costfecfVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出财务费用及流动资金记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:fecl:fea_costfecfVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_costfecfVO> list = ei.getDataList(Fea_costfecfVO.class);
			for (Fea_costfecfVO fea_costfecfVO : list){
				try{
					fea_costfecfVOService.save(fea_costfecfVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条财务费用及流动资金记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条财务费用及流动资金记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入财务费用及流动资金失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/fecl/fea_costfecfVO/?repage";
    }
	
	/**
	 * 下载导入财务费用及流动资金数据模板
	 */
	@RequiresPermissions("fea:fecl:fea_costfecfVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "财务费用及流动资金数据导入模板.xlsx";
    		List<Fea_costfecfVO> list = Lists.newArrayList(); 
    		new ExportExcel("财务费用及流动资金数据", Fea_costfecfVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/fecl/fea_costfecfVO/?repage";
    }

}