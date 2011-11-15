<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Xisp</title>
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le styles -->
      <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"/>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.2.min.js"></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/application.js"></script>
	   <style type="text/css">
	      body {
	        padding-top: 60px;
	      }
	    </style>

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="images/favicon.ico">
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png">

    <link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png">
  </head>

  <body>

	<div class="topbar">
      <div class="topbar-inner">
        <div class="container-fluid">
          <a class="brand" href="${pageContext.request.contextPath}/projects/index">XISP <span class="projectTop">${currentProject.name }</span> -  <span class="iterationTop">${currentInteration.name}</span> </a>
           <p class="pull-right">${currentUser.name } <a href="${pageContext.request.contextPath}/login/logout">Sair</a></p>
        </div>
      </div>
    </div>

    <div class="container-fluid">
      <div class="sidebar">
        <div class="well">
          <h5>Inforaçoes Gerais</h5>
          <ul>
            <small><b>Dono: </b>${project.owner.name }</small><br /><br />
			<small><b>Cliente: </b>${project.client.name }</small>
          </ul>
          
		<h5><fmt:message key="project.show.participantes"></fmt:message></h5>
          <ul>
			  <c:choose>
				<c:when test="${empty project.users }">
					<p style="color: #6BBA70;"><fmt:message key="project.show.semusuario"></fmt:message></p>
					<a href="${pageContext.request.contextPath}/users/newUser">Add Usuarios</a>
				</c:when>
				<c:otherwise>
					<c:forEach items="${project.users }" var="user">
						<small style="font-size: 13px;"><a href="${pageContext.request.contextPath}/users/${ user.id }">${user.name }</a></small>
			       		<form id="user" name="addParticipante" action="<c:url value="/projects/${project.id}/removeParticipantes/"/>" method="post">
							<button type="submit" style="color: #CC0000;">remover</button>
							<input type="hidden" name="participante.id" value="${user.id}">
						</form>
					</c:forEach>
				</c:otherwise>
			</c:choose>
          </ul>
          
          <h5>Acoes</h5>
          <ul>
            <form action="${pageContext.request.contextPath}/projects/${project.id}" method="post">
				<input type="hidden" name="_method" value="delete"/>
				<button type="submit" class="btn small" onclick="return confirm('Voce tem certeza? Ao remover um projeto todas iteracoes do mesmo tambem irao ser deletadas.')">Remover</button>
			</form>
            <a href="${pageContext.request.contextPath}/projects/${project.id}/edita"class="btn small" >Editar</a>
          </ul>

        </div>
      </div>
      <div class="content">
        <!-- Main hero unit for a primary marketing message or call to action -->
        <div class="hero-unit">
