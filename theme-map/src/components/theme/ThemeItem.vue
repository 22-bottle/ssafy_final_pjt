<script setup>
const props = defineProps({ theme: Object });

const theme = props.theme;
</script>

<template>
  <div id="container">
    <div>{{ theme.themeName }} </div>
  </div>
  <br />
</template>

<style scoped>
#container {
  margin: 2%;
}
</style>
