let app = new Vue({
    el: '#app',
    methods: {
        submit() {
            this.addEntry([this.x, this.y, this.r, false])
        },
        shoot(x, y, r) {
            this.addEntry([x, y, r, false])
        },
        addEntry(entry) {
            this.history = [entry].concat(this.history)
        },
        loadHistory() {

        }
    },
    watch: {
        r: function (r, _) {
            canvas.setAreaRadius(r)
        },
        history: function (history, _) {
            canvas.setHistory(history)
        }
    },
    created() {
        this.loadHistory()
    },
    data: {
        x: 0,
        y: 0,
        r: 0,
        history: [],
    },
    components: {
        'multicheckbox': {
            props: {
                value: Number,
                options: Array,
            },
            template: `
                <div>
                    <label v-for="option in options">
                        <input
                            type="checkbox"
                            @change="$emit('input', option == value ? null : option)"
                            v-bind:checked="option == value">
                        {{ option }}
                    </label>
                </div>
            `,
        }
    },
})

let canvas = new CoordinatesCanvas('canvas',
    45,
    -2,
    2,
    -5,
    3,
    app.shoot
)