<script setup>
const props = defineProps({ place: Object });

const place = props.place;

</script>

<template>
  <div id="container">
    <div>{{ place.placeName }}</div>
  </div>
</template>

<style scoped>
* {
  background-color: white;
}
#container {
  width: 87%;
  padding: 10px;
  font-size: 25px;
}
</style>
