'use strict'

var cmdHelper = require('./CmdHelper'),
  elementHelper = require('./ElementHelper')

var is = require('bpmn-js/lib/util/ModelUtil').is

var ButtonsHelper = {}

var getButtons = function(bo) {
  return bo.get('buttons')
}

ButtonsHelper.getButtons = function(bo, type) {
  var buttons = getButtons(bo)
  if (typeof buttons !== 'undefined') {
    var extensionValues = buttons.get('values')
    if (typeof extensionValues !== 'undefined') {
      var elements = extensionValues.filter(function(value) {
        return is(value, type)
      })
      if (elements.length) {
        return elements
      }
    }
  }
}

ButtonsHelper.addEntry = function(bo, element, entry, bpmnFactory) {
  var buttons = bo.get('buttons')

  // if there is no buttons list, create one
  if (!buttons) {
    // TODO: Ask Daniel which operation costs more
    buttons = elementHelper.createElement('bpmn:UserTask', { values: [entry] }, bo, bpmnFactory)
    return { buttons: buttons }
  } else {
    // add new failedJobRetryExtensionElement to existing buttons list
    return cmdHelper.addElementsTolist(element, buttons, 'values', [entry])
  }
}

ButtonsHelper.removeEntry = function(bo, element, entry) {
  var buttons = bo.get('buttons')

  if (!buttons) {
    // return an empty command when there is no buttons list
    return {}
  }

  return cmdHelper.removeElementsFromList(element, buttons, 'values', 'buttons', [entry])
}

module.exports = ButtonsHelper
