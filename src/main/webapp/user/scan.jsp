<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html >
<html>
<head>
<title>用户详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
	<script src="../ot/jquery-2.0.0.min.js"></script>
	<script src="../ot/bootstrap.min.js"></script>  
	<link href="../ot/bootstrap.min.css" rel="stylesheet" media="screen" >
	
	<script src="js/m_index.js?ver=20190612" ></script> 
 	<link href="css/m_index.css?ver=20190612" rel="stylesheet" >
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top" >
		<div class="container" >
			<div class="navbar-header">
					<a class="navbar-brand" href="javascript:void(0)">
						<img alt="Brand" src="img/logo.png" style="width:35px;margin-top:-8px;">
					</a>
					<span id="logo"><i>weibo</i></span>
			</div>
			<form class="navbar-form navbar-left" id="search_form" action="user_search.action" method="post" role="search">
				<div class="input-group">
					<input type="text" name="value" id="search_input" class="form-control" style="width:420px;"placeholder="Search">
					<span class="input-group-btn">
							<button class="btn btn-info" type="submit"><span class="glyphicon glyphicon-search" style="color: white;"></span></button>
						</span>
					</div>
				</form>
			<ul class="nav navbar-nav navbar-right" id="slink">
				<li><a href="microblog_showAll.action">
					<span class="glyphicon glyphicon-home" ></span><span>&nbsp;&nbsp;主页</span></a>
				</li>
				<li><a href="user_scan.action?id=${sessionScope.User.id}">
					<span class="glyphicon glyphicon-user" ></span><span>&nbsp;&nbsp;${sessionScope.User.name}</span></a>
				</li>
				<li><a href="user_logout.action">
					<span class="glyphicon glyphicon-log-out" ></span><span>&nbsp;&nbsp;注销</span></a>
				</li>
			</ul>
		</div>
</nav>
	<div class="container" style="margin-top:80px">
		<div class="row">
    		<div class="col-md-8 col-md-offset-2 user_info" >
    			<img src="img/user_avatar.png" style="width:100px;"/><br/><br/>
				${UserScan.name }
				<c:if test="${UserScan.id != sessionScope.User.id }">
					<c:choose>
					   <c:when test="${Follow}">
					   		<button class="btn btn-info" id="cf${UserScan.id }" onclick="changeFollowers('${UserScan.id}')" >取消关注</button>
					   </c:when>
					   <c:otherwise>
					   		<button class="btn btn-info" id="cf${UserScan.id }" onclick="changeFollowers('${UserScan.id}')" >关注</button>
					   </c:otherwise>
					</c:choose>
				</c:if>
				<br />
				关注：<a href="user_scan_follows.action?id=${UserScan.id}&isFollow=1">${Follows}</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				粉丝：<a href="user_scan_follows.action?id=${UserScan.id}&isFollow=0"><span id="followers${UserScan.id}">${Followers }</span></a>&nbsp;&nbsp;|&nbsp;&nbsp;
				微博：<a href="user_scan.action?id=${UserScan.id}">${Microblogs.size() }</a>
				<br/>
			</div>
		</div>
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<c:if test="${Microblogs.size()==0 }">
					<div class="panel panel-primary">
		  				<div class="panel-heading">提示信息</div>
			  			<div class="panel-body">
			  				<c:choose>
			  				<c:when test="${UserScan.id != sessionScope.User.id }">
			  					此用户暂未发表微博
			  				</c:when>
			  				<c:otherwise>
			  					还没有微博，赶快发表吧。
			  				</c:otherwise>
			  				</c:choose>
			  				<br/>
		  				</div>
					</div>
				</c:if>
				<c:forEach items="${Microblogs}" var="m">
				<div class="list-group blog_content">
					<div class="list-group-item blog">
					<c:choose>
						<c:when test="${sessionScope.User.id == UserScan.id && !m.isForward}">
							<a href="user_scan.action?id=${m.sourceUser.id}">${m.sourceUser.name}</a><br/>
							<span class="time">${m.getNTime()}</span><br/>
							${m.content}
								</div>
							<div class="list-group-item self_operation">
								<a href="microblog_delete.action?id=${m.id}" ><span class="glyphicon glyphicon-trash"></span></a>|
						</c:when>
					   	<c:otherwise>
					   		<c:choose>
						   		<c:when test="${sessionScope.User.id == UserScan.id}">
						   			<a href="user_scan.action?id=${sessionScope.User.id}">${sessionScope.User.name}</a>
						   			<br/><span class="time">${m.getNTime()}</span>
						   			<br/>${m.forwardRemark }
						   			<span class="rf"><c:forEach items="${m.forwardRemarks}" var="r">
						   				<a href="user_scan.action?id=${r.userId}">//@${r.userName}</a>
						   			    :${r.remark }
						   			</c:forEach>
						   			</span>
						   			<div class="list-group-item original">
						   				<a href="user_scan.action?id=${m.sourceUser.id}">@${m.sourceUser.name}</a>
						   				<br/>${m.content}
						   	 		</div>
									</div>
									<div class="list-group-item self_operation">
										<a href="microblog_delete.action?id=${m.id}"><span class="glyphicon glyphicon-trash"></span></a>|
								</c:when>
						
						   	 	<c:otherwise>
						   	 		<a href="user_scan.action?id=${m.user.id}">${m.user.name}</a><br />
						   	 		<c:choose>
							   	 		<c:when test="${m.forwardRemark == null || m.forwardRemark.equals('')}">
							   	 			<span class="time">${m.getNTime()}</span><br/>
							   	 			${m.content}
											</div>
											<div class="list-group-item follower_operation">
							   	 		</c:when>
							   	 		
							   	 		<c:otherwise>
							   	 		<span class="time">${m.getNTime()}</span><br/>
							   	 			${m.forwardRemark }
								   			<span class="rf"><c:forEach items="${m.forwardRemarks}" var="r">
								   				<a href="user_scan.action?id=${r.userId}">//@${r.userName }</a>
								   			   :${r.remark }
								   			</c:forEach>
								   			</span>
								   			<div class="list-group-item original">
								   				<a href="user_scan.action?id=${m.sourceUser.id}">@${m.sourceUser.name }</a>
								   				<br/>${m.content}
											</div>
											</div>
							<div class="list-group-item follower_operation">
							   	 		</c:otherwise>
							   	 	</c:choose>
						   	 	</c:otherwise>
						   	 </c:choose>
						</c:otherwise>
					</c:choose>	
					<a href="javascript:toggleRemark(${m.id})">
						<span class="glyphicon glyphicon-comment"></span>&nbsp;<span id="remarkSize${m.id }">${m.getRemarks().size()}</span>
					</a>|
					<a href="javascript:showForward(${m.id },${m.sourceMicroblogId==0?m.id:m.sourceMicroblogId })" >
						<span class="glyphicon glyphicon-share"></span>&nbsp;<span id="forwardSize${m.id}">${m.getForwards()}</span>
					</a>
					</div>
					<div class="remark_show">
						<input type="hidden" id="h${m.id }" value="0">
						<div class="well collapse" id="${m.id }">
					</div>
				</div>
				</div>
			</c:forEach>
			</div>
		</div>
	</div>
	<div class="modal fade" id="forwardModal" tabindex="-1" role="dialog" aria-labelledby="forwordModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="forwordModalLabel">转发微博</h4>
	      </div>
	      <div class="modal-body">
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>