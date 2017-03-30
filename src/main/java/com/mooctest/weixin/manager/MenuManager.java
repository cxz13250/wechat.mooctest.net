package com.mooctest.weixin.manager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mooctest.weixin.menu.Button;
import com.mooctest.weixin.menu.ClickButton;
import com.mooctest.weixin.menu.ComplexButton;
import com.mooctest.weixin.menu.Menu;
import com.mooctest.weixin.menu.ViewButton;
import com.mooctest.weixin.pojo.Token;
import com.mooctest.weixin.util.CommonUtil;
import com.mooctest.weixin.util.MenuUtil;



/**
 * 菜单管理器类
 * 
 * @author cxz
 * @date 2017-03-21
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		//String appId = "wx9239e3bedd4aa66c";//慕测平台公众号
		// 第三方用户唯一凭证密钥
		//String appSecret = "3928d0aaa4699f132a3ac8380e60d84d";
		
		String appId = "wx9239e3bedd4aa66c";//课堂助手公众号
		String appSecret = "c0a74230fcb06331bd9b1ef6cfaa1127";
		
		// 调用接口获取凭证
		Token token = CommonUtil.getToken(appId, appSecret);
		createMenu(token);
		viewMenu(token);
	}

	/**
	 * 定义菜单结构
	 * 
	 * @return Menu
	 */
	private static Menu initMoocMenu(){

		ClickButton btn11=new ClickButton();
		btn11.setName("任务密码");
		btn11.setKey("taskpwd");
		btn11.setType("click");
		
		ClickButton btn12=new ClickButton();
		btn11.setName("我的群组");
		btn11.setKey("mygroup");
		btn11.setType("click");
		
		ClickButton btn13=new ClickButton();
		btn11.setName("成绩查询");
		btn11.setKey("taskgrade");
		btn11.setType("click");
				
		ComplexButton btn1=new ComplexButton();
		btn1.setName("慕测");
		btn1.setSub_button(new Button[]{btn11,btn12,btn13});
		
		ClickButton btn21=new ClickButton();
		btn21.setName("小测");
		btn21.setKey("quiz");
		btn21.setType("click");
		
		ClickButton btn22=new ClickButton();
		btn22.setName("点名");
		btn22.setKey("rollcall");
		btn22.setType("click");
		
		ClickButton btn23=new ClickButton();
		btn11.setName("成绩");
		btn11.setKey("exam_score");
		btn11.setType("click");
		
		ComplexButton btn2=new ComplexButton();
		btn2.setName("课堂");
		btn2.setSub_button(new Button[]{btn21,btn22,btn23});
		
		ClickButton btn31=new ClickButton();
		btn31.setName("账号");
		btn31.setType("click");
		btn31.setKey("account");
		
		ClickButton btn32=new ClickButton();
		btn32.setName("班级");
		btn32.setKey("clazz");
		btn32.setType("click");
		
		ComplexButton btn3=new ComplexButton();
		btn3.setName("我");
		btn3.setSub_button(new Button[]{btn31,btn32});
		
		Menu menu=new Menu();
		menu.setButton(new Button[]{btn11,btn1,btn2});
		return menu;
	}
	
	/**
	 * 定义菜单结构
	 * 
	 * @return Menu
	 */
	/**
	 * @return
	 */
	private static Menu getMoocMenu() {
		ClickButton btn11 = new ClickButton();
		btn11.setName("密码");
		btn11.setType("click");
		btn11.setKey("exam_password");

		ClickButton btn12 = new ClickButton();
		btn12.setName("成绩");
		btn12.setType("click");
		btn12.setKey("exam_score");

//		ClickButton btn13 = new ClickButton();
//		btn13.setName("小测统计");
//		btn13.setType("click");
//		btn13.setKey("quiz_statistics");

		ComplexButton btn1 = new ComplexButton();
		btn1.setName("考试");
		btn1.setSub_button(new Button[] { btn11, btn12 });
		
		ClickButton btn21 = new ClickButton();
		btn21.setName("小测");
		btn21.setType("click");
		btn21.setKey("quiz");

		ClickButton btn22 = new ClickButton();
		btn22.setName("点名");
		btn22.setType("click");
		btn22.setKey("rollcall");
		
		ComplexButton btn2 = new ComplexButton();
		btn2.setName("课堂");
		btn2.setSub_button(new Button[] { btn21, btn22 });

//		ClickButton btn21 = new ClickButton();
//		btn21.setName("单选");
//		btn21.setType("click");
//		btn21.setKey("quiz_single");
//
//		ClickButton btn22 = new ClickButton();
//		btn22.setName("多选");
//		btn22.setType("click");
//		btn22.setKey("quiz_multiple");
//
//		ClickButton btn23 = new ClickButton();
//		btn23.setName("其他");
//		btn23.setType("click");
//		btn23.setKey("quiz_other");
//
//		ComplexButton btn2 = new ComplexButton();
//		btn2.setName("小测");
//		btn2.setSub_button(new Button[] { btn21, btn22, btn23 });

		
//		ViewButton btn22 = new ViewButton();
//		btn22.setName("京东");
//		btn22.setType("view");
//		btn22.setUrl("http://m.jd.com");
		
		ViewButton btn31 = new ViewButton();
		String registerUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9239e3bedd4aa66c&redirect_uri=http%3A%2F%2Fmooctest.net%2FMOOCWechat%2FWitestRegister&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		btn31.setName("注册");
		btn31.setType("view");
		btn31.setUrl(registerUrl);

		ViewButton btn32 = new ViewButton();
		String loginUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9239e3bedd4aa66c&redirect_uri=http%3A%2F%2Fmooctest.net%2FMOOCWechat%2FWitestLogin&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		btn32.setName("关联");
		btn32.setType("view");
		btn32.setUrl(loginUrl);

		ClickButton btn33 = new ClickButton();
		btn33.setName("吐槽");
		btn33.setType("click");
		btn33.setKey("service");

		ComplexButton btn3 = new ComplexButton();
		btn3.setName("我");
		btn3.setSub_button(new Button[] { btn31, btn32, btn33 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { btn1, btn2, btn3 });

		return menu;
	}
	
	private static void viewMenu(Token token) {
		if (null != token) {
			// 查看菜单
			String result = MenuUtil.getMenu(token.getAccessToken());
			if (result != null) {
				log.info(result);
			}
		}
	}

	private static void createMenu(Token token) {
		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(initMoocMenu(),
					token.getAccessToken());

			// 判断菜单创建结果
			if (result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败！");
		}
	}

	private static void deleteMenu(Token token) {
		if (null != token) {
			// 删除菜单
			boolean result = MenuUtil.deleteMenu(token.getAccessToken());

			// 判断菜单创建结果
			if (result)
				log.info("菜单删除成功！");
			else
				log.info("菜单删除失败！");
		}
	}
	
	private static Menu getTestMenu() {
		ClickButton btn11 = new ClickButton();
		btn11.setName("密码");
		btn11.setType("click");
		btn11.setKey("exam_password");

		ClickButton btn12 = new ClickButton();
		btn12.setName("成绩");
		btn12.setType("click");
		btn12.setKey("exam_score");

		ClickButton btn13 = new ClickButton();
		btn13.setName("小测统计");
		btn13.setType("click");
		btn13.setKey("quiz_statistics");

		ComplexButton btn1 = new ComplexButton();
		btn1.setName("考试");
		btn1.setSub_button(new Button[] { btn11, btn12, btn13 });

		ClickButton btn21 = new ClickButton();
		btn21.setName("单选");
		btn21.setType("click");
		btn21.setKey("quiz_single");

		ClickButton btn22 = new ClickButton();
		btn22.setName("多选");
		btn22.setType("click");
		btn22.setKey("quiz_multiple");

		ClickButton btn23 = new ClickButton();
		btn23.setName("其他");
		btn23.setType("click");
		btn23.setKey("quiz_other");

		ComplexButton btn2 = new ComplexButton();
		btn2.setName("小测");
		btn2.setSub_button(new Button[] { btn21, btn22, btn23 });

		ClickButton btn31 = new ClickButton();
		btn31.setName("注册");
		btn31.setType("click");
		btn31.setKey("register");

		ClickButton btn32 = new ClickButton();
		btn32.setName("关联");
		btn32.setType("click");
		btn32.setKey("binding");

		ClickButton btn33 = new ClickButton();
		btn33.setName("客服");
		btn33.setType("click");
		btn33.setKey("service");

		ComplexButton btn3 = new ComplexButton();
		btn3.setName("我");
		btn3.setSub_button(new Button[] { btn31, btn32, btn33 });
		
		ClickButton btn4 = new ClickButton();
		btn4.setName("test4");
		btn4.setType("click");
		btn4.setKey("test4");
		
		ClickButton btn5 = new ClickButton();
		btn4.setName("test5");
		btn4.setType("click");
		btn4.setKey("test5");
		
		ClickButton btn6 = new ClickButton();
		btn4.setName("test6");
		btn4.setType("click");
		btn4.setKey("test6");

		Menu menu = new Menu();
		menu.setButton(new Button[] { btn1, btn2, btn3, btn4, btn5, btn6 });

		return menu;
	}
	
	/*
	private static Menu getMenu() {
		ClickButton btn11 = new ClickButton();
		btn11.setName("开源中国");
		btn11.setType("click");
		btn11.setKey("oschina");

		ClickButton btn12 = new ClickButton();
		btn12.setName("ITeye");
		btn12.setType("click");
		btn12.setKey("iteye");

		ViewButton btn13 = new ViewButton();
		btn13.setName("CocoaChina");
		btn13.setType("view");
		btn13.setUrl("http://www.iteye.com");

		ViewButton btn21 = new ViewButton();
		btn21.setName("淘宝");
		btn21.setType("view");
		btn21.setUrl("http://m.taobao.com");

		ViewButton btn22 = new ViewButton();
		btn22.setName("京东");
		btn22.setType("view");
		btn22.setUrl("http://m.jd.com");

		ViewButton btn23 = new ViewButton();
		btn23.setName("唯品会");
		btn23.setType("view");
		btn23.setUrl("http://m.vipshop.com");

		ViewButton btn24 = new ViewButton();
		btn24.setName("当当网");
		btn24.setType("view");
		btn24.setUrl("http://m.dangdang.com");

		ViewButton btn25 = new ViewButton();
		btn25.setName("苏宁易购");
		btn25.setType("view");
		btn25.setUrl("http://m.suning.com");

		ViewButton btn31 = new ViewButton();
		btn31.setName("多泡");
		btn31.setType("view");
		btn31.setUrl("http://www.duopao.com");

		ViewButton btn32 = new ViewButton();
		btn32.setName("一窝88");
		btn32.setType("view");
		btn32.setUrl("http://www.yi588.com");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("技术交流");
		mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("购物");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24, btn25 });

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("网页游戏");
		mainBtn3.setSub_button(new Button[] { btn31, btn32 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
	*/
	
	private static Menu getMoocMenu112() {
		ClickButton btn11 = new ClickButton();
		btn11.setName("密码");
		btn11.setType("click");
		btn11.setKey("exam_password");

		ClickButton btn12 = new ClickButton();
		btn12.setName("成绩");
		btn12.setType("click");
		btn12.setKey("exam_score");

		ClickButton btn13 = new ClickButton();
		btn13.setName("小测统计");
		btn13.setType("click");
		btn13.setKey("quiz_statistics");

		ComplexButton btn1 = new ComplexButton();
		btn1.setName("考试");
		btn1.setSub_button(new Button[] { btn11, btn12 });

		ClickButton btn21 = new ClickButton();
		btn21.setName("单选");
		btn21.setType("click");
		btn21.setKey("quiz_single");

		ClickButton btn22 = new ClickButton();
		btn22.setName("多选");
		btn22.setType("click");
		btn22.setKey("quiz_multiple");

		ClickButton btn23 = new ClickButton();
		btn23.setName("其他");
		btn23.setType("click");
		btn23.setKey("quiz_other");

		ComplexButton btn2 = new ComplexButton();
		btn2.setName("小测");
		btn2.setSub_button(new Button[] { btn21, btn22, btn23 });

		ClickButton btn31 = new ClickButton();
		btn31.setName("注册");
		btn31.setType("click");
		btn31.setKey("register");

		ClickButton btn32 = new ClickButton();
		btn32.setName("关联");
		btn32.setType("click");
		btn32.setKey("binding");

		ClickButton btn33 = new ClickButton();
		btn33.setName("客服");
		btn33.setType("click");
		btn33.setKey("service");

		ComplexButton btn3 = new ComplexButton();
		btn3.setName("我");
		btn3.setSub_button(new Button[] { btn31, btn32, btn33 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { btn1, btn2, btn3 });

		return menu;
	}
}
