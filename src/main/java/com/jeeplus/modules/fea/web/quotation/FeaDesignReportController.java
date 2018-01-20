/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.quotation;

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
import com.jeeplus.modules.fea.entity.quotation.FeaDesignReport;
import com.jeeplus.modules.fea.service.quotation.FeaDesignReportService;

/**
 * 设备选型报价清单Controller
 * @author zp
 * @version 2018-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/quotation/feaDesignReport")
public class FeaDesignReportController extends BaseController {

	@Autowired
	private FeaDesignReportService feaDesignReportService;
	
	@ModelAttribute
	public FeaDesignReport get(@RequestParam(required=false) String id) {
		FeaDesignReport entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = feaDesignReportService.get(id);
		}
		if (entity == null){
			entity = new FeaDesignReport();
		}
		return entity;
	}
	
	/**
	 * 设备选型报价清单列表页面
	 */
	@RequiresPermissions("fea:quotation:feaDesignReport:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/quotation/feaDesignReportList";
	}
	
		/**
	 * 设备选型报价清单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:quotation:feaDesignReport:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FeaDesignReport feaDesignReport, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FeaDesignReport> page = feaDesignReportService.findPage(new Page<FeaDesignReport>(request, response), feaDesignReport); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑设备选型报价清单表单页面
	 */
	@RequiresPermissions(value={"fea:quotation:feaDesignReport:view","fea:quotation:feaDesignReport:add","fea:quotation:feaDesignReport:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FeaDesignReport feaDesignReport, Model model) {
		model.addAttribute("feaDesignReport", feaDesignReport);
		return "modules/fea/quotation/feaDesignReportForm";
	}

	/**
	 * 保存设备选型报价清单
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:quotation:feaDesignReport:add","fea:quotation:feaDesignReport:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FeaDesignReport feaDesignReport, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, feaDesignReport)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		feaDesignReportService.save(feaDesignReport);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存设备选型报价清单成功");
		return j;
	}
	
	/**
	 * 删除设备选型报价清单
	 */
	@ResponseBody
	@RequiresPermissions("fea:quotation:feaDesignReport:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FeaDesignReport feaDesignReport, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		feaDesignReportService.delete(feaDesignReport);
		j.setMsg("删除设备选型报价清单成功");
		return j;
	}
	
	/**
	 * 批量删除设备选型报价清单
	 */
	@ResponseBody
	@RequiresPermissions("fea:quotation:feaDesignReport:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			feaDesignReportService.delete(feaDesignReportService.get(id));
		}
		j.setMsg("删除设备选型报价清单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:quotation:feaDesignReport:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FeaDesignReport feaDesignReport, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备选型报价清单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FeaDesignReport> page = feaDesignReportService.findPage(new Page<FeaDesignReport>(request, response, -1), feaDesignReport);
    		new ExportExcel("设备选型报价清单", FeaDesignReport.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出设备选型报价清单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:quotation:feaDesignReport:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FeaDesignReport> list = ei.getDataList(FeaDesignReport.class);
			for (FeaDesignReport feaDesignReport : list){
				try{
					feaDesignReportService.save(feaDesignReport);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设备选型报价清单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条设备选型报价清单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入设备选型报价清单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/quotation/feaDesignReport/?repage";
    }
	
	/**
	 * 下载导入设备选型报价清单数据模板
	 */
	@RequiresPermissions("fea:quotation:feaDesignReport:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "设备选型报价清单数据导入模板.xlsx";
    		List<FeaDesignReport> list = Lists.newArrayList(); 
    		new ExportExcel("设备选型报价清单数据", FeaDesignReport.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/quotation/feaDesignReport/?repage";
    }

}