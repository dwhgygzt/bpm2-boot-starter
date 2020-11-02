'use strict'

var buttons = require('./implementation/Buttons'),
  elementHelper = require('../../../helper/ElementHelper'),
  cmdHelper = require('../../../helper/CmdHelper')

module.exports = function(group, element, bpmnFactory, translate) {
  var buttonsEntry = buttons(element, bpmnFactory, {
    id: 'buttons',
    modelProperties: ['name', 'code', 'next'],
    labels: [translate('Name'), translate('Code'), translate('Next')],

    getParent: function(element, node, bo) {
      return bo.extensionElements
    },

    createParent: function(element, bo) {
      var parent = elementHelper.createElement('bpmn:ExtensionElements', { values: [] }, bo, bpmnFactory)
      var cmd = cmdHelper.updateBusinessObject(element, bo, { extensionElements: parent })
      return {
        cmd: cmd,
        parent: parent
      }
    }
  }, translate)

  if (buttonsEntry) {
    group.entries.push(buttonsEntry)
  }
}
