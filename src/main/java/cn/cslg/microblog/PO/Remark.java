package cn.cslg.microblog.PO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Remark {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column remark.id
     *
     * @mbggenerated
     */
    private Integer id;
    
    /**
     * 这条评论属于的用户的id
     */
    private Integer userId;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column remark.userId
     * 是谁评论的
     * @mbggenerated
     */
    private User user;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column remark.microblogId
     * 属于哪条微博
     * @mbggenerated
     */
    private Integer microblogid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column remark.isReply
     * 是否属于回复别人的（是评论别人的为true，否则为false）
     * @mbggenerated
     */
    private Boolean isreply;
    
    /**
     * 这条评论回复的评论的用户的id
     */
    private Integer replyId;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column remark.replyId
     * 如果是回复他人的，代表回复人，否则为null
     * @mbggenerated
     */
    private User replyUser;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column remark.time
     * 评论时间
     * @mbggenerated
     */
    private Date time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column remark.isRead
     * 是否看过（看过为true，没看过为false）
     * @mbggenerated
     */
    private Boolean isread;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column remark.content
     * 评论内容
     * @mbggenerated
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column remark.id
     *
     * @return the value of remark.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column remark.id
     *
     * @param id the value for remark.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column remark.microblogId
     *
     * @return the value of remark.microblogId
     *
     * @mbggenerated
     */
    public Integer getMicroblogid() {
        return microblogid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column remark.microblogId
     *
     * @param microblogid the value for remark.microblogId
     *
     * @mbggenerated
     */
    public void setMicroblogid(Integer microblogid) {
        this.microblogid = microblogid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column remark.isReply
     *
     * @return the value of remark.isReply
     *
     * @mbggenerated
     */
    public Boolean getIsreply() {
        return isreply;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column remark.isReply
     *
     * @param isreply the value for remark.isReply
     *
     * @mbggenerated
     */
    public void setIsreply(Boolean isreply) {
        this.isreply = isreply;
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column remark.time
     *
     * @return the value of remark.time
     *
     * @mbggenerated
     */
    public Date getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column remark.time
     *
     * @param time the value for remark.time
     *
     * @mbggenerated
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column remark.isRead
     *
     * @return the value of remark.isRead
     *
     * @mbggenerated
     */
    public Boolean getIsread() {
        return isread;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column remark.isRead
     *
     * @param isread the value for remark.isRead
     *
     * @mbggenerated
     */
    public void setIsread(Boolean isread) {
        this.isread = isread;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column remark.content
     *
     * @return the value of remark.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column remark.content
     *
     * @param content the value for remark.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getReplyUser() {
		return replyUser;
	}

	public void setReplyUser(User replyUser) {
		this.replyUser = replyUser;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getReplyId() {
		return replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
    
	/**
	 * 获取正常的日期格式
	 * @return
	 */
	public String getNTime(){
    	DateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss"); 
    	String s = sdf.format(time);
    	return s;
    }
}