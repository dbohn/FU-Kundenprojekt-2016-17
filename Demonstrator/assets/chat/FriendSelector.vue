<template>
    <div class="friends">
        <div class="d-flex friend align-items-center justify-content-start" :class="{'active': isSelected(friend)}" @click="selectFriend(friend)" v-for="friend in friends">
            <avatar :user="friend"></avatar>
            <span>{{ friend.displayname }}</span>
            <i class="ml-auto mr-2 fa checkbox" :class="{'fa-check-square-o': isSelected(friend), 'fa-square-o': !isSelected(friend)}"></i>
        </div>
    </div>
</template>
<script>
    import Avatar from '../Avatar.vue';

    /**
     * This component is used to display a friend list
     * in the create conversation view.
     */
    export default {
        data() {
            return {
                selected: [],
            }
        },

        props: ['value', 'friends'],

        watch: {
            selected(newVal) {
                this.$emit('input', newVal);
            },

            value(newVal) {
                this.selected = newVal;
            }
        },

        mounted() {
            this.selected = this.value;
        },

        methods: {

            selectFriend(friend) {
                let index = this.selected.indexOf(friend.id);
                if (index > -1) {
                    this.selected.splice(index, 1);
                } else {
                    this.selected.push(friend.id);
                }
            },

            isSelected(friend) {
                return this.selected.includes(friend.id);
            }
        },

        components: {
            Avatar
        }
    }
</script>