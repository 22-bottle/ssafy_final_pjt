<script setup>
const props = defineProps({ place: Object });

const place = props.place;
const emit = defineEmits(['detail']);

const handlePlace = () => {
  console.log('Enter handlePlace method');
  console.log(place);
  emit('add', place);
};
</script>

<template>
  <div id="container" @click="handlePlace">
    <div>{{ place.placeName }} </div>
  </div>
  <br />
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
