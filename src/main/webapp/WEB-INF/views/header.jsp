<%@ page import="com.boun.semanticweb.model.User" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" session="true" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<%
	//allow access only if session exists
	String userName = null;
	Integer userType = 0;
	Integer totalScore = 0;
	User user = null;
	if(session.getAttribute("username") != null){
	    userName = (String) session.getAttribute("username");
	    userType = (Integer) session.getAttribute("userType");
        user = (User) session.getAttribute("user");
        totalScore = user.getTotalScore();
	}
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <META http-equiv=content-type content=text/html;charset=iso-8859-9>
    <META http-equiv=content-type content=text/html;charset=windows-1254>
    <META http-equiv=content-type content=text/html;charset=x-mac-turkish>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Welcome</title>

    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
    <link id="active_style" rel="stylesheet" href="${contextPath}/resources/css/bootswatch_flatly_bootstrap.min.css">

    <script src="${contextPath}/resources/js/jquery-2.2.1.min.js"></script>
    <script src="${contextPath}/resources/js/jquery-ui.js"></script>    
    <script src="${contextPath}/resources/js/bootstrap-3.3.2.min.js"></script>
        
    <script src="${contextPath}/resources/js/angular.min.js"></script>
    <script src="${contextPath}/resources/js/bootbox.min.js"></script>
    <script src="${contextPath}/resources/Controllers/app.js"></script>
    
    
</head>

<body>

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </c:if>
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
          <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="${contextPath}/">Linked Language</a>
            </div>

          <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

              <ul class="nav navbar-nav navbar-right">

              <% if(userName != null)
                      { 
              %>
                  <li class="dropdown">
                          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                              ${username} <span class="caret"></span></a>
                          <ul class="dropdown-menu">                                    
                              <li><a onclick="document.forms['logoutForm'].submit()">Logout</a></li>
                          </ul>
                  </li>

              <% } %>
               </ul>

            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
    <div class="container" style="margin-top:50px">