<c:url var="post_link" value = "/user/${user.username}/edit"/>
<form:form method="POST" enctype="multipart/form-data"
           modelAttribute="userForm" action="${post_link}">
    <p>Username and Password are only accepted in LETTERS and NUMBERS.</p>
            <form:label path="username">Username</form:label>
            <form:input type="text" path="username" required="required" value = "${user.username}"  pattern="[a-zA-Z0-9]+" placeholder = "Username"/>
         
            <form:label path="password">Password</form:label>
            <form:input type="password" path="password" required="required" value = "${user.noHashPassword}"  pattern="[a-zA-Z0-9]+"  placeholder = "Password"/>
          
            <form:label path="confirm">Confirm Password</form:label>
            <form:input type="password" path="confirm" required="required" value = "${user.noHashPassword}" pattern="[a-zA-Z0-9]+"  placeholder = "Confirm Password"/>


    <security:authorize access="hasRole('ADMIN')">
                <form:label path="roles">Role Admin</form:label>
                    <p>Role Admin</p>
                <c:choose>
                    <c:when test="${user.hasRole('ROLE_ADMIN')}">
                        <form:checkbox path="roles"  value = "ROLE_ADMIN" checked="true"/>
                    </c:when>
                    <c:otherwise>
                        <form:checkbox path="roles"  value = "ROLE_ADMIN"/>
                    </c:otherwise>
                </c:choose>

                <form:label path="roles">Role User</form:label>
                    <p>Role User</p>
                <c:choose>
                    <c:when test="${user.hasRole('ROLE_USER')}">
                        <form:checkbox path="roles"  value = "ROLE_USER" checked="true"/>
                    </c:when>
                    <c:otherwise>
                        <form:checkbox path="roles"  value = "ROLE_USER"/>
                    </c:otherwise>
                </c:choose>
                <form:label path="roles">Disabled</form:label>
                    <p>Disabled</p>
                <c:choose>
                    <c:when test="${user.isDisabled()}">
                        <form:checkbox path="disabled"  value = "disabled" checked="true"/>
                    </c:when>
                    <c:otherwise>
                        <form:checkbox path="disabled"  value = "disabled"/>
                    </c:otherwise>
                </c:choose>
    </security:authorize>           
    <button type="submit">Submit</button>

</form:form>