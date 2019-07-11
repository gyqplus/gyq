/**
 * @User Administrator
 */

package com.dw.health.eportal.util;
import com.dw.health.eportal.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


/**
 * Created by gyq on 2019-2-27.
 */
public class CurrentUserUtil {
    public static User getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        Object currentUser = subject.getSession().getAttribute("currentUser");
        if (currentUser instanceof User){
            return (User)currentUser;
        }else {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(currentUser, User.class);
        }
    }
}
