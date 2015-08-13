package com.luiscamilo.swagger.controllers;

import play.Configuration;
import play.mvc.Result;
import play.twirl.api.Content;
import views.html.com_luiscamilo_swagger;

import javax.inject.Inject;

import static play.mvc.Results.ok;

/**
 * Created by lcamilo on 8/9/15.
 */
public class Swagger {
    @Inject
    Configuration configuration;
    public Result apiDocs() {
        return ok((Content) com_luiscamilo_swagger.render(
                configuration.getString("swagger.api.title"),
                configuration.getString("swagger.api.basepath"),
                configuration.getString("swagger.api.token")
        ));
    }

}
