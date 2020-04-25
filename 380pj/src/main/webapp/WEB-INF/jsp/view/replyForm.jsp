<button type="button" data-target="#reply_form">
    Reply
</button>
        <h5>Reply to ${thread.title}</h5>
                <button type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>

         <c:url var="post_url"  value="/thread/${thread.category}/${thread.id}/createReply" />
            <form:form method="POST" enctype="multipart/form-data"
                  modelAttribute="replyForm" action="${post_url}">
                                    <form:label path="content">Content</form:label>
                                    <form:textarea rows="5" cols="30" maxlength="1000" path="content" required="required" placeholder = "Content"/>
              
                                    <form:label path="attachments">Attachment</form:label>
                                        <p>Attachment</p>
                                    <form:input multiple="true" type="file" path="attachments"  placeholder = "attachments"/>

                            <button type="submit">Submit</button>

                </form:form>
                            