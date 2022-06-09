<template>
  <div class="col-span-2 flex flex-col gap-2">
    <div class="bg-white p-4 rounded-sm shadow-md">
      <LineChart :dataProp="tempArr" v-if="dataLoaded" :tempLabel="'Tjedan'" />
    </div>
    <div class="bg-white p-4 rounded-sm shadow-md">
      <LineChart
        :dataProp="last24Hrs"
        v-if="dataLoaded2"
        :chartId="'line-chart-2'"
        :tempLabel="'24h'"
      />
    </div>
  </div>
</template>

<script>
import LineChart from "@/components/LineChart.vue";
import { mockTemp24Hrs } from "@/mocks/mockTempData.js";

export default {
  name: "AvgTempHistory",
  data() {
    return {
      tempArr: [],
      dataLoaded: false,
      dataLoaded2: false,
      last24Hrs: [],
    };
  },
  components: { LineChart },

  methods: {
    async fetchTempData() {
      const getOptions = {
        method: "GET",
        /* TODO: Zamijeniti token sa storeom */
        headers: {
          "Content-Type": "application/json",
          Authorization:
            "Bearer " +
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIzZGNkYmI0ZGJkZDA0MWRkYmY3ODkwZWE0MzVmZTM4YyIsImlhdCI6MTY1MzU1NDAzMSwiZXhwIjoxOTY4OTE0MDMxfQ.u_QQyMTrzd91g9HfuVY-DROGGawLbatsF_88eJSHOPE",
        },
      };

      const currentDate = new Date();
      const lastWeek = new Date(
        currentDate.getTime() - 7 * 24 * 60 * 60 * 1000,
      );

      const res = await fetch(
        "http://10.19.4.140:8123/api/history/period/" +
          lastWeek.toISOString() +
          "?filter_entity_id=sensor.average_temperature" +
          "&end_time=" +
          encodeURIComponent(currentDate.toISOString()),
        getOptions,
      );
      const data = await res.json();
      var filteredData = data[0].filter((datapoint) => {
        return datapoint.state != "unknown";
      });
      return filteredData.map((datapoint) => {
        return {
          x: Date.parse(datapoint.last_changed),
          y: Number.parseFloat(datapoint.state),
        };
      });
    },

    async fetchLast24Hrs() {
      const getOptions = {
        method: "GET",
        /* TODO: Zamijeniti token sa storeom */
        headers: {
          "Content-Type": "application/json",
          Authorization:
            "Bearer " +
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIzZGNkYmI0ZGJkZDA0MWRkYmY3ODkwZWE0MzVmZTM4YyIsImlhdCI6MTY1MzU1NDAzMSwiZXhwIjoxOTY4OTE0MDMxfQ.u_QQyMTrzd91g9HfuVY-DROGGawLbatsF_88eJSHOPE",
        },
      };
      const res = await fetch(
        "http://10.19.4.140:8123/api/history/period?filter_entity_id=sensor.average_temperature",
        getOptions,
      );
      const data = await res.json();
      console.log(data);

      return this.prepareData(data);

      /* var filteredData = data[0].filter((datapoint) => {
        return datapoint.state != "unknown";
      });
      return filteredData.map((datapoint) => {
        return {
          x: Date.parse(datapoint.last_changed),
          y: Number.parseFloat(datapoint.state),
        };
      }); */
    },

    prepareData(data) {
      console.log("PrepareData called with data:  " + data);
      var filteredData = data[0].filter((datapoint) => {
        return datapoint.state != "unknown";
      });
      return filteredData.map((datapoint) => {
        return {
          x: Date.parse(datapoint.last_changed),
          y: Number.parseFloat(datapoint.state),
        };
      });
    },
  },

  async created() {
    if (import.meta.env.VITE_TESTIRANJE == "true") {
      this.last24Hrs = this.prepareData(mockTemp24Hrs);
      this.dataLoaded = true;
      this.dataLoaded2 = true;
    } else {
      this.tempArr = await this.fetchTempData();
      this.last24Hrs = await this.fetchLast24Hrs();
      this.dataLoaded2 = true;
      this.dataLoaded = true;
    }
  },
};
</script>

<style></style>
