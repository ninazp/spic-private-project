/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.fea.web.othercostinfo;

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
import com.jeeplus.modules.fea.entity.othercostinfo.FeaOthercostinfoVO;
import com.jeeplus.modules.fea.entity.project.FeaProjectB;
import com.jeeplus.modules.fea.service.othercostinfo.FeaOthercostinfoVOService;
import com.jeeplus.modules.fea.service.project.FeaProjectBService;
import com.jeeplus.modules.sys.utils.UserUtils;

/**
 * 其他收入Controller
 * @author jw
 * @version 2018-06-15
 */
@Controller
@RequestMapping(value = "${adminPath}/fea/othercostinfo/feaOthercostinfoVO")
public class FeaOthercostinfoVOController extends BaseController {

	@Autowired
	private FeaOthercostinfoVOService feaOthercostinfoVOService;
	
	@Autowired
	private FeaProjectBService feaProjectBService;
	
	
	@ModelAttribute
	public FeaOthercostinfoVO get(@RequestParam(required=false) String id) {
		FeaOthercostinfoVO entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = feaOthercostinfoVOService.get(id);
		}
		if (entity == null){
			entity = new FeaOthercostinfoVO();
		}
		return entity;
	}
	
	/**
	 * 其他收入列表页面
	 */
	@RequiresPermissions("fea:othercostinfo:feaOthercostinfoVO:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/fea/othercostinfo/feaOthercostinfoVOList";
	}
	
		/**
	 * 其他收入列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fea:othercostinfo:feaOthercostinfoVO:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FeaOthercostinfoVO feaOthercostinfoVO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FeaOthercostinfoVO> page = feaOthercostinfoVOService.findPage(new Page<FeaOthercostinfoVO>(request, response), feaOthercostinfoVO); 
		
		
		FeaProjectB  feaProjectB = new FeaProjectB();
		feaProjectB.setCreateBy(UserUtils.getUser());
		List<FeaProjectB> list = feaProjectBService.findList(feaProjectB);
		Map<String,String> projectbmap = new HashMap<String,String>();
		if(null!=list && list.size()>0) {
			for(FeaProjectB proj : list) {
				projectbmap.put(proj.getId(), proj.getId());
			}
		}
		List<FeaOthercostinfoVO> flist = page.getList();
		List<FeaOthercostinfoVO> filterlist = new ArrayList<>();
		if(null!=flist && flist.size()>0) {
			for(FeaOthercostinfoVO vo : flist) {
				if(projectbmap.containsKey(vo.getFeaProjectB().getId())) {
					filterlist.add(vo);
				}
			}
		}
		page.setList(filterlist);
		
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑其他收入表单页面
	 */
	@RequiresPermissions(value={"fea:othercostinfo:feaOthercostinfoVO:view","fea:othercostinfo:feaOthercostinfoVO:add","fea:othercostinfo:feaOthercostinfoVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FeaOthercostinfoVO feaOthercostinfoVO, Model model) {
		model.addAttribute("feaOthercostinfoVO", feaOthercostinfoVO);
		return "modules/fea/othercostinfo/feaOthercostinfoVOForm";
	}

	/**
	 * 保存其他收入
	 */
	@ResponseBody
	@RequiresPermissions(value={"fea:othercostinfo:feaOthercostinfoVO:add","fea:othercostinfo:feaOthercostinfoVO:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FeaOthercostinfoVO feaOthercostinfoVO, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, feaOthercostinfoVO)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		feaOthercostinfoVOService.save(feaOthercostinfoVO);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存其他收入成功");
		return j;
	}
	
	/**
	 * 删除其他收入
	 */
	@ResponseBody
	@RequiresPermissions("fea:othercostinfo:feaOthercostinfoVO:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FeaOthercostinfoVO feaOthercostinfoVO, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		feaOthercostinfoVOService.delete(feaOthercostinfoVO);
		j.setMsg("删除其他收入成功");
		return j;
	}
	
	/**
	 * 批量删除其他收入
	 */
	@ResponseBody
	@RequiresPermissions("fea:othercostinfo:feaOthercostinfoVO:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			feaOthercostinfoVOService.delete(feaOthercostinfoVOService.get(id));
		}
		j.setMsg("删除其他收入成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fea:othercostinfo:feaOthercostinfoVO:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FeaOthercostinfoVO feaOthercostinfoVO, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "其他收入"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FeaOthercostinfoVO> page = feaOthercostinfoVOService.findPage(new Page<FeaOthercostinfoVO>(request, response, -1), feaOthercostinfoVO);
    		new ExportExcel("其他收入", FeaOthercostinfoVO.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出其他收入记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fea:othercostinfo:feaOthercostinfoVO:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FeaOthercostinfoVO> list = ei.getDataList(FeaOthercostinfoVO.class);
			for (FeaOthercostinfoVO feaOthercostinfoVO : list){
				try{
					feaOthercostinfoVOService.save(feaOthercostinfoVO);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条其他收入记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条其他收入记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入其他收入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/othercostinfo/feaOthercostinfoVO/?repage";
    }
	
	/**
	 * 下载导入其他收入数据模板
	 */
	@RequiresPermissions("fea:othercostinfo:feaOthercostinfoVO:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "其他收入数据导入模板.xlsx";
    		List<FeaOthercostinfoVO> list = Lists.newArrayList(); 
    		new ExportExcel("其他收入数据", FeaOthercostinfoVO.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fea/othercostinfo/feaOthercostinfoVO/?repage";
    }

}