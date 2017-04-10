package com.wasu.ptyw.galaxycrm.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wasu.ptyw.galaxy.common.util.monitor.MonitorLog;

@Controller
@Slf4j
public class CommonController extends BaseController {
	@RequestMapping(value = "")
	public ModelAndView toIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return index(request, response, model);
	}

	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		try {
			MonitorLog.addStat("a", "b", "c");
			MonitorLog.addStat("a1", "b1", "c1");
			log.info("index info");
			log.warn("index warn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return new ModelAndView("/crm/index", model);
		return new ModelAndView("redirect:/management/index");
	}
}
