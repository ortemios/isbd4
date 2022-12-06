let app = new Vue({
    el: '#app',
    methods: {

    },
    watch: {
        side: async function (sideName, _) {
            this.side =
            this.races = await(await fetch('/race?sideId='+side.id)).json()
        }
    },
    async created () {
        this.persons = await(await fetch('/person')).json()
        this.sides = await(await fetch('/side')).json()
        this.personClasses = await(await fetch('/person_class')).json()
    },
    data: {
        name: '',
        personClass: null,
        side: null,
        race: null,
        persons: [],
        personClasses: [],
        races: [],
        sides: []
    }
})
