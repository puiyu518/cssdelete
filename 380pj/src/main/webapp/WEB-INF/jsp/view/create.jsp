<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum - Sign Up</title>
    </head>
    <body> 
        <%@ include file="/WEB-INF/jsp/view/nav.jsp" %>
            <c:choose>
                <c:when test="${type == '/user/signup'}">
                    <h2>Course Discussion Forum Sign Up</h2>
                </c:when>
                <c:otherwise>
                    <h2>Course Discussion Forum Create User</h2>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${param.error == 'exists'}">
                    <p>Username is already exists.</p>
                </c:when>
                <c:when test="${param.error == 'password'}">
                    <p>Confrim password is not match with Password.</p>
                </c:when>
                <c:when test="${param.error == 'noRole'}">
                    <p>Please select one or more Role.</p>
                </c:when>
                <c:when test="${param.error == 'spchara'}">
                    <p>Username or Password are only accepted in LETTERS and NUMBERS.</p>
                </c:when>
                <c:otherwise>
                    <br/>
                </c:otherwise>
            </c:choose>

                    <form:form method="POST" enctype="multipart/form-data"
                               modelAttribute="userForm">
                        <p>Username or Password are only accepted in LETTERS and NUMBERS.</p>

                      
                                <form:label path="username">Username</form:label>
                                <form:input type="text" path="username" required="required" pattern="[a-zA-Z0-9]+"  placeholder = "Username"/>
                                <form:label path="password">Password</form:label>
                                <form:input type="password" path="password" required="required" pattern="[a-zA-Z0-9]+"  placeholder = "Password"/>
                                <form:label path="confirm">Confirm Password</form:label>
                                <form:input type="password" path="confirm" required="required" pattern="[a-zA-Z0-9]+"  placeholder = "Confirm Password"/>
                              
                        <security:authorize access="hasRole('ADMIN')">
                                    <form:label path="roles">Role Admin</form:label>
                                        <p>Role Admin</p>
                                    <c:choose>
                                        <c:when test="${user.hasRole('ROLE_ADMIN')}">
                                            <form:checkbox path="roles"  value = "ROLE_ADMIN" checked="true"/>
                                        </c:when>
                                        <c:otherwise>
                                            <form:checkbox path="roles"  value = "ROLE_ADMIN"/>
                                        </c:otherwise>
                                    </c:choose>

                                    <form:label path="roles">Role User</form:label>
                                        <p>Role User</p>
                                    <c:choose>
                                        <c:when test="${user.hasRole('ROLE_USER')}">
                                            <form:checkbox path="roles"  value = "ROLE_USER" checked="true"/>
                                        </c:when>
                                        <c:otherwise>
                                            <form:checkbox path="roles"  value = "ROLE_USER"/>
                                        </c:otherwise>
                                    </c:choose>
                                

            
                                    <form:label path="roles">Disabled</form:label>
                                        <p>Disabled</p>
                                    <c:choose>
                                        <c:when test="${user.isDisabled()}">
                                            <form:checkbox path="disabled"  value = "disabled" checked="true"/>
                                        </c:when>
                                        <c:otherwise>
                                            <form:checkbox path="disabled"  value = "disabled"/>
                                        </c:otherwise>
                                    </c:choose>
                        </security:authorize>           
                        <button type="submit">Submit</button>
                    </form:form>
    </body>
</html>