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
				<br />
			</div>
		</div>
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                    <c:choose>
                        <c:when test="${userScanfollowDetail.size()>0}">
                            <c:choose>
                                <c:when test="${isFollow==1}">
                                        ${UserScan.name}共关注${userScanfollowDetail.size()}位用户
                                </c:when>
                                <c:otherwise>
                                        ${UserScan.name}共有${userScanfollowDetail.size()}位粉丝
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="panel-body">
                        <div class="list-group">
                            <c:forEach items="${userScanfollowDetail}" var="user">
                                <div class="list-group-item blog"	>
                                    <a href="user_scan.action?id=${user.id}">${user.name}</a>
                                    <c:choose>
                                    <c:when test="${user.id != sessionScope.User.id}">
                                        <c:choose>
                                            <c:when test="${followList.contains(user.id)}">
                                                <button class="btn btn-info" id="cf${user.id }" onclick="changeFollowers('${user.id}')" >取消关注</button>
                                            </c:when>
                                            <c:otherwise>
                                                <button class="btn btn-info" id="cf${user.id }" onclick="changeFollowers('${user.id}')">关注</button>
                                            </c:otherwise>
                                        </c:choose>
                                            <br/>
                                            关注：<a href="user_scan_follows.action?id=${user.id}&isFollow=1">${user.follows }</a>&nbsp;&nbsp;|&nbsp;&nbsp;
                                    </c:when>
                                    <c:otherwise>
                                            <br/>
                                        关注：<a href="user_scan_follows.action?id=${user.id}&isFollow=1"><span id="currentuser">${user.follows }</span></a>&nbsp;&nbsp;|&nbsp;&nbsp;
                                    </c:otherwise>
                                    </c:choose>
                                    粉丝：<a href="user_scan_follows.action?id=${user.id}&isFollow=0"><span id="followers${user.id}">${user.followers }</span></a>&nbsp;&nbsp;|&nbsp;&nbsp;
                                    微博：<a href="user_scan.action?id=${user.id}">${user.microblogs }</a>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                        </c:when>
                        <c:otherwise>
                            提示信息</div>
                            <div class="panel-body"> 
                                <c:choose>
                                    <c:when test="${isFollow==1}">
                                            ${UserScan.name}谁也没关注，这可能是个测试账号。
                                    </c:when>
                                    <c:otherwise>
                                            ${UserScan.name}没有粉丝，这可能是个测试账号。
                                    </c:otherwise>
                                </c:choose>
                            </div>
                       </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</body>
</html>