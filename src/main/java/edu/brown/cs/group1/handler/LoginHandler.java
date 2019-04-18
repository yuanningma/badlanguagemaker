package edu.brown.cs.group1.handler;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.Map;

public class LoginHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
        Map<String, Object> variables = ImmutableMap.of("title",
                "User Login", "message", "");
        return new ModelAndView(variables, "login.ftl");
    }
}
