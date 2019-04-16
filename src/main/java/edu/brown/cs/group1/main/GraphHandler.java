package edu.brown.cs.group1.main;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.Map;

public class GraphHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
        Map<String, Object> variables = ImmutableMap.of("title",
                "Data", "message", "");
        return new ModelAndView(variables, "chart.ftl");
    }
}
