package cn.cslg.microblog.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.cslg.microblog.PO.Microblog;
import cn.cslg.microblog.PO.User;

@Service("searchService")
public class SearchServiceImpl implements SearchService {
	@Resource
	private UserService userService;
	
	@Resource
	private RemarkService remarkService;
	
	@Resource
	private FollowService followService;

	@Resource
	private MicroblogService microblogService;
	
	@Override
	public HashMap<String, List> searchResult(String value) {
		List<String> search = new ArrayList<>();
		for(int i=0;i<value.length();i++){
			search.add(String.valueOf(value.charAt(i)));
		}
		HashMap<String,List> map = new HashMap<String,List>();
		List<User> users = this.userService.selectIllegibilityByName(search);
		for(int i=0;i<users.size();i++){
			users.get(i).setFollows(this.followService.countFollows(users.get(i)));
			users.get(i).setFollowers(this.followService.countFollowers(users.get(i)));
			users.get(i).setMicroblogs(this.microblogService.countAll(users.get(i)));
		}
		List<Microblog> microblogs = this.microblogService.selectIllegibilityByContent(search);
		for(int i=0;i<microblogs.size();i++){
			Microblog microblogTemp = microblogs.get(i);
			microblogs.get(i).setRemarkSys();
			microblogs.get(i).setRemarks(remarkService.findByMicroblogid(microblogTemp.getId()));
			if (!microblogs.get(i).getIsForward()) {
				microblogs.get(i).setForwards(this.microblogService.countBySourceMicroblogId(microblogTemp.getId()));
			} else {
				microblogs.get(i).setForwards(this.microblogService.countByForwardMicroblogId(microblogTemp.getId()));
			}
		}

		users = (users.size() == 0)?null:users;
		microblogs = (microblogs.size() == 0)?null:microblogs;
		if(users == null && microblogs == null){
			return null;
		}else {
			map.put("users", users);
			map.put("microblogs", microblogs);
			return map;
		}
	}

}
