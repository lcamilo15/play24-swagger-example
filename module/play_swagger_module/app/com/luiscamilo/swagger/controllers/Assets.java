package com.luiscamilo.swagger.controllers;

import play.api.mvc.Action;
import play.api.mvc.AnyContent;

import javax.inject.Inject;

/**
 * Created by lcamilo on 8/9/15.
 */
public class Assets {
    @Inject
    controllers.Assets assets;
    public Action<AnyContent> versioned(String path, controllers.Assets.Asset file) {
        return assets.versioned(path, file);
    }
}
