<template>
    <div class="list-group" v-if="conversations.length > 0">
        <a href="#" v-for="conversation in conversations" @click.prevent="loadConversation(conversation.id)"
           class="list-group-item list-group-item-action"
           :class="{active: value != null && value.id == conversation.id}">

            <div class="d-flex w-100 justify-content-between">
                <h5 class="list-group-item-heading">{{conversation.title}}</h5>
                <span v-if="isLoading(conversation.id)"><i class="fa fa-circle-o-notch fa-spin fa-fw"></i></span>
            </div>
            <p class="list-group-item-text conversation-info">
                <em>
                    <span v-for="participant in conversation.participants" v-if="me != participant.user.id">
                        {{ participant.displayName }} -
                    </span>
                    {{conversation.updatedAt | date }}
                </em>
            </p>
        </a>
    </div>
    <ul class="list-group" v-else-if="conversations.length == 0 && !isLoadingConversations">
        <li class="list-group-item list-group-item-info">Keine Konversation vorhanden.</li>
    </ul>
    <ul class="list-group" v-else-if="isLoadingConversations">
        <li class="list-group-item list-group-item-info"><i class="fa fa-circle-o-notch fa-spin fa-fw"></i> Lade Konversationen...</li>
    </ul>
</template>
<script>
    import moment from 'moment';
    import Dispatcher from '../Dispatcher';

    moment.locale('de');

    export default {
        data() {
            return {
                conversations: [],
                isLoadingConversations: false,
                loadingConversation: [],
            };
        },

        filters: {
            date(value) {
                return moment(value).fromNow();
            }
        },

        props: ['value', 'me'],

        mounted() {
            this.updateConversationList();
            Dispatcher.$on('conversation.created', () => this.updateConversationList());

            Dispatcher.$on('conversation.loaded', (conversation) => {
                let index = this.loadingConversation.indexOf(conversation.id);
                if (index >= 0) {
                    this.loadingConversation.splice(index, 1);
                }
            });
        },

        methods: {
            loadConversation(id) {
                this.$emit('input', id);
                this.loadingConversation.push(id);
            },

            updateConversationList() {
                this.isLoadingConversations = true;
                $.get('?conversations=1').then((data) => {
                    this.isLoadingConversations = false;
                    this.conversations = data;
                });
            },

            isLoading(id) {
                return this.loadingConversation.includes(id);
            }
        }
    }
</script>