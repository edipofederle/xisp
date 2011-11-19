<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>xisp</title>
    <meta name="description" content="">
    <meta name="edipo" content="">

    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le styles -->
    
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.16.custom.min.js"></script>
	   	   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker-pt-BR.js"></script>
   	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.puts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.tablesorter.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="images/favicon.ico">
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png">
    
    <script>
    $(document).ready(function() {
		$.datepicker.setDefaults($.datepicker.regional['pt-BR']);
	});
    </script>
  </head>
  
  <style>
  	span.userSession{
  		float:right;
  		margin-left:200px;
  		font-size:14px;
  		color:white;
  		margin-top:10px;
  	}
  	
  	b.sessionProject{
  		color:white;
  	}
  	
#formNewUser{
	display: none;
}


.my_projects {
background-color: #333333;
    border: 1px solid #333333;
    border-radius: 10px 10px 0 0;
    color: #A0A4A0;
    font-size: 10px;
    height: 37px;
    line-height: 37px;
    overflow: hidden;
    text-align: center;
    text-transform: uppercase;
}

#proj{
	float:left;
	width: 200px;
}

.my_projects h3 {
    color: #FFFFFF;
    font-size: 13px;
    font-weight: bold;
    margin-left:10px;
    margin-top: 1px !important;
    text-align: left;
}

span.title{
	padding-left:10px;
}


#teste{
	float:left;
	margin-left:100px;
}

span#actions a{
	color:black;
	font-weight: bold;
}

b.days{
	
	font-size:16px;
	font-weight: bold;
	
}

.label {
  padding: 1px 3px 2px;
  background-color: #bfbfbf;
  font-size: 9.75px;
  font-weight: bold;
  color: #ffffff;
  text-transform: uppercase;
  -webkit-border-radius: 3px;
  -moz-border-radius: 3px;
  border-radius: 3px;
}
.label.important {
  background-color: #c43c35;
}
.label.warning {
  background-color: #f89406;
}
.label.success {
  background-color: #46a546;
}
.label.notice {
  background-color: #62cffc;
}

.formDescStory{
	width: 400px;
	height: 100px;
  	
  </style>

  <body>
	<div class="topbar">
      <div class="topbar-inner">
        <div class="container-fluid">
          <a class="brand" href="${pageContext.request.contextPath}/projects/index">XISP <span class="projectTop">${currentProject.name }</span> -  <span class="iterationTop">${currentInteration.name}</span> </a>
           <p class="pull-right" style="color: white;">${currentUser.email } <b><a href="${pageContext.request.contextPath}/login/logout"><fmt:message key="xisp.logout"/></a></b></p>
       	           <ul class="nav">
            <li><a href="${pageContext.request.contextPath}/stories/${currentProject.id }/index"><fmt:message key="projects.userstories"/></a></li>
            <li><a href="${pageContext.request.contextPath}/interations/index"><fmt:message key="projects.iterations"/></a></li>
            <li><a href="${pageContext.request.contextPath}/users/index"><fmt:message key="projects.users"/></a></li>
          </ul>
        </div>
      </div>
    </div>
    

      <div class="content">
      	<div class="hero-unit">
