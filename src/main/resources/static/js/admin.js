let app = new Vue({
    el: '#app',
    methods: {
        createPerson: async function () {
            let name = this.personName
            let race = this.race
            let personClass = this.personClass
            if (race && personClass && name) {
                const formData = new FormData();
                formData.append('name', name)
                formData.append('raceId', race.id)
                formData.append('personClassId', personClass.id)
                await fetch('/person', {
                    method: 'post',
                    body: formData
                }).then(async (response) => {
                    if (response.ok) {
                        this.persons = await response.json()
                    } else {
                        alert('Ошибка при создании персонажа: возможно, данное имя уже занято')
                    }
                })
            } else {
                alert("Заполните форму!")
            }
        }
    },
    watch: {
        sideName: async function (sideName, _) {
            this.races = await(await fetch('/race?sideId='+this.side.id)).json()
        }
    },
    async created () {
        if (await(await fetch('/TypeAccount')).text()!="admin"){
            document.location.href = '/logout'
        }
        this.persons = await(await fetch('/allPerson')).json()
        this.sides = await(await fetch('/side')).json()
        this.personClasses = await(await fetch('/person_class')).json()
    },
    data: {
        personName: '',
        personClassName: '',
        sideName: '',
        raceName: '',
        personClasses: [],
        races: [],
        sides: [],
        persons: []
    },
    computed: {
        personClass: {
            get: function() {
                return this.personClasses.find(personClass => personClass.name === this.personClassName)
            }
        },
        side: {
            get: function() {
                return this.sides.find(side => side.name === this.sideName)
            }
        },
        race: {
            get: function() {
                return this.races.find(race => race.name === this.raceName)
            }
        }
    }
})
