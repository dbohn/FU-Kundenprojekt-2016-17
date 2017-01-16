<template>
    <div style="margin-bottom: 10px;">
        <div class="modal fade" id="createModal">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Neue Konversation</h5>
                    </div>
                    <div class="modal-body">
                        <div class="form-group" :class="{'has-danger': hasError('title')}">
                            <label for="title">Titel:</label>
                            <input type="text" name="title" id="title" class="form-control" v-model="createForm.title" placeholder="Betreff der Konversation">
                            <small id="emailHelp" class="form-control-feedback" v-if="hasError('title')">{{ errors.title }}</small>
                        </div>
                        <div class="form-group" :class="{'has-danger': hasError('recipients')}">
                            <label for="friends">Empfänger:</label>
                            <friends :friends="friends" v-model="createForm.recipients"></friends>
                            <small id="friendsHelp" class="form-control-feedback" v-if="hasError('recipients')">{{ errors.recipients }}</small>
                        </div>
                        <div class="form-group" :class="{'has-danger': hasError('message')}">
                            <label for="message">Nachricht:</label>
                            <message-editor name="message" id="message" v-model="createForm.message"></message-editor>
                            <small id="messageHelp" class="form-control-feedback" v-if="hasError('message')">{{ errors.message }}</small>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" @click.prevent="postConversation">
                            <i class="fa fa-circle-o-notch fa-spin fa-fw" v-if="creating"></i> Konversation starten
                        </button>
                        <button type="button" class="btn btn-secondary" @click.prevent="closeModal">Schließen</button>
                    </div>
                </div>
            </div>
        </div>
        <button class="btn btn-primary" @click="createConversation"><i class="fa fa-plus"></i> Neue Konversation</button>
    </div>
</template>
<script>

    import Dispatcher from '../Dispatcher';
    import FriendSelector from './FriendSelector.vue';
    import MessageEditor from './MessageEditor.vue';

    export default {
        data() {
            return {
                settings: Settings,
                createForm: {
                    title: "",
                    recipients: [],
                    message: ""
                },
                creating: false,
                friends: {},
                errors: {},
            };
        },

        methods: {
            createConversation() {
                $('#createModal').modal();
                this.loadFriends();
            },

            reset() {
                this.createForm.title = "";
                this.createForm.message = "";
                this.createForm.recipients = [];
            },

            loadFriends() {
                $.get(this.settings.thisUrl + "/friends").then((data) => {
                    this.friends = data.message.friends;
                });
            },

            closeModal() {
                $('#createModal').modal('hide');
            },

            postConversation() {
                this.errors = {};
                if (this.createForm.title == "") {
                    this.errors.title = "Der Titel darf nicht leer sein";
                }

                if (this.createForm.recipients.length == 0) {
                    this.errors.recipients = "Bitte Empfänger auswählen";
                }

                if (this.createForm.message == "") {
                    this.errors.message = "Bitte eine Nachricht angeben!";
                }

                if (!this.anyError()) {
                    this.creating = true;
                    $.post(this.settings.thisUrl + "/conversations/create", this.createForm).then(data => {
                        this.creating = false;
                        this.reset();
                        this.$emit('done');
                        Dispatcher.$emit('conversation.created');
                        this.closeModal();
                    });
                }
            },

            hasError(error) {
                return Object.keys(this.errors).includes(error);
            },

            anyError() {
                return Object.keys(this.errors).length > 0;
            }
        },

        components: {
            'friends': FriendSelector,
            'message-editor': MessageEditor
        }
    }
</script>