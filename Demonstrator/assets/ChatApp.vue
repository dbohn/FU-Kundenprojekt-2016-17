<template>
    <div class="row" id="messenger">
        <div class="col-4">
            <create-conversation></create-conversation>
            <conversation-list
                    :me="me"
                    :value="conversation"
                    @input="loadConversation($event)">
            </conversation-list>
        </div>
        <div class="col-8 message-view">
            <div v-if="conversation.id != null">
                <div class="header">
                    <small>Gestartet: {{ createdAtDate }}, {{ createdAtTime }}</small>
                    <h3>{{ conversation.title }}</h3>
                    <div class="participants d-flex w-100 m-2">
                        <div class="d-flex align-items-center mr-2" v-for="participant in conversation.participants">
                            <avatar :user="participant" class="mr-1"></avatar>
                            <small>{{ participant.displayName }}</small>
                        </div>
                    </div>
                </div>
                <div class="messages" ref="messages">
                    <div class="row"
                         v-for="message in messages">
                        <message :message="message" :class="{'from-me': mine(message), 'offset-6': mine(message), 'from-them': !mine(message)}"></message>
                    </div>
                    <div class="row"
                         v-for="message in conversation.pending">
                        <div class="message-box col-6 from-me offset-6">
                            <div class="message pending">{{ message.content }}</div>
                        </div>
                    </div>
                </div>
                <div class="message-editor">
                    <form @submit.prevent="postMessage">
                        <div class="form-group row">
                            <div class="col-10">
                                <message-editor v-model="message"></message-editor>
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
    import MessageEditor from './chat/MessageEditor.vue';
    import Message from './chat/Message.vue';
    import Dispatcher from './Dispatcher';
    import Conversation from './chat/Conversation';

    /**
     * This is the main chat application component.
     * It is responsible for coordinating the child components and
     * displaying the messages of a selected chat.
     */
    export default {
        data() {
            return {
                me: Settings.me,
                thisUrl: Settings.thisUrl,
                conversation: new Conversation(Settings.thisUrl),
                message: "",
                pendingMessages: {},
                conversations: [],
            };
        },

        computed: {
            createdAtDate() {
                if (this.conversation == null) {
                    return "";
                }

                let date = new Date(this.conversation.createdAt);

                return date.toLocaleDateString();
            },

            createdAtTime() {
                if (this.conversation == null) {
                    return "";
                }

                let date = new Date(this.conversation.createdAt);

                return date.toLocaleTimeString();
            },

            messages() {
                return this.conversation.messages;
            }
        },

        mounted() {
            this.conversation.addEventListener('beforeReply', ({key, message}) => {
                this.message = "";

                Vue.nextTick(() => {
                    this.scrollToBottom();
                });
            });

            setInterval(() => {
                if (this.conversation.id != null) {
                    this.conversation.refresh();
                }
            }, 3000);
        },

        methods: {
            /**
             * Load the selected conversation.
             */
            loadConversation(conversationId) {
                this.conversation.load(conversationId).then((data) => {
                    Vue.nextTick(() => {
                        this.scrollToBottom();
                    });

                    Dispatcher.$emit('conversation.loaded', data);
                });
            },

            /**
             * Submit a new message to the current conversation.
             */
            postMessage() {
                this.conversation.reply(this.message);
            },

            /**
             * Format the given date into a local representation
             */
            format(date) {
                let dateObj = new Date(date);

                return dateObj.toLocaleString();
            },

            /**
             * Check if the passed message is from the current user.
             */
            mine(message) {
                return message.user.user != null && this.me == message.user.user.id;
            },

            /**
             * Scroll to the bottom of the message list container.
             */
            scrollToBottom() {
                let messageContainer = this.$refs.messages;
                messageContainer.scrollTop = messageContainer.scrollHeight;
            }
        },

        components: {
            ConversationList,
            CreateConversation,
            Avatar,
            'message-editor': MessageEditor,
            Message
        }
    }
</script>