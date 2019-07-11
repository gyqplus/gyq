package com.dw.health.eportal.service;
import com.dw.health.eportal.entity.Application;
import com.dw.health.eportal.entity.dto.ApplicationDTO;
import java.util.List;

/**
 *
 * @ClassName: gyq
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author gyq 
 * @date 2019-2-20 14:59
 * @version V1.0 
 */
public interface ApplicationService {
    void saveApplication(Application app);

    List<Application> listAppByType(int currentPage,int pageSize,String type);

    int countAppByType(String type);

    void deleteAppList(List<Application> appList);

    void updateApplication(Application application);

    List<ApplicationDTO> listAppDTO(ApplicationDTO appDTO);

    ApplicationDTO getAppDTOByAppId(String id);
}
