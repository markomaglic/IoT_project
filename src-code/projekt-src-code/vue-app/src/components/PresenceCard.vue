<template>
  <div v-if="show" class="rounded-sm shadow-md h-min bg-white w-full">
    <div class="text-center py-4 rounded-t-sm bg-circles bg-zinc-700">
      <span class="text-white font-extrabold font-mono tracking-wider text-xl"
        >prisustva</span
      >
    </div>
    <div class="py-2 flex flex-col gap-2 items-stretch">
      <li
        v-for="(presence, index) in last10Presences"
        :key="index"
        class="list-none text-center hover:bg-zinc-200 first:pt-2 last:pb-2"
      >
        {{ presence.last_changed }}
      </li>
    </div>
  </div>
</template>

<script>
import { mockPresence } from "@/mocks/mockPresence";
export default {
  name: "PresenceCard",
  data() {
    return {
      last10Presences: [],
      show: false,
    };
  },

  methods: {
    prepareData(data) {
      var filtered = data[0].filter((datapoint) => {
        return datapoint.state == "on";
      });

      return filtered
        .map((datapoint) => {
          return {
            state: datapoint.state,
            last_changed: new Date(datapoint.last_changed).toLocaleString(),
          };
        })
        .slice(-10)
        .reverse();
    },
  },

  async created() {
    this.show = false;

    if (import.meta.env.VITE_TESTIRANJE == "true") {
      this.last10Presences = this.prepareData(mockPresence);
      this.show = true;
    } else {
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
        "http://10.19.4.140:8123/api/history/period?filter_entity_id=binary_sensor.waspmotepir",
        getOptions,
      );
      const data = await res.json();
      this.last10Presences = this.prepareData(data);
      this.show = true;
    }
  },
};
</script>
