<template>
    <div class="list-group">
        <a href="#" v-for="conversation in conversations" @click.prevent="loadConversation(conversation.id)"
           class="list-group-item list-group-item-action"
           :class="{active: active != null && active.id == conversation.id}">

            <h5 class="list-group-item-heading">{{conversation.title}}</h5>
            <p class="list-group-item-text">
                <em>
                    <span v-for="participant in conversation.participants" v-if="me != participant.user.id">
                        {{ participant.displayName }} -
                    </span>
                    {{conversation.updatedAt | date }}
                </em>
            </p>
        </a>
    </div>
</template>
<script>
    import moment from 'moment';

    moment.locale('de');

    export default {
        data() {
            return {};
        },

        filters: {
            date(value) {
                return moment(value).fromNow();
            }
        },

        props: ['conversations', 'active', 'me'],

        methods: {
            loadConversation(id) {
                this.$emit('select', id);
            }
        }
    }
</script>