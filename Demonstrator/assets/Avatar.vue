<template>
    <img class="avatar" :src="url" :alt="description">
</template>
<script>
    export default {
        data() {
            return {};
        },

        props: ['user'],

        computed: {
            url() {
                let id;
                if (typeof this.user == 'string') {
                    id = this.user;
                } else {
                    id = this.user.id;
                }
                return `http://humhub.local:8082/bcs/user/avatar?user_id=${id}`;
            },

            description() {
                let name;
                if (typeof this.user == 'string') {
                    name = this.user;
                } else {
                    name = this.user.displayname;
                }

                return `Profilbild von ${name}`;
            }
        }
    }
</script>