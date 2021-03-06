package cn.cslg.microblog.Controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import org.apache.jasper.tagplugins.jstl.core.Out;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
// import org.springframework.web.portlet.ModelAndView;

import cn.cslg.microblog.PO.Microblog;
import cn.cslg.microblog.PO.User;
import cn.cslg.microblog.Service.FollowService;
import cn.cslg.microblog.Service.MailService;
import cn.cslg.microblog.Service.MicroblogService;
import cn.cslg.microblog.Service.SearchService;
import cn.cslg.microblog.Service.UserService;
import cn.cslg.microblog.Util.MD5;
@Controller
@SessionAttributes({"User"})		//此处定义此Controller中将要创建和使用哪些session中的对象名
public class UserController {
	@Resource
	private UserService userService;
	
	@Resource
	private MailService mailService;
	
	@Resource
	private MicroblogService microblogService;
	
	@Resource
	private FollowService followService;
	
	@Resource
	private SearchService searchService;
	
	@RequestMapping("user_isExist")
	public void exist(String name, HttpServletResponse httpServletResponse){
		try {
			if(this.userService.isExist(name)){
				httpServletResponse.getWriter().print("1");
			}else {
				httpServletResponse.getWriter().print("0");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("user_isFindPassword")
	public void isFindPassword(User user, HttpServletResponse httpServletResponse){
		try {
			if(this.userService.isFindPassword(user)){
				httpServletResponse.getWriter().println("1");
			}else {
				httpServletResponse.getWriter().println("0");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("user_emailCheck")
	public void emailCheck(User user, HttpServletResponse httpServletResponse, ModelMap modelMap){	
		//modelMap自动与session对应，你在往modelmap中添加对应属性便是往session中添加属性（前提是你已经在@SessionAttributes注解中定义好）
		User userTemp = this.userService.findByEmail(user.getEmail());
		try {
			if(userTemp != null){
				httpServletResponse.getWriter().println("1");
			}else {
				httpServletResponse.getWriter().println("0");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("user_loginCheck")
	public void loginCheck(User user, HttpServletResponse httpServletResponse, ModelMap modelMap){	
		//modelMap自动与session对应，你在往modelmap中添加对应属性便是往session中添加属性（前提是你已经在@SessionAttributes注解中定义好）
		User userTemp = this.userService.findByName(user.getName());
		try {
			if(userTemp==null){
				httpServletResponse.getWriter().println("0");
				return ;
			}
			if((userTemp.getState() == 1) && (userTemp.getPassword().equals(MD5.md5(user.getPassword())))){
				modelMap.addAttribute("User", userTemp);		//成功将userTemp存入session中
				httpServletResponse.getWriter().println("1");
			}else {
				httpServletResponse.getWriter().println("0");		
			}
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("user_register")
	public String register(User user, HttpServletRequest request){
		if(this.userService.register(user)){
			this.mailService.sendActivecode(user);
			request.setAttribute("visited","exist");
			return "registerSuccess";
		}else {
			return "index";
		}
	}
	
	@RequestMapping("user_active")
	public String active(User user, HttpServletRequest request){
		request.setAttribute("visited","exist");
		if(this.userService.active(user)){
			return "activeSuccess";
		}else {
			return "activeError";
		}
	}
	
	@RequestMapping("user/user_logout")
	public String logout(@ModelAttribute("User") User user, SessionStatus sessionStatus){	
		//@ModelAttribute("User")相当于将session中名为"User"的对象注入user对象中
		//sessionStatus中的setComplete方法可以将session中的内容全部清空
		sessionStatus.setComplete();
		return "redirect:../index.jsp";
	}
	
	@RequestMapping("user_findPassword")
	public String findPassword(User user, HttpServletRequest request){
		request.setAttribute("visited","exist");
		if(userService.checkNameAndEmail(user)){
			this.mailService.sendResetPassword(user);
			return "findPasswordSuccess";
		}else {
			return "findPassword";
		}
	}
	
	@RequestMapping("user_showResetPassword")
	public String showResetPassword(User user){
		return "showResetPassword";
	}
	
	@RequestMapping("user_resetPassword")
	public String resetPassword(User user,HttpServletRequest request){
		request.setAttribute("visited","exist");
		userService.resetPassword(user);
		return "resetPasswordSuccess";
	}

	@RequestMapping("user/user_scan")
	public String scan(User user1, ModelMap modelMap){
		user1 = userService.findById(user1.getId());
		User user2 = (User) modelMap.get("User");
		List<Microblog> microblogs = this.microblogService.getAllNotFollow(user1);
		Boolean follow = user1.getId()!=user2.getId() && this.followService.findFollowIsExist(user2, user1);	//是否是关注关系
		Integer follows = this.followService.countFollows(user1);				//关注者人数
		Integer followers = this.followService.countFollowers(user1);			//粉丝人数
		modelMap.addAttribute("Follow", follow);
		modelMap.addAttribute("UserScan", user1);
		modelMap.addAttribute("Follows", follows);
		modelMap.addAttribute("Followers", followers);
		modelMap.addAttribute("Microblogs", microblogs);
		return "/user/scan";
	}
	
	@RequestMapping("user/user_search")
	public String search(String value, ModelMap modelMap){
		if(value==null){
			return "/user/searchResult";
		}
		User user = (User) modelMap.get("User");
		modelMap.addAttribute("value", value);
		List<Integer> followList = this.followService.findFollowsByUser(user);
		modelMap.addAttribute("followList", followList);
		HashMap<String, List> result = this.searchService.searchResult(value);
		modelMap.put("result", result);
		return "/user/searchResult";
	}

	@RequestMapping("user/user_scan_follows")
	public String scanFollows(User user1, Integer isFollow,ModelMap modelMap){

		// 是查看关注还是粉丝
		modelMap.addAttribute("isFollow", isFollow);

		// 登录用户关注的所有用户
		User user = (User) modelMap.get("User");
		List<Integer> followList = this.followService.findFollowsByUser(user);
		modelMap.addAttribute("followList", followList);

		//用户详情界面属于哪个用户
		user1 = userService.findById(user1.getId());
		modelMap.addAttribute("UserScan", user1);

		List<Integer> followRelated = null;
		if(isFollow == 1){
			//这个用户关注的所有用户的基础信息
			followRelated = this.followService.findFollowsByUser(user1);
		}
		else{
			//这个用户的所有粉丝的信息
            followRelated = this.followService.findFollowersByUser(user1);
		}

		List<User> users = new ArrayList<User>();
		for (int id : followRelated) {
			User temp = this.userService.findById(id);
			temp.setFollows(this.followService.countFollows(temp));
			temp.setFollowers(this.followService.countFollowers(temp));
			temp.setMicroblogs(this.microblogService.countAll(temp));
			users.add(temp);
		}
		modelMap.addAttribute("userScanfollowDetail", users);
		return "/user/scanFollows";
	}

}
