<template>
  <div id="app">
    <el-button class="filter-item" size="mini" type="primary" icon="el-icon-plus" @click="add">新增</el-button>
    <flow-designer ref="FlowDesigner" :is-add="true" />
  </div>
</template>

<script>
export default {
  name: 'App',
  methods: {
    add() {
      this.$refs.FlowDesigner.dialog = true
    }
  }
}
</script>

<style>
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>