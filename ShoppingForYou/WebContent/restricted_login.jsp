<%@ page language="java" contentType="text/html; charset=windows-1255"
    pageEncoding="windows-1255"%>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:h="http://xmlns.jcp.org/jsf/html"
	xmlns:f="http://xmlns.jcp.org/jsf/core">
<head>
<meta charset="windows-1255">
<title>Insert title here</title>
</head>
<body>
  <f:view>
    <p><a href="restricted/secret.faces">try to go to secret page</a></p>
    <h:form>
    Username:
    <h:panelGroup rendered="#{not authenticationBean.loggedIn}">
        <h:inputText value="#{authenticationBean.name}" />
        <h:commandButton value="login"
          action="#{authenticationBean.login}" />
      </h:panelGroup>
      <h:commandButton value="logout"
        action="#{authenticationBean.logout}"
        rendered="#{authenticationBean.loggedIn}" />
    </h:form>
  </f:view>
</body>
</html>