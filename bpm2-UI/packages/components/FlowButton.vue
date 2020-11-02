<template>
  <el-dialog :visible.sync="dialog" :close-on-click-modal="false" top="5vh" title="添加监听器" append-to-body width="600px"
    @close="cancel">
    <el-table ref="multipleTable" :data="buttons" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center" width="55" />
      <el-table-column prop="name" label="名称" align="center" />
      <el-table-column prop="name" label="编码" align="center" />
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" size="small" @click="cancel">取消</el-button>
      <el-button type="primary" size="small" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { buttons } from './data/buttons'
export default {
  name: 'FlowButton',
  data() {
    return {
      dialog: false,
      buttons,
      multipleSelection: []
    }
  },
  methods: {
    doSubmit() {
      this.$emit('callback', this.multipleSelection)
      this.cancel()
    },
    cancel() {
      this.multipleSelection = []
      this.$refs.multipleTable.clearSelection()
      this.dialog = false
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    }
  }
}
</script>