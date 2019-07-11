package com.dw.health.eportal;

import com.dw.health.eportal.dao.ApplicationMapper;
import com.dw.health.eportal.entity.Application;
import com.dw.health.eportal.service.ApplicationService;
import com.dw.health.eportal.service.NavigationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EportalApplicationTests {
	@Autowired
	private ApplicationMapper applicationMapper;
	@Autowired
	private ApplicationService appService;
	@Autowired
	NavigationService navigationService;

	@Test
	public void contextLoads() {
	}
	@Test
	public void BooleanTest(){
		Application application = new Application();
		application.setAppId("2");
		application.setAppName("ceshi2");
		application.setDeleteFlag(false);
		application.setSeq(4);
		application.setCreateTime(new Date());
		application.setUrl("www.baidu.com");
		//applicationMapper.insert(application);
	}
	@Test
	public void getTest(){
		Application application = applicationMapper.selectByPrimaryKey("2");
		System.out.println(application.getDeleteFlag());
	}
	@Test
	public void testPageHelper(){

		//List<Application> apps = appService.getAppsByPageAndType(2,5,"1");
		String aa = "SFDASD";

	}
	@Test
	public void testInsert(){
		List<Application> apps = new ArrayList<>();
		for (int i = 0; i <= 40;i++){
			Application app = new Application();
			app.setAppId("10"+i);
			app.setAppName("name"+i);
			app.setSeq(10+i);
			app.setCreateTime(new Date());
			app.setType(""+(i%2+1));
			app.setSp("N"+i);
			app.setUrl("baidu.com");
			apps.add(app);
		}
		//appService.insertApps(apps);
	}

	@Test
	public void testNavigationService() {
		System.out.println(navigationService.getNavigationHtml());
	}
}

