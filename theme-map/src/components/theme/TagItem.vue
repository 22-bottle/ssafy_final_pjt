<script setup>
const props = defineProps({ tag: Object });
</script>

<template>
    <div>#{{ tag.tagName }}</div>
</template>

<style scoped></style>