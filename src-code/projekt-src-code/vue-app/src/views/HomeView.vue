<template>
  <div class="grid grid-cols-5 gap-4 p-4 z-10 bg-slate-100">
    <div class="col-span-3 grid grid-cols-2 gap-4 h-fit">
      <div class="h-min shadow-md flex flex-col w-full bg-white rounded-sm">
        <bulbs />
        <bulb-color />
      </div>
      <div class="flex flex-col gap-4 items-center">
        <presence-card />
        <current-temp-card />
      </div>
    </div>
    <avg-temp-history />
  </div>
</template>

<script>
import AvgTempHistory from "../components/AvgTempHistory.vue";
import Bulbs from "@/components/Bulbs.vue";
import CurrentTempCard from "@/components/CurrentTempCard.vue";
import BulbColor from "@/components/BulbColor.vue";
import PresenceCard from "@/components/PresenceCard.vue";
export default {
  components: {
    AvgTempHistory,
    Bulbs,
    CurrentTempCard,
    BulbColor,
    PresenceCard,
  },
};
</script>

<style></style>
