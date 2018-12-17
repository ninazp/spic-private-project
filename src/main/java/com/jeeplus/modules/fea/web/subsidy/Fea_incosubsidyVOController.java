/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.subsidy;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.entity.subsidy.Fea_incosubsidyVO;
import com.jeeplus.modules.fea.service.project.FeaProjectBService;
import com.jeeplus.modules.fea.service.subsidy.Fea_incosubsidyVOService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 补贴收入Controller
 * @author jw
 * @version 2017-12-06
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/subsidy/fea_incosubsidyVO")
public class Fea_incosubsidyVOController extends BaseController {

	@Autowired
	private Fea_incosubsidyVOService fea_incosubsidyVOService;
	
	@Autowired
	private FeaProjectBService feaProjectBService;
	
	@ModelAttribute
	public Fea_incosubsidyVO get(@RequestParam(required=false) String id) {
		Fea_incosubsidyVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fea_incosubsidyVOService.get(id);
		}
		if (entity == null){
			entity = new Fea_incosubsidyVO();
		}
		return entity;
	}
	
	/**
	 * 补贴收入列表页面
	 */
	@RequiresPermissions("fea:subsidy:fea_incosubsidyVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/subsidy/fea_incosubsidyVOList";
	}
	
		/**
	 * 补贴收入列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:subsidy:fea_incosubsidyVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fea_incosubsidyVO fea_incosubsidyVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fea_incosubsidyVO> page = fea_incosubsidyVOService.findPage(new Page<Fea_incosubsidyVO>(request, response), fea_incosubsidyVO); 
		
		FeaProjectB  feaProjectB = new FeaProjectB();
		feaProjectB.setCreateBy(UserUtils.getUser());
		List<FeaProjectB> list = feaProjectBService.findList(feaProjectB);
		Map<String,String> projectbmap = new HashMap<String,String>();
		if(null!=list && list.size()>0) {
			for(FeaProjectB proj : list) {
				projectbmap.put(proj.getId(), proj.getId());
			}
		}
		List<Fea_incosubsidyVO> flist = page.getList();

		List<Fea_incosubsidyVO> filterlist = new ArrayList<>();
		if(null!=flist && flist.size()>0) {
			for(Fea_incosubsidyVO vo : flist) {
				if(projectbmap.containsKey(vo.getFeaProjectB().getId())) {
					filterlist.add(vo);
				}
			}
		}
		page.setList(filterlist);
		
		
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑补贴收入表单页面
	 */
	@RequiresPermissions(value={"fea:subsidy:fea_incosubsidyVO:view","fea:subsidy:fea_incosubsidyVO:add","fea:subsidy:fea_incosubsidyVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fea_incosubsidyVO fea_incosubsidyVO, Model model) {
		model.addAttribute("fea_incosubsidyVO", fea_incosubsidyVO);
		return "modules/fea/subsidy/fea_incosubsidyVOForm";
	}

	/**
	 * 保存补贴收入
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:subsidy:fea_incosubsidyVO:add","fea:subsidy:fea_incosubsidyVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fea_incosubsidyVO fea_incosubsidyVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fea_incosubsidyVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fea_incosubsidyVOService.save(fea_incosubsidyVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存补贴收入成功");
		return j;
	}
	
	/**
	 * 删除补贴收入
	 */
	@ResponseBody
	@RequiresPermissions("fea:subsidy:fea_incosubsidyVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fea_incosubsidyVO fea_incosubsidyVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fea_incosubsidyVOService.delete(fea_incosubsidyVO);
		j.setMsg("删除补贴收入成功");
		return j;
	}
	
	/**
	 * 批量删除补贴收入
	 */
	@ResponseBody
	@RequiresPermissions("fea:subsidy:fea_incosubsidyVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fea_incosubsidyVOService.delete(fea_incosubsidyVOService.get(id));
		}
		j.setMsg("删除补贴收入成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:subsidy:fea_incosubsidyVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fea_incosubsidyVO fea_incosubsidyVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "补贴收入"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fea_incosubsidyVO> page = fea_incosubsidyVOService.findPage(new Page<Fea_incosubsidyVO>(request, response, -1), fea_incosubsidyVO);
    		new ExportExcel("补贴收入", Fea_incosubsidyVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出补贴收入记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:subsidy:fea_incosubsidyVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fea_incosubsidyVO> list = ei.getDataList(Fea_incosubsidyVO.class);
			for (Fea_incosubsidyVO fea_incosubsidyVO : list){
				try{
					fea_incosubsidyVOService.save(fea_incosubsidyVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条补贴收入记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条补贴收入记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入补贴收入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/subsidy/fea_incosubsidyVO/?repage";
    }
	
	/**
	 * 下载导入补贴收入数据模板
	 */
	@RequiresPermissions("fea:subsidy:fea_incosubsidyVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "补贴收入数据导入模板.xlsx";
    		List<Fea_incosubsidyVO> list = Lists.newArrayList(); 
    		new ExportExcel("补贴收入数据", Fea_incosubsidyVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/subsidy/fea_incosubsidyVO/?repage";
    }

}