<template>
    <div class="layout">
        <Header class="header-container"></Header>
        <div class="body-container">
            <Menu class="menu-container"></Menu>
            <Content class="content-container"></Content>
        </div>
<!--        <Footer class="footer-container"></Footer>-->
    </div>
</template>

<script>
    import Header from "@/layout/Header"
    import Footer from "@/layout/Footer"
    import Menu from "@/layout/Menu";
    import Content from "@/layout/Content";

    export default {
        name: "Index",
        components: {
            Header,
            Footer,
            Menu,
            Content
        }
    }
</script>

<style lang="scss" scoped>
    .layout {
        display: flex;
        flex-direction: column;
        height: 100vh;

        .header-container {

        }

        .body-container {
            flex: 1;
            overflow-y: auto;
            display: flex;
            flex-direction: row;

            .menu-container {

            }

            .content-container {
                flex: 1;
            }
        }

        .footer-container {

        }
    }

</style>
