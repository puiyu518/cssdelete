<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum - List User</title>
    </head>
    <body> 
        <%@ include file="/WEB-INF/jsp/view/nav.jsp" %>
                    <a href="<c:url value="/user/create" />">Create a User</a>
                    <br/>
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
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${fn:length(ticketUsers) == 0}">
                            <i>There are no users in the system.</i>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <thead>
                                    <tr>
                                        <th scope="col">Username</th>
                                        <th scope="col">Password</th>
                                        <th scope="col">Roles</th>
                                        <th scope="col">Disabled</th>
                                        <th scope="col">Action</th>
                                    </tr>
                                </thead>

                                <c:forEach items="${ticketUsers}" var="user">
                                    <tr>
                                        <td>${user.username}</td><td>${user.noHashPassword}</td>
                                        <td>
                                            <c:forEach items="${user.roles}" var="role" varStatus="status">
                                                <c:if test="${!status.first}">, </c:if>
                                                ${role.role}
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:if test="${user.isDisabled()}">
                                                &#8730;
                                            </c:if>
                                        </td>
                                        <td>
                                              <c:if test="${user.username != currentUser}"> 
                                                  <a href="<c:url value="/user/delete/${user.username}" />">Delete</a>
                                              </c:if>
                                            
                                                  <a href="#" data-toggle="modal" data-target="#editUser_form_${user.username}">Edit</a>
                                                            <h5>Edit User - ${user.username}</h5>
                                                            <button type="button" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                                    <%@ include file="/WEB-INF/jsp/view/editUser.jsp" %>
                                                          
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:otherwise>
                    </c:choose>
    </body>
</html>