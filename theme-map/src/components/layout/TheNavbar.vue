<script setup></script>

<template>
  <div class="flex fixed" id="navbar">
    <span class="title">갈만할지도</span>
    <span id="nav_btns">
      <button class="">지도로</button>
      <button class="">테마별로</button>
      <button class="">에디터별로</button>
      <button class="">로그인</button>
    </span>
  </div>
</template>

<style scoped>
.flex {
  display: flex;
}
.fixed {
  position: fixed;
}
.inline_block {
  display: inline-block;
}
#navbar {
  flex-direction: row;
}
#nar_btns {
  justify-content: space-around;
}
</style>
