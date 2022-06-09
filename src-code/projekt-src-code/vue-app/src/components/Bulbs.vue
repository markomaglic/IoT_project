<template>
  <div v-if="show">
    <div class="text-center p-4 rounded-t-sm bg-formal bg-zinc-700">
      <span class="text-white font-extrabold font-mono tracking-wider text-xl"
        >phillips</span
      >
    </div>

    <div class="flex justify-around mt-2">
      <h2><Toggle v-model="status1" @click="toggleBulb('', status1)" /></h2>
      <h2><Toggle v-model="status2" @click="toggleBulb('2', status2)" /></h2>
      <h2><Toggle v-model="status3" @click="toggleBulb('3', status3)" /></h2>
    </div>
  </div>
</template>

<script>
import Toggle from "@vueform/toggle";
export default {
  name: "Bulbs",
  components: {
    Toggle,
  },
  data() {
    return {
      show: false,
      status1: null,
      status2: null,
      status3: null,
    };
  },

  methods: {
    async toggleBulb(bulbId, status) {
      // Ako je testiranje, nećemo bezveze slati zahtjev
      if (import.meta.env.VITE_TESTIRANJE == "true") return;

      const postOptions = {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization:
            "Bearer " +
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIzZGNkYmI0ZGJkZDA0MWRkYmY3ODkwZWE0MzVmZTM4YyIsImlhdCI6MTY1MzU1NDAzMSwiZXhwIjoxOTY4OTE0MDMxfQ.u_QQyMTrzd91g9HfuVY-DROGGawLbatsF_88eJSHOPE",
        },
        body: JSON.stringify({
          entity_id: "light.philipsbulb" + bulbId,
        }),
      };

      if (status == false) {
        const res = await fetch(
          "http://10.19.4.140:8123/api/services/light/turn_off",
          postOptions,
        );
      } else {
        const res = await fetch(
          "http://10.19.4.140:8123/api/services/light/turn_on",
          postOptions,
        );
      }
    },

    async fetchBulbStatus(bulbId) {
      const getOptions = {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization:
            "Bearer " +
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIzZGNkYmI0ZGJkZDA0MWRkYmY3ODkwZWE0MzVmZTM4YyIsImlhdCI6MTY1MzU1NDAzMSwiZXhwIjoxOTY4OTE0MDMxfQ.u_QQyMTrzd91g9HfuVY-DROGGawLbatsF_88eJSHOPE",
        },
      };
      const res = await fetch(
        "http://10.19.4.140:8123/api/states/light.philipsbulb" + bulbId,
        getOptions,
      );
      const data = await res.json();
      if (data.state == "off") return false;
      if (data.state == "on") return true;
    },
  },

  async created() {
    if (import.meta.env.VITE_TESTIRANJE == "true") {
      this.status1 = false;
      this.status2 = false;
      this.status3 = false;
      this.show = true;
    } else {
      /* TODO: Ažurirati sa ikonama svjetiljki */
      this.status1 = await this.fetchBulbStatus("");
      this.status2 = await this.fetchBulbStatus("2");
      this.status3 = await this.fetchBulbStatus("3");
      this.show = true;
    }
  },
};
</script>

<style src="@vueform/toggle/themes/default.css"></style>
