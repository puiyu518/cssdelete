<!DOCTYPE html>
<html>
    <head>
        <title>${thread.title}</title>
    </head>
    <body> 
        <%@ include file="/WEB-INF/jsp/view/nav.jsp" %>
        <security:authorize access="isAuthenticated()" var = "authed"/>

                    <a href ="<c:url value = "/thread/${thread.category}"/>">                      
                        Back
                    </a>

            <br>
                      <h1>${thread.category.toUpperCase()}</h1>
                      

                    <br>
                    <c:forEach var = "type_thread" items="${threads}">
                        <c:if test="${not type_thread.user.isDisabled()}">
                                    <p>${type_thread.user.username} ${type_thread.dateBetween}
                                    </p>
                    
                                    <a href="<c:url value = "/thread/${type_thread.category}/${type_thread.id}"/>">
                                       ${type_thread.title}
                                    </a>
                            <br>
                        </c:if>

                    </c:forEach>
                        <security:authorize access="isAuthenticated()" var = "authed"/>
                        <c:choose>
                            <c:when test = "${authed}">
                                    <h1>${thread.title}</h1>
                                    <%@ include file="/WEB-INF/jsp/view/replyForm.jsp" %>
                             
                            </c:when>
                            <c:otherwise>
                                
                                    <h1>${thread.title}</h1>
                             
                            </c:otherwise>
                        </c:choose>
              
                    <br>
                                <p>${thread.user.username}
                                   ${thread.dateBetween}
                                </p>
                                <hr>
                          
                                <p>${thread.content}</p>
                          
                            <br>
                            <c:if test="${!empty thread.attachments}">
                                
                                    <c:forEach var="attachment" items="${thread.attachments}">
                                       
                                            <c:choose>
                                                <c:when test="${authed}">
                                                    <a href="<c:url value = "/thread/${thread.category}/${thread.id}/download/${attachment.attachmentName}" />">                                      
                                                            ${attachment.attachmentName}
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <button onclick="loginAlert()">
                                                        ${attachment.attachmentName}
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>

                                     
                                    </c:forEach>
                     
                            </c:if>
                    <br>
                    <c:if test="${!empty replies}">
                        <c:forEach var = "reply" items="${replies}">
                            <c:if test="${not reply.user.isDisabled()}">
                   
                                            <p>${reply.user.username} &nbsp;
                                                <security:authorize access="hasRole('ADMIN')">
                                                    <a href="<c:url value = "/thread/${thread.category}/${thread.id}/reply/${reply.id}/delete" />">       
                                                            Delete Reply
                                                    </a>
                                                </security:authorize>

                                                <span>${reply.dateBetween}</span>
                                            </p>
                                            <hr>
                                            <p>${reply.content}</p>                              
                                        <br>
                                        <c:if test="${!empty reply.attachments}">
                                            <div class ="row">
                                                <c:forEach var="attachment" items="${reply.attachments}">
                                                  
                                                        <c:choose>
                                                            <c:when test="${authed}">
                                                                <a href="<c:url value = "/thread/${thread.category}/${thread.id}/reply/${reply.id}/download/${attachment.attachmentName}" />">
                                                                
                                                                        <span>${attachment.attachmentName}</span>
                                                                 
                                                                </a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <button onclick="loginAlert()">
                                                                    ${attachment.attachmentName}
                                                                </button>
                                                            </c:otherwise>
                                                        </c:choose>

                                             
                                                </c:forEach>
                                    
                                        </c:if>
                                <br>
                            </c:if>
                        </c:forEach>
                    </c:if>
    </body>
</html>