<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum - Login</title>
    </head>
    <body> 
        <%@ include file="/WEB-INF/jsp/view/nav.jsp" %>
            <h2>Course Discussion Forum Login</h2>
            <c:choose>
                <c:when test="${param.error != null}">
                    <p>Login failed.</p>
                </c:when>
                <c:when test="${param.logout != null}">
                    <p>You have logged out.</p>
                </c:when>
                <c:when test="${param.create != null}">
                    <p>You have successfully created the account.</p>
                </c:when>
                <c:otherwise>
                    <br/>
                </c:otherwise>
            </c:choose>
                    <form action="login" method="POST">
                                <label>Username</label>
                                <input id="username" type="text" name="username" required="required" placeholder = "Username" data-validation-required-message="Please enter your username.">
                                <label>Password</label>
                                <input id="password" type="password" name="password" required="required" placeholder = "Password" data-validation-required-message="Please enter your password.">
                                <label>Remember me</label>
                                <p>Remember me</p>
                                <input id="remember-me" type="checkbox" placeholder = "Remember Me" name="remember-me">
                              
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <br/>
                            <button type="submit">Login</button>
                    
                    </form>
    </body>
</html>