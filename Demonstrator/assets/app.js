
window.$ = window.jQuery = require('jquery');

window.Tether = require('tether');

require('bootstrap');

import Vue from 'vue';

import ChatApp from './ChatApp.vue';

new Vue({
    el: '#app',
    data: {

    },
    components: {
        ChatApp
    }
});