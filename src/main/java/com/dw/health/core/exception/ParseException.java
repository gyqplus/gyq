package com.dw.health.core.exception;

import freemarker.template.TemplateModelException;

public class ParseException extends TemplateModelException {
    public ParseException(String paramName) {
        super("The \"" + paramName + "\" parameter parse failed.");
    }
}
