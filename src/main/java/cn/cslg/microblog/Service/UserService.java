package cn.cslg.microblog.Service;

import cn.cslg.microblog.PO.User;

public interface UserService {
	public User findById(int id);
	
	public Boolean nameExist(String name);
	
	public Boolean emailExist(String email);
	
	public Boolean register(User user);

	public boolean active(User user);

	public User findByName(String name);

	public boolean checkNameAndEmail(User user);

	public void resetPassword(User user);

	public boolean isExist(String name);

	public boolean isFindPassword(User user);

	public User findByEmail(String email);

}
