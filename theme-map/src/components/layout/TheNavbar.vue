<script setup></script>

<template>
  <div class="flex fixed" id="navbar">
    <span class="title" id="logo">갈만할지도</span>
    <div class="flex" id="nav_btns">
      <button class="selected_btn">지도로</button>
      <button class="nav_btn">테마별로</button>
      <button class="nav_btn">에디터별로</button>
      <button class="log_btn">로그인</button>
    </div>
  </div>
</template>

<style scoped>
body, html {
  width: 100%;
}
.flex {
  display: flex;
}
.fixed {
  position: fixed;
}
#navbar {
  top: 0;
  left: 0;
  width: 96%;
  flex-direction: row;
  justify-content: space-between;
  padding: 2%;
  box-shadow: 0 0 28px rgba(0,0,0,0.25);
  background-color: white;
  z-index: 11;
}
#logo {
  font-size: 40px;
}
#nav_btns {
  width: 60%;
  flex-direction: row;
  justify-content: space-around
}
.selected_btn {
  background-color: white;
  font-size: 25px;
  width: 130px;
  border: none;
  border-bottom: 5px solid;
  color: #016ef5;
}
.nav_btn {
  background-color: white;
  font-size: 25px;
  width: 130px;
  border: none;
  color: black;
}
.nav_btn:hover {
  color: #016ef5;
  border-bottom: 5px solid;
}
.log_btn {
  background-color: white;
  font-size: 25px;
  width: 130px;
  border-radius: 15px;
  border-color: #016ef5;
}
</style>