<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum - Create Polling</title>
    </head>
    <body> 
        <%@ include file="/WEB-INF/jsp/view/nav.jsp" %>
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
                               modelAttribute="pollingForm">
                                <form:label path="title">Title</form:label>
                                <form:input type="text" path="title" required="required" placeholder = "title"/>
                                <form:label path="option1">Option Item 1</form:label>
                                <form:input type="text" path="option1" required="required"  placeholder = "Option Item 1"/>
                                <form:label path="option2">Option Item 2</form:label>
                                <form:input type="text" path="option2" placeholder = "Option Item 2"/>
                                <form:label path="option3">Option Item 3</form:label>
                                <form:input type="text" path="option3" placeholder = "Option Item 3"/>
                                <form:label path="option4">Option Item 4</form:label>
                                <form:input type="text" path="option4" placeholder = "Option Item 4"/>
                    <button type="submit">Submit</button>
                    </form:form>
    </body>
</html>