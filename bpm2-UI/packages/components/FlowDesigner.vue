<template>
	<el-dialog :append-to-body="true" :modal-append-to-body="false" :visible.sync="dialog" :title="title" fullscreen
		custom-class="form-write-dialog flow-design-dialog" @opened="handleOpen" @closed="handleClose">
		<el-tabs v-model="activeName" type="border-card" class="custom-tabs" @tab-click="handleTabClick">
			<el-tab-pane label="流程设计器" name="1">
				<el-container id="wrap" class="wrap">
					<el-main id="canvas" ref="canvas" class="canvas" />
					<el-aside class="aside" width="400px">
						<panel v-if="bpmnModeler" :modeler="bpmnModeler" />
					</el-aside>
				</el-container>
			</el-tab-pane>
			<el-tab-pane label="BPMN XML" name="2">
				<codemirror v-model="showXML" :options="cmOptions" class="wrap" />
			</el-tab-pane>
		</el-tabs>
		<div v-if="activeName === '1'" slot="footer" class="dialog-footer">
			<el-button-group>
				<el-tooltip class="item" effect="dark" content="导入本地BPMN" placement="bottom">
					<el-button size="mini" @click="openFile">
						<i class="el-icon-folder" title="导入本地BPMN" />
						<input ref="file" type="file" style="display: none" @change="showRealPath">
					</el-button>
				</el-tooltip>
				<el-tooltip class="item" effect="dark" content="创建BPMN" placement="bottom">
					<el-button size="mini" @click="openDiagram(diagramXML)">
						<i class="el-icon-folder-add" title="创建BPMN" />
					</el-button>
				</el-tooltip>
				<el-tooltip class="item" effect="dark" content="下载" placement="bottom">
					<el-button size="mini" @click="saveDiagram">
						<i class="el-icon-download" title="下载" />
					</el-button>
				</el-tooltip>
				<el-tooltip class="item" effect="dark" content="下载图片" placement="bottom">
					<el-button size="mini" @click="saveSVG">
						<i class="el-icon-picture" title="下载图片" />
					</el-button>
				</el-tooltip>
				<el-tooltip class="item" effect="dark" content="撤消" placement="bottom">
					<el-button size="mini" @click="undo()">
						<i class="el-icon-refresh-left" title="撤消" />
					</el-button>
				</el-tooltip>
				<el-tooltip class="item" effect="dark" content="恢复" placement="bottom">
					<el-button size="mini" @click="redo()">
						<i class="el-icon-refresh-right" title="恢复" />
					</el-button>
				</el-tooltip>
				<el-tooltip class="item" effect="dark" content="验证模型" placement="bottom">
					<el-button size="mini">
						<i class="el-icon-check" title="验证模型" />
					</el-button>
				</el-tooltip>
				<el-tooltip class="item" effect="dark" content="保存" placement="bottom">
					<el-button size="mini" @click="doSubmit">
						<i class="el-icon-folder-checked" title="保存" />
					</el-button>
				</el-tooltip>
			</el-button-group>
		</div>
	</el-dialog>
</template>

<script>
	import Modeler from './CustomModeler'
	import customTranslate from './CustomTranslate'
	import propertiesPanelModule from './CustomPanel'
	import propertiesProviderModule from './CustomPanel/lib/provider/flowable'
	import flowableModdleDescriptor from './CustomDescriptor/resources/flowable'
	import FileSaver from 'file-saver'
	import { codemirror } from 'vue-codemirror'

	import 'codemirror/mode/xml/xml.js'
	import 'codemirror/lib/codemirror.css'
	import 'codemirror/theme/idea.css'
	import 'codemirror/addon/fold/foldgutter.css'
	import 'codemirror/addon/fold/foldcode.js'
	import 'codemirror/addon/fold/foldgutter.js'
	import 'codemirror/addon/fold/xml-fold.js'
	import 'codemirror/addon/fold/indent-fold.js'
	import 'codemirror/addon/fold/brace-fold'
	import 'codemirror/addon/fold/markdown-fold.js'
	import 'codemirror/addon/fold/comment-fold.js'
	import 'codemirror/addon/selection/active-line'

	import panel from './PropertyPanel'

	export default {
		name: 'FlowDesigner',
		components: {
			codemirror,
			panel
		},
		props: {
			isAdd: {
				type: Boolean,
				required: true
			}
		},
		data() {
			return {
				modelId: null,
				// bpmn建模器
				bpmnModeler: null,
				xmlStr: null,
				encodedData: null,
				dialog: false,
				loading: false,
				title: '流程设计',
				diagramXML: '<?xml version="1.0" encoding="UTF-8"?>\n' +
					'<definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"\n' +
					'             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"\n' +
					'             xmlns:flowable="http://flowable.org/bpmn"\n' +
					'             xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI"\n' +
					'             xmlns:omgdc="http://www.omg.org/spec/DD/20100524/DC"\n' +
					'             xmlns:xsd="http://www.w3.org/2001/XMLSchema"\n' +
					'             targetNamespace="http://www.flowable.org/processdef">\n' +
					'  <process id="Process_1" name="测试流程" isExecutable="true">\n' +
					'    <startEvent id="startEvent1" name="开始" flowable:formFieldValidation="true" />\n' +
					'  </process>\n' +
					'  <bpmndi:BPMNDiagram id="BPMNDiagram_1">\n' +
					'    <bpmndi:BPMNPlane id="BPMNPlane_1" bpmnElement="Process_1">\n' +
					'      <bpmndi:BPMNShape id="BPMNShape_startEvent1" bpmnElement="startEvent1">\n' +
					'        <omgdc:Bounds x="120" y="200" width="36" height="36" />\n' +
					'      </bpmndi:BPMNShape>\n' +
					'    </bpmndi:BPMNPlane>\n' +
					'  </bpmndi:BPMNDiagram>\n' +
					'</definitions>',
				showXML: '',
				customTranslate: {
					translate: ['value', customTranslate]
				},
				activeName: '1',
				cmOptions: {
					tabSize: 4,
					mode: {
						name: 'xml',
						json: true
					},
					theme: 'idea',
					styleActiveLine: true,
					lineNumbers: true,
					line: true,
					foldgutter: true,
					gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter', 'CodeMirror-lint-markers'],
					lineWrapping: true, // 代码折叠
					foldGutter: true,
					matchBrackets: true, // 括号匹配
					autoCloseBrackets: true
				}
			}
		},
		created: function () {

		},
		methods: {
			cancel() {
				this.dialog = false
			},
			getFlowXml(modelId) {
				if (modelId === null) return
				// 服务端接口
				// loadXmlByModelId(modelId).then(res => {
				//     if (res) {
				//         this.openDiagram(res)
				//     }
				// })
			},
			openDiagram(xml) {
				this.bpmnModeler.importXML(xml, function (err) {
					if (err) {
						console.log('====>>>err', err)
					} else {
						console.log('====>>>导入成功')
					}
				})
			},
			handleOpen() {
				if (this.bpmnModeler) return
				this.bpmnModeler = new Modeler({
					container: '#canvas',
					additionalModules: [
						propertiesPanelModule,
						propertiesProviderModule,
						this.customTranslate
					],
					// needed if you'd like to maintain camunda:XXX properties in the properties panel
					moddleExtensions: {
						flowable: flowableModdleDescriptor
					}
				})
				if (this.isAdd) {
					this.openDiagram(this.diagramXML)
				} else {
					this.getFlowXml(this.modelId)
				}
			},
			handleClose() {
				this.openDiagram(this.diagramXML)
				// this.bpmnModeler = null
				this.activeName = '1'
			},
			saveDiagram() {
				this.bpmnModeler.saveXML({
					format: true
				}, function (err, xml) {
					if (!err) {
						FileSaver.saveAs(new Blob([xml], {
							type: 'application/octet-stream'
						}), 'diagram.bpmn')
					}
				})
			},
			saveSVG() {
				this.bpmnModeler.saveSVG({
					format: true
				}, function (err, svg) {
					if (!err) {
						FileSaver.saveAs(new Blob([svg], {
							type: 'application/octet-stream'
						}), 'diagram.svg')
					}
				})
			},
			handleTabClick(obj) {
				if (obj.name === '2') {
					const _this = this
					this.bpmnModeler.saveXML({
						format: true
					}, function (err, xml) {
						if (!err) {
							_this.showXML = xml
						}
					})
				}
			},
			openFile() {
				this.$refs.file.dispatchEvent(new MouseEvent('click'))
			},
			showRealPath() {
				const selectedFile = this.$refs.file.files[0]
				// 后缀获取
				let suffix = ''
				try {
					const fileArr = selectedFile.name.split('.')
					suffix = fileArr[fileArr.length - 1]
				} catch (err) {
					suffix = ''
				}
				if (suffix === '' || (suffix !== 'xml' && suffix !== 'bpmn')) {
					alert('不是有效的流程文件！')
					return
				}
				// FileReader对象，h5提供的异步api，可以读取文件中的数据。
				const reader = new FileReader()
				// readAsText是个异步操作，只有等到onload时才能显示数据。
				reader.readAsText(selectedFile)
				const _this = this
				reader.onload = function () {
					// 当读取完成后回调这个函数,然后此时文件的内容存储到了result中,直接操作即可
					_this.openDiagram(this.result)
				}
				// 防止选择同一个文件不执行此方法
				this.$refs.file.value = null
			},
			undo() {
				this.bpmnModeler.get('commandStack').undo()
			},
			redo() {
				this.bpmnModeler.get('commandStack').redo()
			},
			doSubmit() {
				let xmlData
				this.bpmnModeler.saveXML(function(err, xml) {
					if (!err) {
							xmlData = xml
					}
				})
				// if (xmlData) {
				// 	const fileData = new File([xmlData], 'diagram.bpmn', { type: 'text/xml' })
				// 	const param = new FormData() // 创建form对象
				// 	param.append('file', fileData) // 通过append向form对象添加数据
				// 	param.append('chunk', '0') // 添加form表单中其他数据
				// 	console.log(param.get('file')) // FormData私有类对象，访问不到，可以通过get判断值是否传进去
				// 	const config = {
				// 			headers: {
				// 					'Content-Type': 'multipart/form-data',
				// 					'Authorization': 'Bearer ' + getToken()
				// 			}
				// 	}
				// 	// 添加请求头
				// 	axios.post(this.baseUrl + '/api/import-process-model', param, config)
				// 			.then(response => {
				// 					if (response.status === 200) {
				// 							this.dialog = false
				// 							this.$parent.init()
				// 					}
				// 			})
				// }
			}
		}
	}
</script>

<style lang="scss">
@import url('../styles/index.scss');
	/*左边工具栏以及编辑节点的样式*/

	.el-tabs__content {
		padding: 0 0 15px 0 !important;
	}

	.bjs-powered-by {
		display: none;
	}

	.canvas {
		overflow: hidden;
		height: 100%;
	}

	.wrap {
		height: calc(100vh - 140px);
		margin-bottom: 10px;
	}

	.CodeMirror {
		height: calc(100vh - 140px);
	}

	.aside {
		width: 400px;
		background: #f8f8f8;
		height: 100%;
		border-left: 1px solid #e0e0e0;
	}
</style>