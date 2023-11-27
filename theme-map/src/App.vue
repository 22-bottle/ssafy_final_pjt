<script setup>
import { RouterView } from 'vue-router';
import TheHeading from './components/layout/TheHeading.vue';
import TheNavbar from './components/layout/TheNavbar.vue';
</script>

<template>
  <div>
    <the-heading></the-heading>
    <the-navbar></the-navbar>
    <router-view></router-view>
  </div>
</template>

<style scoped></style>
