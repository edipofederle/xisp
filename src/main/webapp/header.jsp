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
    <link href="${pageContext.request.contextPath}/bootstrap-1.1.1.css" rel="stylesheet">
    
   	<script type="text/javascript" src="<c:url value="../js/jquery-1.6.2.min.js"/>"></script>
   	<script type="text/javascript" src="<c:url value="/js/jquery-1.6.2.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="../js/jquery.validate.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/jquery.validate.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../js/application.js"/>"></script>
     <script type="text/javascript" src="<c:url value="/js/application.js"/>"></script>
    <script type="text/javascript" src="<c:url value="../js/jquery.tablesorter.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/jquery.tablesorter.min.js"/>"></script>

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="images/favicon.ico">
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png">
  </head>
  
  <style>
  	span.userSession{
  		float:right;
  		margin-left:200px;
  		font-size:14px;
  		color:white;
  		margin-top:10px;
  	}
  </style>

  <body>
	
	<div class="topbar">
      <div class="fill">
        <div class="container">
          <h3><a href="#">xisp</a></h3>
          <ul>
            <li class="active">
            	<a href="${pageContext.request.contextPath}/projects/index">Projects</a>
            </li>
            <li><a href="${pageContext.request.contextPath}/users/index">Users</a></li>
            <li><a href="#about">Stories</a></li>
            <li><a href="#about">Iterations</a></li>
            <li><a href="#about">Users</a></li>
            <li><a href="#about">Teams</a></li>
            <li><a href="#about">Release</a></li>
            <li><a href="#contact">About</a></li>
          </ul>
          <span class="userSession">${currentUser.name } <a href="${pageContext.request.contextPath}/login/logout"><b class="userSession">Sair</b></a></span>
        </div>
      </div>
    </div>
    
    <div class="container">
	<div class="hero-unit">