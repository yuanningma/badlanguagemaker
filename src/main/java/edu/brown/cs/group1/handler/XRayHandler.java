package edu.brown.cs.group1.handler;

import com.google.common.collect.ImmutableMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import java.util.Map;

public class XRayHandler implements TemplateViewRoute {
        @Override
   public ModelAndView handle(Request req, Response res) {
       Map<String, Object> variables = ImmutableMap.of("title",
                    "XRay-View", "message", "");
      return new ModelAndView(variables, "xray.ftl");
   }
}
