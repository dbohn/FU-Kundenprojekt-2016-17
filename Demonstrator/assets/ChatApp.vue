<template>
    <div class="row" id="messenger">
        <div class="col-xs-4">
            <div class="list-group">
                <a href="#" v-for="conversation in conversations" @click.prevent="loadConversation(conversation.id)"
                   class="list-group-item list-group-item-action"
                   :class="{active: activeConversation != null && activeConversation.id == conversation.id}">

                    <h5 class="list-group-item-heading">{{conversation.title}}</h5>
                    <p class="list-group-item-text">
                        <!--{{conversation.lastMessage.content}}<br>-->
                        <em>
                            <span v-for="participant in conversation.participants" v-if="me != participant.user.id">
                                {{ participant.displayName }} -
                            </span>
                            {{conversation.updatedAt | date }}
                        </em>
                    </p>
                </a>
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
</template>
<script>
    import Vue from 'vue';

    import moment from 'moment';

    moment.locale('de');

    export default {
        data() {
            return {
                me: Settings.me,
                thisUrl: Settings.thisUrl,
                activeConversation: null,
                message: "",
                pendingMessages: {},
                conversations: {}
            };
        },

        mounted() {
            $.get('?conversations=1').then((data) => {
                this.conversations = data;
            });
        },

        filters: {
           date(value) {
               return moment(value).fromNow();
           }
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
    }
</script>