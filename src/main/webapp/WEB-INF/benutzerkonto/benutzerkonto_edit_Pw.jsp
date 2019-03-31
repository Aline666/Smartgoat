<%-- 
    Document   : benutzerkonto_edit
    Created on : Mar 29, 2019, 1:03:14 PM
    Author     : laurahetzel
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
         Benutzerpasswort ändern
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/form.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/tasks/list/"/>">Liste der Aufgaben</a>
        </div>


        <div class="menuitem">
            <a href="<c:url value="/app/tasks/categories/"/>">Kategorien bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                    <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                    <%-- Eingabefelder --%>
                    <label for="pw_old">
                        Aktuelles Passwort:
                        <span class="required">*</span>
                    </label>
                    <input type="password" name="pw_old">

                    <label for="pw_new1">
                        Neues Passwort:
                        <span class="required">*</span>
                    </label>
                    <input type="password" name="pw_new1">
                    
                    <label for="pw_new2">
                        Neues Passwort wiederholen
                        <span class="required">*</span>
                    </label>
                    <input type="password" name="pw_new2">

                    <button type="submit" name="action" value="save">Änderung speichern</button>
                </div>
                
                <%-- Fehlermeldungen --%>
                <c:if test="${!empty profil_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${profil_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
                   
                
            <a href="<c:url value="/app/profil/"/>" class="greenBtn">Zum Benutzerprofil</a> 
            <a href="<c:url value="/app/profil/edit/"/>" class="greenBtn">Profil bearbeiten</a> 
            
        </div>
    </jsp:attribute>
</template:base>