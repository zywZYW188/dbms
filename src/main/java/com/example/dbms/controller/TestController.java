package com.example.dbms.controller;


import com.example.dbms.domain.po.Users;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	@RequestMapping("/")
	public String redirectindex(Model m) {
		return "index";
	}
	@RequestMapping("/index")
	public String index(Model m) {
		return "index";
	}
	@RequestMapping("/login")
	public String login(Model m) {
		return "login";
	}

    @RequestMapping("/loginIndex")
    public String loginIndex(Model model) {
        Users user = (Users)SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", user);
        return "loginIndex";
    }
	@RequestMapping("/casesSearch")
	@RequiresPermissions("permission:query")
	public String casesSearch(Model model) {
		Users user = (Users)SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
		return "casesSearch";
	}
	@RequestMapping("/casesAddition")
	@RequiresPermissions("permission:add")
	public String casesAddition(Model model) {
		Users user = (Users)SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
		return "casesAddition";
	}
	@RequestMapping("/casesModification")
	@RequiresPermissions("permission:modify")
	public String casesModification(Model model) {
		Users user = (Users)SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
		return "casesModification";
	}
	@RequestMapping("/casesUpdata")
	@RequiresPermissions("permission:upload")
	public String casesUpdata(Model model) {
		Users user = (Users)SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
		return "casesUpdata";
	}
	@RequestMapping("/cdgDiagnosis")
	@RequiresPermissions("permission:diagnosis")
	public String cdgDiagnosis(Model model) {
		Users user = (Users)SecurityUtils.getSubject().getPrincipal();
		model.addAttribute("user", user);
		return "cdgDiagnosis";
	}
}
