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
 * @date 2019.3.21
 */
public class SelectMap implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env,
                        Map params,
                        TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        List<DictItem> dictItems = null;
        String code = DirectiveUtils.getString("code", params);
        String arrayCode = DirectiveUtils.getString("arrayCode", params);
        if (StringUtils.isEmpty(code) && StringUtils.isEmpty(arrayCode)) {
            throw new ParamsRequiredException("code and arrayCode");
        }

        if (StringUtils.isNotEmpty(arrayCode)) {
            dictItems = DictUtils.parseArrayCode(arrayCode);
        } else if (StringUtils.isNotEmpty(code)) {
            dictItems = SpringBeanUtils.getBean(DictItemMapper.class).selectByTypeId(code);

        }


        StringBuilder sb = new StringBuilder();
        sb.append(" { ");
        if (dictItems != null && !dictItems.isEmpty()) {
            for (DictItem dictItem : dictItems) {
                sb.append(dictItem.getValue()).append(" : '").append(dictItem.getContent()).append("' , ");
            }
        }
        sb.append(" } ");

        Writer writer = env.getOut();
        writer.write(sb.toString());
        writer.flush();

        if (body != null) {
            body.render(writer);
        }
    }
}
