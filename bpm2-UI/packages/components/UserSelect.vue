<template>
  <el-dialog :visible.sync="dialog" :close-on-click-modal="false" title="用户选择" append-to-body top="5vh" width="1200px">
    <div class="app-container">
      <!--表格渲染-->
      <el-table ref="multipleTable" :data="data" :highlight-current-row="!multiple" size="small" style="width: 100%;"
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="date" label="日期" width="180">
        </el-table-column>
        <el-table-column prop="realName" label="姓名" width="180">
        </el-table-column>
        <el-table-column prop="address" label="地址">
        </el-table-column>
      </el-table>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" size="small" @click="cancel">取消</el-button>
      <el-button type="primary" size="small" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'UserSelect',
  data() {
    return {
      dialog: false,
      multiple: false,
      selectedUsers: [],
      data: [{
        id: '1',
        date: '2016-05-02',
        realName: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      }, {
        id: '2',
        date: '2016-05-04',
        realName: '王小虎',
        address: '上海市普陀区金沙江路 1517 弄'
      }, {
        id: '3',
        date: '2016-05-01',
        realName: '王小虎',
        address: '上海市普陀区金沙江路 1519 弄'
      }, {
        id: '4',
        date: '2016-05-03',
        realName: '王小虎',
        address: '上海市普陀区金沙江路 1516 弄'
      }]
    }
  },
  methods: {
    handleSelectionChange(val) {
      this.selectedUsers = val
    },
    cancel() {
      this.multipleSelection = []
      this.$refs.multipleTable.clearSelection()
      this.dialog = false
    },
    doSubmit() {
      if (!this.multiple && this.selectedUsers.length > 1) {
        this.$message.error('你最多只能选择一个用户')
        return
      }
      this.$emit('callback', this.selectedUsers)
      this.cancel()
    }
  }
}
</script>