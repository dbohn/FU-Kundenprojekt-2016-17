<template>
    <div class="message-box col-6">
        <div class="author">{{ message.user.displayName }}</div>
        <div class="message" v-html="content"></div>
        <div class="time">{{ message.createdAt | date }}</div>
    </div>
</template>
<script>
    import moment from 'moment';
    import Showdown from 'showdown';

    const converter = new Showdown.Converter();

    export default {
        data() {
            return {};
        },

        props: ['message'],

        filters: {
            date(value) {
                let dateObj = new Date(value);

                return dateObj.toLocaleString();
            }
        },

        computed: {
            content() {
                return converter.makeHtml(this.message.content);
            }
        },
    }
</script>