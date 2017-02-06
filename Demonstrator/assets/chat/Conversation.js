import Vue from "vue";

/**
 * This class represents the current conversation.
 * It provides a list of all messages, metadata about
 * the conversation and also methods to refresh the list
 * or post a new message to the conversation.
 */
export default class Conversation {

    constructor(baseUrl) {
        this.baseUrl = baseUrl;
        this.id = null;
        this.messages = [];
        this.pending = {};
        this.createdAt = "";
        this.updatedAt = "";
        this._listeners = {};
    }

    /**
     * Load the given Conversation from the server and update the data.
     *
     * @param conversationId The identifier of the conversation
     * @returns {Promise.<TResult>} resolves as soon as the conversation was loaded
     */
    load(conversationId) {
        return $.get('?conversation=' + conversationId).then((data) => {
            this.id = conversationId;
            this.assignData(data);
            this.$emit('loadedConversation', {'id': conversationId});

            return data;
        });
    }

    /**
     * Refresh the currently loaded conversation.
     *
     * @returns {Promise.<TResult>} resolves as soon as the new messages are appended
     */
    refresh() {
        if (this.id == null) {
            return;
        }

        let latest = this.latestMessage();
        let date = encodeURIComponent(latest.createdAt);

        return $.get('?conversation=' + this.id + '&since=' + date).then((data) => {
            this.messages = this.messages.concat(data.messages);

            if (data.messages.length > 0) {
                this.$emit('newMessages');
            }

            return data;
        });
    }

    /**
     * Load the received conversation data into this object.
     *
     * @param data
     */
    assignData(data) {
        this.messages = data.messages;
        this.participants = data.participants;
        this.title = data.title;
        this.createdAt = data.createdAt;
        this.updatedAt = data.updatedAt;
    }

    /**
     * Send a message to the current conversation.
     *
     * @param message
     */
    reply(message) {
        let key = Date.now();

        Vue.set(this.pending, key, {
            content: message
        });

        this.$emit('beforeReply', {key, message});

        $.post(this.baseUrl + "/conversations", {
            conversation: this.id,
            message: message,
        }).then(() => {
            return this.refresh();
        }).then(() => {
            Vue.delete(this.pending, key);
            this.$emit('afterReply', {message});
        });
    }

    /**
     * Get the latest message from the conversation.
     *
     * @returns {T|*} the latest message
     */
    latestMessage() {
        return this.messages[this.messages.length - 1];
    }

    /**
     * Add a new event listener to the event given by type.
     *
     * @param type
     * @param callback
     */
    addEventListener(type, callback) {
        if (!(type in this._listeners)) {
            this._listeners[type] = [];
        }
        this._listeners[type].push(callback);
    }

    /**
     * Remove an event listener.
     *
     * @param type The type of the event, the callback is assigned to.
     * @param callback The callback, that should be removed.
     * @returns {*} nothing really
     */
    removeEventListener(type, callback) {
        if (!(type in this._listeners)) {
            return;
        }
        let stack = this._listeners[type];
        for (let i = 0, l = stack.length; i < l; i++) {
            if (stack[i] === callback) {
                stack.splice(i, 1);
                return this.removeEventListener(type, callback);
            }
        }
    }

    /**
     * Fire an event.
     *
     * @param event the event to fire.
     */
    dispatchEvent(event) {
        if (!(event.type in this._listeners)) {
            return;
        }

        let stack = this._listeners[event.type];
        event.target = this;

        for (let i = 0, l = stack.length; i < l; i++) {
            stack[i].call(this, event);
        }
    }

    /**
     * Vue-like shortcut for {@link dispatchEvent} that accepts the type
     * as a separate argument.
     *
     * @param event
     * @param data
     */
    $emit(event, data) {
        this.dispatchEvent({
            type: event,
            ...data
        });
    }
}