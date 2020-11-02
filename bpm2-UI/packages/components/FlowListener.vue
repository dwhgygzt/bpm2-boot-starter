<template>
  <el-dialog :visible.sync="dialog" :close-on-click-modal="false" title="添加监听器" append-to-body width="900px">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="事件类型">
        <el-select v-model="form.eventType" placeholder="请选择">
          <el-option v-for="item in eventTypes" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="监听类型">
        <el-select v-model="form.listenerType" placeholder="请选择">
          <el-option v-for="item in listenerTypes" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="值" prop="value">
        <el-input v-model="form.value" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" size="small" @click="cancel">取消</el-button>
      <el-button type="primary" size="small" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { randomString } from '../utils/index'
export default {
  name: 'FlowListener',
  data() {
    return {
      dialog: false,
      eventTypes: [{
          value: 'start',
          label: 'start'
        },
        {
          value: 'take',
          label: 'take'
        },
        {
          value: 'end',
          label: 'end'
        }
      ],
      listenerTypes: [{
          value: 'class',
          label: '类'
        },
        {
          value: 'expression',
          label: '表达式'
        },
        {
          value: 'delegateExpression',
          label: '代理表达式'
        }
      ],
      form: {
        id: '',
        eventType: 'start',
        listenerType: 'class',
        value: ''
      },
      rules: {
        value: [{
          required: true,
          message: '值不能为空',
          trigger: 'blur'
        }]
      }
    }
  },
  methods: {
    randomString,
    cancel() {
      this.dialog = false
      this.form = {
        id: '',
        eventType: 'start',
        listenerType: 'class',
        value: ''
      }
    },
    doSubmit() {
      this.form.id = randomString(16)
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$emit('callback', this.form)
          this.cancel()
        } else {
          return false
        }
      })
    }
  }
}
</script>