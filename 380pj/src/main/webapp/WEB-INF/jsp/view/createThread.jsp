<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum - Sign Up</title>
    </head>
    <body> 
        <%@ include file="/WEB-INF/jsp/view/nav.jsp" %>

            <h2>Course Discussion Forum Create Thread</h2>

            <c:if test="${param.error == 'field'}">
                <p>Please fill in all the required fields.</p>
            </c:if>

                    <form:form method="POST" enctype="multipart/form-data"
                               modelAttribute="threadForm">
                                <form:label path="title">Title</form:label>
                                <form:input type="text" path="title" required="required" placeholder = "Title"/>
                                <form:label path="content">Content</form:label>
                                <form:textarea rows="5" cols="30" maxlength="1000" path="content" required="required" placeholder = "Content"/>
                                <form:label path="category">Category</form:label>
                                <p>Category</p>
                                <form:select path="category">
                                    <form:option value="lecture">Lecture</form:option>
                                    <form:option value="lab">Lab</form:option>
                                    <form:option value="other">Other</form:option>
                                </form:select>

                                <form:label path="attachments">Attachment</form:label>
                                    <p>Attachment</p>
                                <form:input type="file" path="attachments" multiple="true" placeholder = "attachments"/>
                      

                        <button type="submit">Submit</button>

                    </form:form>
    </body>
</html>