<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum - List User</title>
    </head>
    <body> 
        <%@ include file="/WEB-INF/jsp/view/nav.jsp" %>
                    <c:choose>
                        <c:when test="${total == 0}">
                            <i>There are no threads in ${type.toUpperCase()} .</i>
                        </c:when>
                        <c:otherwise>
                            <i>There are ${total} threads in ${type.toUpperCase()} .</i>
                            <table>
                                <thead>
                                    <tr>
                                        <th scope="col">Author</th>
                                        <th scope="col">Title</th>
                                        <th scope="col">Time</th>
                                        <th scope="col">Action</th>
                                    </tr>
                                </thead>

                                <c:forEach items="${threads}" var="thread">
                                    <c:if test="${not thread.user.isDisabled()}">
                                    <tr>
                                        <td>${thread.user.username}</td>
                                        <td>${thread.title}</td>
                                        <td>${thread.dateBetween}</td>
                                        <td>
                                            <a href="<c:url value="/thread/${thread.category}/${thread.id}" />"><button type="button">Enter</button></a>
                                            <security:authorize access="hasRole('ADMIN')">
                                                <a href="<c:url value="/thread/${thread.category}/${thread.id}/delete" />"><button type="button">Delete</button></a>
                                            </security:authorize>
                                        </td>
                                    </tr>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </c:otherwise>
                    </c:choose>
    </body>
</html>