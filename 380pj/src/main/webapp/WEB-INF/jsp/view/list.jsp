<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body> 
        <%@ include file="/WEB-INF/jsp/view/nav.jsp" %>
        <br> 


        <section>
                        <a href = "<c:url value="/thread/lecture" />">Lecture </a>
                        <a href = "<c:url value="/thread/lab" />">Lab</a>
                        <a href = "<c:url value="/thread/other" />">Other</a>              
    </section>
    <section>
            <h2>Current Polling</h2>
                <c:choose>
                    <c:when test="${not empty polling}">
                        <security:authorize access="isAuthenticated()" var = "authed"/>

                        <c:choose>
                            <c:when test="${authed && not voted}">
                                    <h3>${polling.title} (Total voted: ${polling.totalVoted})</h3>
                                        <canvas id="pollingGraph_${polling.title}" ></canvas>
                                   
                                    <%@ include file="/WEB-INF/jsp/view/pollingGraph.jsp" %>
                          
                                    <%@ include file="/WEB-INF/jsp/view/voteForm.jsp" %>
 

                            </c:when>
                            <c:when test="${authed && voted}">
                                    <h3>${polling.title} (Total voted: ${polling.totalVoted})</h3>
                                        <canvas id="pollingGraph_${polling.title}" ></canvas>
                                    <%@ include file="/WEB-INF/jsp/view/pollingGraph.jsp" %>
                            </c:when>
                            <c:otherwise>
                                    <h3>${polling.title} (Total voted: ${polling.totalVoted})</h3>
                                        <canvas id="pollingGraph_${polling.title}" ></canvas>
                                    <%@ include file="/WEB-INF/jsp/view/pollingGraph.jsp" %>
                            </c:otherwise>
                        </c:choose>


                    </c:when>
                    <c:otherwise>
                            <p>No polling yet. </p>                    
                    </c:otherwise>
                </c:choose>
    </section>                     
</body>
</html>