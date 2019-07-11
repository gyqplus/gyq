package com.dw.health.core.web.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.dw.health.core.exception.ParamsRequiredException;
import com.dw.health.core.util.DictUtils;
import com.dw.health.core.util.DirectiveUtils;
import com.dw.health.eportal.dao.DictItemMapper;
import com.dw.health.eportal.entity.DictItem;
import com.dw.health.framework.util.SpringBeanUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 下拉框指令
 *
 * @author 卢燕辉
 * @date 2019.3.19
 */
public class SelectTag implements TemplateDirectiveModel {
    @Override
    public void execute(Environment env,
                        Map params,
                        TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        List<DictItem> dictItems = null;

        String id = DirectiveUtils.getString("id", params);
        String name = DirectiveUtils.getString("name", params);
        String code = DirectiveUtils.getString("code", params);
        String arrayCode = DirectiveUtils.getString("arrayCode", params);
        String value = DirectiveUtils.getString("value", params);
        String clazz = DirectiveUtils.getString("class", params);
        String style = DirectiveUtils.getString("style", params);

        if (StringUtils.isEmpty(name)) {
            throw new ParamsRequiredException("name");
        }
        if (StringUtils.isEmpty(code) && StringUtils.isEmpty(arrayCode)) {
            throw new ParamsRequiredException("code and arrayCode");
        }
        if (StringUtils.isEmpty(clazz)) {
            clazz = "form-control";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(" <select ");
        if (StringUtils.isNotEmpty(id)) {
            sb.append(" id='").append(id).append("' ");
        }
        sb.append(" name='").append(name).append("' ");
        sb.append(" class='").append(clazz).append("' ");
        if (StringUtils.isNotEmpty(style)) {
            sb.append(" style='").append(style).append("' ");
        }
        sb.append(" > ");

        if (StringUtils.isNotEmpty(arrayCode)) {
            dictItems = DictUtils.parseArrayCode(arrayCode);
        } else if (StringUtils.isNotEmpty(code)) {
            dictItems = SpringBeanUtils.getBean(DictItemMapper.class).selectByTypeId(code);

        }
        sb.append(" <option value=''> --请选择-- </option>");
        if (dictItems != null && !dictItems.isEmpty()) {
            for (DictItem dictItem : dictItems) {
                sb.append(" <option value='").append(dictItem.getValue()).append("' ");
                if (dictItem.getValue().equals(value)) {
                    sb.append(" selected='selected' ");
                }
                sb.append(" > ");
                sb.append(dictItem.getContent());
                sb.append(" </option> ");
            }
        }
        sb.append(" </select> ");

        Writer writer = env.getOut();
        writer.write(sb.toString());
        writer.flush();

        if (body != null) {
            body.render(writer);
        }
    }
}
