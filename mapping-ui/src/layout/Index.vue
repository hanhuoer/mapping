<template>
  <div class="layout">
    <Header class="header-container"></Header>
    <Content class="content-container"></Content>
    <Footer class="footer-container"></Footer>
  </div>
</template>

<script>
import Header from "@/layout/Header"
import Footer from "@/layout/Footer"
import Content from "@/layout/Content";

export default {
  name: "Index",
  components: {
    Header,
    Footer,
    Content
  }
}
</script>

<style scoped>
.layout {
  display: flex;
  flex-direction: column;
  height: 100vh;
}

.header-container {

}

.content-container {
  flex: 1;
  overflow-y: auto;
}

.footer-container {

}
</style>