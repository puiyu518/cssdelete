<%@ include file="/WEB-INF/jsp/view/js.jsp" %>
<nav>
        <a href="<c:url value = '/thread'/>">Course Discussion Forum</a>
        <security:authorize access="isAuthenticated()" var = "authed"/>
        <c:choose>
            <c:when test = "${authed}">
                            <security:authorize access="hasRole('ADMIN')">

                                <li>
                                    <a href="<c:url value = '/user/listUser'/>">
                                        Manage User Accounts
                                    </a>
                                </li>

                                <li>
                                    <a href="<c:url value = '/polling/list'/>">
                                        Manage Pollings
                                    </a>
                                </li>
                            </security:authorize>
                            <li>
                                <a href="<c:url value = '/thread/createThread'/>">
                                    Create Thread
                                </a>
                            </li> 
                            <li>
                                <c:url var="logoutUrl" value="/logout"/>
                                <form action="${logoutUrl}" method="post">
                                    <input id ="logout" type="submit" value="Log out" />
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </form>
                            </li>
            </c:when>
            <c:otherwise>
                <ul>        
                    <li>
                        <a href="<c:url value = '/login'/>">
                            Login
                        </a>
                    </li> 
                    <li>
                        <a href="<c:url value = '/user/signup'/>">
                         Create
                        </a>
                    </li>
                </ul>
            </c:otherwise>
        </c:choose>
</nav>
<br/>