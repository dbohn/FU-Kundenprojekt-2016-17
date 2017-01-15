<template>
    <div>
        <ul class="list-group" v-if="isLoadingConversations">
            <li class="list-group-item list-group-item-info"><i class="fa fa-circle-o-notch fa-spin fa-fw"></i> Lade
                Konversationen...
            </li>
        </ul>
        <div class="list-group" v-if="conversations.length > 0">
            <a href="#" v-for="conversation in conversations" @click.prevent="loadConversation(conversation.id)"
               class="list-group-item list-group-item-action"
               :class="{active: value != null && value.id == conversation.id}">
                <p class="m-0">
                    <small>
                        {{ displayParticipants(conversation.participants) }}
                    </small>
                </p>
                <div class="d-flex w-100 justify-content-between">
                    <h5 class="list-group-item-heading">{{conversation.title}}</h5>
                    <span v-if="isLoading(conversation.id)"><i class="fa fa-circle-o-notch fa-spin fa-fw"></i></span>
                </div>
                <div class="list-group-item-text conversation-info">
                    <p class="m-0">
                        <small>
                            Zuletzt aktiv: {{conversation.updatedAt | date }}
                        </small>
                    </p>
                </div>
            </a>
        </div>
        <ul class="list-group" v-if="conversations.length == 0 && !isLoadingConversations">
            <li class="list-group-item list-group-item-info">Keine Konversation vorhanden.</li>
        </ul>
    </div>
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
            },

            displayParticipants(participants) {
                return participants
                    .filter((participant) => this.me != participant.user.id)
                    .map((participant) => participant.displayName)
                    .join(", ");
            }
        }
    }
</script>