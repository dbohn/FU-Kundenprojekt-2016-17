import Vue from 'vue';

new Vue({
    el: '#messenger',
    data: {

        me: Settings.me,
        thisUrl: Settings.thisUrl,
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