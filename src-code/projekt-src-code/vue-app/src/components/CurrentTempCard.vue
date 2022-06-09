<template>
  <div
    class="flex align-baseline bg-yellow-500 p-2 w-72 rounded-sm -mb-10 z-10 items-center justify-center"
  >
    <img :src="'src/assets/thermometer-icon.png'" class="w-8 h-8" />
    <span class="font-bold font-mono text-l"> temperatura </span>
  </div>
  <div
    class="w-64 h-min rounded-sm shadow-md bg-white border-yellow-500 border-2 p-4 pt-8"
  >
    <div class="flex flex-col items-end">
      <div class="font-bold">Prosjek</div>
      <div class="text-center mb-2">{{ temp }} °C</div>
      <div>Waspmote</div>
      <div class="mb-2">{{ waspmoteTemp }} °C</div>
      <div>Aqara</div>
      <div>{{ aqaraTemp }} °C</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CurrentTempCard",

  data() {
    return {
      show: false,
      temp: null,
      waspmoteTemp: null,
      aqaraTemp: null,
    };
  },

  methods: {
    setTimer() {
      setInterval(() => this.fetchTemp(), 5000);
    },
    async fetchTemp() {
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
        "http://10.19.4.140:8123/api/states/sensor.average_temperature",
        getOptions,
      );
      const res2 = await fetch(
        "http://10.19.4.140:8123/api/states/sensor.waspmotetemp",
        getOptions,
      );
      const res3 = await fetch(
        "http://10.19.4.140:8123/api/states/sensor.aqaratemp_temperature",
        getOptions,
      );
      const data = await res.json();
      const data2 = await res2.json();
      const data3 = await res3.json();
      this.temp = data.state;
      this.waspmoteTemp = data2.state;
      this.aqaraTemp = data3.state;
    },
  },
  async created() {
    if (import.meta.env.VITE_TESTIRANJE == "true") {
      this.temp = 24.3;
      this.waspmoteTemp = 28.6;
      this.aqaraTemp = 20.0;
    } else {
      await this.fetchTemp();
      this.setTimer();
    }
  },
};
</script>

<style></style>
