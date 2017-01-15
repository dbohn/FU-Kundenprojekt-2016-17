<template>
    <div class="row" id="messenger">
        <div class="col-4">
            <create-conversation></create-conversation>
            <conversation-list
                    :me="me"
                    :value="activeConversation"
                    @input="loadConversation($event)">
            </conversation-list>
        </div>
        <div class="col-8 message-view">
            <div v-if="activeConversation != null">
                <div class="header">
                    <small>Gestartet: {{ createdAtDate }}, {{ createdAtTime }}</small>
                    <h3>{{ activeConversation.title }}</h3>
                    <div class="participants d-flex w-100 m-2">
                        <div class="d-flex align-items-center mr-2" v-for="participant in activeConversation.participants">
                            <avatar :user="participant" class="mr-1"></avatar>
                            <small>{{ participant.displayName }}</small>
                        </div>
                    </div>
                </div>
                <div class="messages" ref="messages">
                    <div class="row"
                         v-for="message in messages">
                        <div class="message-box col-6"
                             :class="{'from-me': mine(message), 'offset-6': mine(message), 'from-them': !mine(message)}">
                            <div class="author">{{ message.user.displayName }}</div>
                            <div class="message">{{ message.content }}</div>
                            <div class="time">{{ format(message.createdAt) }}</div>
                        </div>
                    </div>
                    <div class="row"
                         v-for="message in pendingMessages">
                        <div class="message-box col-6 from-me offset-6">
                            <div class="message pending">{{ message.content }}</div>
                        </div>
                    </div>
                </div>
                <div class="message-editor">
                    <form @submit.prevent="postMessage">
                        <div class="form-group row">
                            <div class="col-10">
                                <textarea class="form-control" placeholder="Antworten..." name="editor" id="editor" cols="30" rows="5" v-model="message"></textarea>
                            </div>
                            <div class="col-2">
                                <button class="btn btn-primary">Senden</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import Vue from 'vue';
    import ConversationList from './chat/ConversationList.vue';
    import CreateConversation from './chat/CreateConversation.vue';
    import Avatar from './Avatar.vue';
    import Dispatcher from './Dispatcher';

    export default {
        data() {
            return {
                me: Settings.me,
                thisUrl: Settings.thisUrl,
                activeConversation: null,
                message: "",
                pendingMessages: {},
                conversations: [],
            };
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

                    Dispatcher.$emit('conversation.loaded', data);
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

                $.post(this.thisUrl + "/conversations", {
                    conversation: id,
                    message: message,
                }).then((response) => {
                    this.loadConversation(id, key);
                });
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
        },

        components: {
            ConversationList,
            CreateConversation,
            Avatar
        }
    }
</script>