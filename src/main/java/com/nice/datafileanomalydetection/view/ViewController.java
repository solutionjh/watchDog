package com.nice.datafileanomalydetection.view;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nice.datafileanomalydetection.util.SessionUtils;

import io.swagger.annotations.Api;

@Api(value="View Mapping")
@Controller
public class ViewController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	
	@GetMapping(value = "/study/{html}")
	public String studyView(@PathVariable("html") String html, Model model) {
		model.addAttribute("menu", html);
		return "content/study/" + html;
	}
	@GetMapping(value = "/result/{html}")
	public String resultView(@PathVariable("html") String html, Model model) {
		model.addAttribute("menu", html);
		return "content/result/" + html;
	}
	
	@GetMapping(value = "/member/{html}")
	public String memberView(@PathVariable("html") String html, Model model) {
		model.addAttribute("menu", html);
		return "content/member/" + html;
	}	
	
	@GetMapping(value = "/execute/{html}")
	public String executeView(@PathVariable("html") String html, Model model) {
		model.addAttribute("menu", html);
		return "content/execute/" + html;
	}
	
	@GetMapping(value = {"/", "/calendar"})
	public String calendarView(Model model) {
		model.addAttribute("menu", "calendar");
		return "content/calendar";
	}

}
