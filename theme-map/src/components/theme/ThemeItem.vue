<script setup>
const props = defineProps({ theme: Object });

const theme = props.theme;
</script>

<template>
  <div id="container">
    <router-link to="detail">
      <div>{{ theme.themeName }} </div>
    </router-link>
  </div>
  <br />
</template>

<style scoped>
#container {
  margin: 2%;
}
</style>
