<template>
  <Line
    :chart-options="chartOptions"
    :chart-data="chartData"
    :chart-id="chartId"
    :dataset-id-key="datasetIdKey"
    :css-classes="cssClasses"
    :styles="styles"
    :width="width"
    :height="height"
  />
</template>

<script>
import { Line } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  CategoryScale,
  LinearScale,
  TimeScale,
  PointElement,
  Decimation,
} from "chart.js";
ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  CategoryScale,
  LinearScale,
  TimeScale,
  PointElement,
  Decimation,
);

import "chartjs-adapter-date-fns";

const decimation = {
  enabled: true,
  algorithm: "lttb",
  samples: 10,
  threshold: 20,
};

export default {
  name: "LineChart",
  components: { Line },
  props: {
    chartId: {
      type: String,
      default: "line-chart-1",
    },
    datasetIdKey: {
      type: String,
      default: "label",
    },
    width: {
      type: Number,
      default: 800,
    },
    height: {
      type: Number,
      default: 600,
    },
    cssClasses: {
      default: "",
      type: String,
    },
    styles: {
      type: Object,
      default: () => {},
    },
    dataProp: {
      type: Array,
      default: () => [],
    },
    tempLabel: {
      type: String,
      default: () => "Test",
    },
  },
  data: () => ({
    chartData: {
      datasets: [
        {
          label: null,
          data: null,
          borderColor: "#eb6d6d",
          borderWidth: 2,
          pointRadius: 2,
        },
      ],
    },
    testData: [],
    show: false,
    chartOptions: {
      responsive: true,
      maintainAspectRatio: true,
      parsing: false,
      scales: {
        x: {
          type: "time",
        },
      },
      plugins: { decimation: decimation },
    },
  }),

  mounted() {
    this.show = false;
    this.testData = this.dataProp;

    this.chartData.datasets[0].data = this.testData;
    this.chartData.datasets[0].label = this.tempLabel;
    this.show = true;
  },
};
</script>

<style></style>
