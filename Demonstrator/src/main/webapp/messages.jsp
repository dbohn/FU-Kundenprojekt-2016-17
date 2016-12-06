<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class="chat">
<head>
    <title>Demonstrator &mdash; Users</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap-flex.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/tether.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="https://unpkg.com/vue/dist/vue.js"></script>
</head>
<body>
<%@ include file="partials/navigation.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-xs">
            <div class="jumbotron">
                <h1 class="display-3">Nachrichten</h1>
                <p class="lead">HumHub-Nachrichten lesen und beantworten.</p>
            </div>
        </div>
    </div>
    <c:if test="${not empty status}">
    <div class="row">
        <div class="col-xs">
            <div class="alert alert-success">
                    ${status}
            </div>
        </div>
    </div>
    </c:if>
<div class="container">
    <div class="row" id="messenger">
        <div class="col-xs-4">
            <div class="list-group">
                <c:forEach var="conversation" items="${conversations}">
                    <a href="#" @click.prevent="loadConversation(${conversation.id})"
                       class="list-group-item list-group-item-action"
                       :class="{active: activeConversation != null && activeConversation.id == ${conversation.id}}">

                        <h5 class="list-group-item-heading">${conversation.title}</h5>
                        <p class="list-group-item-text">
                                ${conversation.lastMessage.content}<br>
                            <em>
                                <c:forEach var="participant" items="${conversation.participants}">
                                    <c:if test="${user != participant.user}">
                                        ${participant.displayName} -
                                    </c:if>
                                </c:forEach>
                                    ${conversation.lastMessageTimestamp}
                            </em></p>
                    </a>
                </c:forEach>
            </div>
        </div>
        <div class="col-xs-8 message-view">
            <div v-if="activeConversation != null">
                <div class="header">
                    <h3>{{ activeConversation.title }}</h3>
                    <h4>Gestartet am {{ createdAtDate }} um {{ createdAtTime }}</h4>
                </div>
                <div class="messages" ref="messages">
                    <div class="row" :class="{'flex-items-xs-right': mine(message)}"
                         v-for="message in messages">
                        <div class="message-box col-xs-6"
                             :class="{'from-me': mine(message), 'from-them': !mine(message)}">
                            <div class="author">{{ message.user.displayName }}</div>
                            <div class="message">{{ message.content }}</div>
                            <div class="time">{{ format(message.createdAt) }}</div>
                        </div>
                    </div>
                    <div class="row flex-items-xs-right"
                         v-for="message in pendingMessages">
                        <div class="message-box col-xs-6 from-me">
                            <div class="message pending">{{ message.content }}</div>
                        </div>
                    </div>
                </div>
                <div class="message-editor">
                    <form @submit.prevent="postMessage">
                        <div class="form-group row">
                            <div class="col-xs-10">
                                <textarea class="form-control" placeholder="Antworten..." name="editor" id="editor" cols="30" rows="5" v-model="message"></textarea>
                            </div>
                            <div class="col-xs-2">
                                <button class="btn btn-primary">Senden</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    new Vue({
        el: '#messenger',
        data: {

            me: "${user.id}",
            thisUrl: "${pageContext.request.contextPath}/conversations",
            activeConversation: null,
            message: "",
            pendingMessages: {}
        },
        computed: {
            createdAtDate() {
                if (this.activeConversation == null) {
                    return "";
                }

                let date = new Date(this.activeConversation.createdAt);

                return date.toLocaleDateString();
            },

            createdAtTime() {
                if (this.activeConversation == null) {
                    return "";
                }

                let date = new Date(this.activeConversation.createdAt);

                return date.toLocaleTimeString();
            },

            messages() {
                return this.activeConversation.messages;
            }
        },
        methods: {
            loadConversation(conversationId, pendingId = null) {
                $.get('?conversation=' + conversationId).then((data) => {
                    this.activeConversation = data;

                    if (pendingId != null) {
                        Vue.delete(this.pendingMessages, pendingId);
                    }

                    Vue.nextTick(() => {
                        this.scrollToBottom();
                    });
                });
            },

            postMessage() {
                let id = this.activeConversation.id;
                let message = this.message;
                let key = Date.now();

                Vue.set(this.pendingMessages, key, {
                    content: this.message
                });

                this.message = "";

                Vue.nextTick(() => {
                    this.scrollToBottom();
                });

                $.post(this.thisUrl, {
                    conversation: id,
                    message: message,
                }).then((response) => {
                    this.loadConversation(id, key);
                });

                console.log(this.message);
            },

            format(date) {
                let dateObj = new Date(date);

                return dateObj.toLocaleString();
            },

            mine(message) {
                return message.user.user != null && this.me == message.user.user.id;
            },

            scrollToBottom() {
                let messageContainer = this.$refs.messages;
                messageContainer.scrollTop = messageContainer.scrollHeight;
            }
        }
    });
</script>
</body>
</html>
