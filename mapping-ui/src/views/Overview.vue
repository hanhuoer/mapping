<template>
    <div class="overview">
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card class="box-card">
                    <div class="clearfix" slot="header">
                        <span>Overview</span>
                    </div>
                    <div class="label-container">
                        <p class="label"><span class="label-title">version:</span><span class="label-item">{{overviewData.version}}</span>
                        </p>
                        <p class="label"><span class="label-title">bind port:</span><span class="label-item">{{overviewData.bindPort}}</span>
                        </p>
                        <p class="label"><span class="label-title">web port:</span><span class="label-item">{{overviewData.webPort}}</span>
                        </p>
                        <p class="label"><span class="label-title">client total:</span><span class="label-item">{{overviewData.clientTotal}}</span>
                        </p>
                        <p class="label"><span class="label-title">client online:</span><span class="label-item">{{overviewData.clientOnline}}</span>
                        </p>
                        <p class="label"><span class="label-title">proxy total:</span><span class="label-item">{{overviewData.proxyTotal}}</span>
                        </p>
                        <p class="label"><span class="label-title">proxy active:</span><span class="label-item">{{overviewData.proxyActive}}</span>
                        </p>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card class="box-card">
                    <v-chart :autoresize="true" :option="trafficChartOption" class="traffic-chart"/>
                    <v-chart :autoresize="true" :option="trafficTimesChartOption" class="traffic-times-chart"/>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {use} from "echarts/core";
    import {CanvasRenderer} from "echarts/renderers";
    import {PieChart} from "echarts/charts";
    import {LegendComponent, TitleComponent, TooltipComponent} from "echarts/components";
    import VChart from "vue-echarts";
    import {overview, traffic} from "@/api/home"

    use([
        CanvasRenderer,
        PieChart,
        TitleComponent,
        TooltipComponent,
        LegendComponent
    ]);

    export default {
        name: "Overview",
        components: {
            VChart
        },
        provide: {
            // [THEME_KEY]: "dark"
        },
        data() {
            return {
                overviewData: {},
                trafficData: {},
                trafficChartData: [],
                trafficChartOption: {},
                trafficTimesChartData: [],
                trafficTimesChartOption: {}
            }
        },
        created() {
            this.getOverview();
            this.getTraffic();
        },
        mounted() {
        },
        watch: {
            trafficData: {
                handler(data) {
                    this.trafficChartData = [];
                    this.trafficChartData.push({name: "in bytes", value: data.in});
                    this.trafficChartData.push({name: "out bytes", value: data.out});
                    this.loadTrafficChart();

                    this.trafficTimesChartData = [];
                    this.trafficTimesChartData.push({name: "in times", value: data.inTimes});
                    this.trafficTimesChartData.push({name: "out times", value: data.outTimes});
                    this.loadTrafficTimesChart();
                },
                deep: true
            }
        },
        methods: {
            getOverview: function () {
                overview().then(response => {
                    this.overviewData = response.data
                });
            },
            getTraffic: function () {
                traffic().then(response => {
                    this.trafficData = response.data;
                })
            },

            loadTrafficChart: function () {
                this.trafficChartOption = {
                    title: {
                        text: "Traffic",
                        left: "center"
                    },
                    tooltip: {
                        trigger: "item",
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: "vertical",
                        left: "left",
                        data: [
                            "in bytes",
                            "out bytes"
                        ]
                    },
                    series: [
                        {
                            name: "Traffic",
                            type: "pie",
                            radius: "55%",
                            center: ["50%", "60%"],
                            data: this.trafficChartData,
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: "rgba(0, 0, 0, 0.5)"
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: function (params) {
                                        let colorArr = ["#333333", "#deb887"];
                                        return colorArr[params.dataIndex % colorArr.length];
                                    },
                                }
                            }
                        }
                    ]
                }
            },
            loadTrafficTimesChart: function () {
                this.trafficTimesChartOption = {
                    title: {
                        text: "Traffic Times",
                        left: "center"
                    },
                    tooltip: {
                        trigger: "item",
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                        orient: "vertical",
                        left: "left",
                        data: [
                            "in times",
                            "out times"
                        ]
                    },
                    series: [
                        {
                            name: "Traffic Times",
                            type: "pie",
                            radius: "55%",
                            center: ["50%", "60%"],
                            data: this.trafficTimesChartData,
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: "rgba(0, 0, 0, 0.5)"
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: function (params) {
                                        let colorArr = ["#333333", "#deb887"];
                                        return colorArr[params.dataIndex % colorArr.length];
                                    },
                                }
                            }
                        }
                    ]
                }
            }

        }
    }
</script>

<style lang="scss" scoped>
    .overview {
        min-width: 650px;

        .label-container {
            min-width: 250px;
        }

        .label {
            text-align: center;
            padding: 10px 0;
        }

        .label-title, .label-item {
            display: inline-block;
            padding: 0 5px;
        }

        .label-title {
            width: 120px;
            text-align: right;
        }

        .label-item {
            width: 80px;
            text-align: left;
        }

        .traffic-chart, .traffic-times-chart {
            width: 100%;
            height: 400px;
        }
    }
</style>
