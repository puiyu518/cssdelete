<c:url var="post_url"  value="/pollingResult/${polling.id}/vote" />
<form:form method="POST" enctype="multipart/form-data"
           modelAttribute="pollingForm" action="${post_url}">
            <form:label path="optionItem">Option</form:label>
                <p>Option</p>
            <form:select path="optionItem">
                <form:option value="1">${polling.option1}</form:option>
                <c:if test="${not empty polling.option2}">
                    <form:option value="2">${polling.option2}</form:option>
                </c:if>
                <c:if test="${not empty polling.option3}">
                    <form:option value="3">${polling.option3}</form:option>
                </c:if>
                <c:if test="${not empty polling.option4}">
                    <form:option value="3">${polling.option4}</form:option>
                </c:if>
            </form:select>
 <button type="submit">Submit</button>

</form:form>
