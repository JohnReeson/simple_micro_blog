package cn.cslg.microblog.Service;

import java.io.UnsupportedEncodingException;

import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Service;

import cn.cslg.microblog.PO.User;
import cn.cslg.microblog.Util.Mail;

@Service("mailService")
public class MailServiceImpl implements MailService {

	@Override
	public void sendActivecode(User user) {
		Mail mail = new Mail("mailserver","emailaddress","simple microBlog register",
				"emailaddress","password",true);
		StringBuffer sf=new StringBuffer();  
        sf.append("http://localhost:8080/microBlog/user_active.action?name=");  
        try {
			sf.append(java.net.URLEncoder.encode(user.getName(), "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}  
        sf.append("&password=");  
        sf.append(user.getPassword());
        sf.append("&activecode=");  
        sf.append(user.getActivecode());  
		try {
			mail.sendSimpleMail(user.getEmail(),user.getName(),"邮箱激活",sf.toString());
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendResetPassword(User user) {
		Mail mail = new Mail("mailserver","emailaddress","simple microBlog register",
				"emailaddress","password",true);
		StringBuffer sf=new StringBuffer();  
        sf.append("http://localhost:8080/microBlog/user_showResetPassword.action?name=");  
        try {
			sf.append(java.net.URLEncoder.encode(user.getName(), "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}  
		try {
			mail.sendSimpleMail(user.getEmail(),user.getName(),"密码重置",sf.toString());
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

}
