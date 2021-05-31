package com.nice.datafileanomalydetection.view;

import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;

@Api(value = "View Mapping")
@Controller
public class ViewController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@RequestMapping(value="/study/{html}" , method = {RequestMethod.GET, RequestMethod.POST})
	public String studyView(@PathVariable("html") String html, HttpServletRequest request, Model model) {
		model.addAttribute("menu", html);
		model.addAttribute("paramData", request.getParameter("paramData"));
		return "content/study/" + html;
	}

	@RequestMapping(value="/result/{html}" , method = {RequestMethod.GET, RequestMethod.POST})
	public String resultView(@PathVariable("html") String html, HttpServletRequest request, Model model) {
		model.addAttribute("menu", html);
		model.addAttribute("paramData", request.getParameter("paramData"));
		return "content/result/" + html;
	}

	@RequestMapping(value="/member/{html}" , method = {RequestMethod.GET, RequestMethod.POST})
	public String memberView(@PathVariable("html") String html, HttpServletRequest request, Model model) {
		model.addAttribute("menu", html);
		model.addAttribute("paramData", request.getParameter("paramData"));
		return "content/member/" + html;
	}

	@RequestMapping(value="/execute/{html}" , method = {RequestMethod.GET, RequestMethod.POST})
	public String executeView(@PathVariable("html") String html, HttpServletRequest request, Model model) {
		model.addAttribute("menu", html);
		model.addAttribute("paramData", request.getParameter("paramData"));
		return "content/execute/" + html;
	}

	@RequestMapping(value={ "/", "/calendar" } , method = {RequestMethod.GET, RequestMethod.POST})
	public String calendarView(Model model, HttpServletRequest request) {
		model.addAttribute("menu", "calendar");
		return "content/calendar";
	}

}
