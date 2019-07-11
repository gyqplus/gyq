package com.dw.health.core.config;

import com.dw.health.core.web.freemarker.SelectMap;
import com.dw.health.core.web.freemarker.SelectTag;

import freemarker.template.SimpleHash;

public class DwTags extends SimpleHash {
    public DwTags() {
        this.put("select", new SelectTag());
        this.put("selectMap", new SelectMap());
    }
}
