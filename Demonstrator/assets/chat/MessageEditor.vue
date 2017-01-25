<template>
    <div>
        <textarea ref="text"></textarea>
    </div>
</template>
<script>
    import SimpleMDE from 'simplemde';

    export default {
        data() {
            return {
                editor: null
            };
        },

        props: ['value'],

        watch: {

            value(newVal) {
                if (newVal == "") {
                    this.editor.value(newVal);
                }
            }
        },

        mounted() {
            this.editor = new SimpleMDE({
                element: this.$refs.text,
                spellChecker: false,
                status: false,
                autoDownloadFontAwesome: false,
            });

            this.editor.value(this.value);

            this.editor.codemirror.on('change', () => {
                this.$emit('input', this.editor.value())
            });

            this.text = this.value;
        },
        beforeDestroy() {
            this.editor.toTextArea();
        }
    }
</script>