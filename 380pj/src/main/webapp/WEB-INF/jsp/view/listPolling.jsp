<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum List Polling</title>
    </head>
    <body> 
        <%@ include file="/WEB-INF/jsp/view/nav.jsp" %>

        ${test}
                    <a href="<c:url value="/polling/create" />">Create a Polling</a><br/>
                    <c:choose>
                        <c:when test="${param.error == 'exists'}">
                            <p>Please disable the current polling first!</p>
                        </c:when>
                        <c:when test="${param.error == 'noExists'}">
                            <p>Polling not exists!</p>
                        </c:when>   
                        <c:otherwise>
                            <br/>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${fn:length(pollings) == 0}">
                            <i>There are no pollings in the system.</i>
                        </c:when>
                        <c:otherwise>
                            <table>
                                <thead>
                                    <tr>
                                        <th scope="col">Title</th>
                                        <th scope="col">Option Item 1</th>
                                        <th scope="col">Option Item 2</th>
                                        <th scope="col">Option Item 3</th>
                                        <th scope="col">Option Item 4</th>
                                        <th scope="col">Current Polling</th>
                                        <th scope="col">Result</th>
                                        <th scope="col">Action</th>

                                    </tr>
                                </thead>

                                <c:forEach items="${pollings}" var="polling">
                                    <tr>
                                        <td>${polling.title}</td>
                                        <td>${polling.option1}</td>
                                        <td>${polling.option2}</td>
                                        <td>${polling.option3}</td>
                                        <td>${polling.option4}</td>
                                        <td>
                                            <c:if test="${polling.isEnabled()}">
                                                &#8730;
                                            </c:if>
                                        </td>
                                        <td>
                                            <p>Total voted: ${polling.totalVoted}</p> 
                                             <canvas id="pollingGraph_${polling.title}" ></canvas>
                                          
                                            <%@ include file="/WEB-INF/jsp/view/pollingGraph.jsp" %>
                                            
                                        </td>
                                        <td>
                                            <a href="<c:url value="/polling/delete/${polling.id}" />">Delete</a>
                                            <c:choose>
                                                <c:when test = "${polling.isEnabled()}">
                                                    [<a href="<c:url value="/polling/disable/${polling.id}" />">Disable</a>]
                                                </c:when>
                                                <c:otherwise>
                                                    [<a href="<c:url value="/polling/enable/${polling.id}" />">Enable</a>]
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:otherwise>
                    </c:choose>
    </body>
</html>