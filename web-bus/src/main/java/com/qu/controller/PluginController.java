package com.qu.controller;

import com.qu.spring.DefaultSpringPluginFactory;
import com.qu.spring.PluginConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Controller
@RequestMapping(value="/plugin")
public class PluginController {

    @Autowired
    private DefaultSpringPluginFactory pluginFactory;

    @RequestMapping(value="list", method = RequestMethod.GET )
    public String getHavePlugins(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Collection<PluginConfig> lst = null;

        pluginFactory.flushConfigs();

        req.setAttribute("havePlugin", lst);
        return "/plugins";
    }

    @RequestMapping(value="/active", method = RequestMethod.GET )
    public void activePlugin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        pluginFactory.activePlugin(req.getParameter("id"));
        resp.getWriter().append("active succeed!");
    }

    @RequestMapping(value="/active", method = RequestMethod.GET )
    public void disablePlugin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        pluginFactory.disablePlugin(req.getParameter("id"));
        resp.getWriter().append("disable succeed!");
    }
}
