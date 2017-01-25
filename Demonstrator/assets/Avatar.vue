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
                    if (typeof this.user.user != 'undefined') {
                        id = this.user.user.id;
                    } else {
                        id = this.user.id;
                    }
                }
                return `http://${window.location.hostname}:8082/bcs/user/avatar?user_id=${id}`;
            },

            description() {
                let name;
                if (typeof this.user == 'string') {
                    name = this.user;
                } else {
                    if (typeof this.user.user != 'undefined') {
                        name = this.user.displayName;
                    } else {
                        name = this.user.displayname;
                    }
                }

                return `Profilbild von ${name}`;
            }
        }
    }
</script>