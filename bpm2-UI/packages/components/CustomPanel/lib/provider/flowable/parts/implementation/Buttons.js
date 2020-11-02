'use strict'

var getBusinessObject = require('bpmn-js/lib/util/ModelUtil').getBusinessObject,
  is = require('bpmn-js/lib/util/ModelUtil').is

var factory = require('../../../../factory/EntryFactory')

var elementHelper = require('../../../../helper/ElementHelper'),
  extensionElementsHelper = require('../../../../helper/ExtensionElementsHelper'),
  cmdHelper = require('../../../../helper/CmdHelper'),
  utils = require('../../../../Utils')

var assign = require('lodash/assign'),
  forEach = require('lodash/forEach'),
  find = require('lodash/find')

function generateButtonId() {
  return utils.nextId('Button_')
}

/**
 * Get all flowable:button objects for a specific business object
 *
 * @param  {ModdleElement} parent
 *
 * @return {Array<ModdleElement>} a list of flowable:button objects
 */
function getButtonValues(parent) {
  var buttons = parent && getButtonsElement(parent)
  if (buttons && buttons.values) {
    return buttons.values
  }
  return []
}

/**
 * Get all flowable:Buttons object for a specific business object
 *
 * @param  {ModdleElement} parent
 *
 * @return {ModdleElement} a flowable:Buttons object
 */
function getButtonsElement(element) {
  if (!isExtensionElements(element)) {
    return element.buttons
  } else {
    return getButtonsElementInsideExtensionElements(element)
  }
}

/**
 * Get first flowable:Buttons object for a specific bpmn:ExtensionElements
 * business object.
 *
 * @param {ModdleElement} extensionElements
 *
 * @return {ModdleElement} a flowable:Buttons object
 */
function getButtonsElementInsideExtensionElements(extensionElements) {
  return find(extensionElements.values, function(elem) {
    return is(elem, 'flowable:Buttons')
  })
}

/**
 * Returns true, if the given business object is a bpmn:ExtensionElements.
 *
 * @param {ModdleElement} element
 *
 * @return {boolean} a boolean value
 */
function isExtensionElements(element) {
  return is(element, 'bpmn:ExtensionElements')
}

/**
 * Create a flowable:button entry using tableEntryFactory
 *
 * @param  {djs.model.Base} element
 * @param  {BpmnFactory} bpmnFactory
 * @param  {Object} options
 * @param  {string} options.id
 * @param  {Array<string>} options.modelProperties
 * @param  {Array<string>} options.labels
 * @param  {function} options.getParent Gets the parent business object
 * @param  {function} options.show Indicate when the entry will be shown, should return boolean
 */
module.exports = function(element, bpmnFactory, options, translate) {
  var getParent = options.getParent

  var modelProperties = options.modelProperties,
    createParent = options.createParent

  var bo = getBusinessObject(element)
  if (is(element, 'bpmn:Participant')) {
    bo = bo.get('processRef')
  }

  // build buttons group only when the participant have a processRef
  if (!bo) {
    return
  }

  assign(options, {
    addLabel: translate('Add Button'),
    getElements: function(element, node) {
      var parent = getParent(element, node, bo)
      return getButtonValues(parent)
    },
    addElement: function(element, node) {
      var commands = [],
        parent = getParent(element, node, bo)

      if (!parent && typeof createParent === 'function') {
        var result = createParent(element, bo)
        parent = result.parent
        commands.push(result.cmd)
      }

      var buttons = getButtonsElement(parent)
      if (!buttons) {
        buttons = elementHelper.createElement('flowable:Buttons', {}, parent, bpmnFactory)

        if (!isExtensionElements(parent)) {
          commands.push(cmdHelper.updateBusinessObject(element, parent, { 'buttons': buttons }))
        } else {
          commands.push(cmdHelper.addAndRemoveElementsFromList(
            element,
            parent,
            'values',
            'extensionElements',
            [buttons],
            []
          ))
        }
      }

      var buttonProps = {}
      forEach(modelProperties, function(prop) {
        buttonProps[prop] = undefined
      })

      // create id if necessary
      if (modelProperties.indexOf('id') >= 0) {
        buttonProps.id = generateButtonId()
      }

      var button = elementHelper.createElement('flowable:Button', buttonProps, buttons, bpmnFactory)
      commands.push(cmdHelper.addElementsTolist(element, buttons, 'values', [button]))

      return commands
    },
    updateElement: function(element, value, node, idx) {
      var parent = getParent(element, node, bo),
        button = getButtonValues(parent)[idx]

      forEach(modelProperties, function(prop) {
        value[prop] = value[prop] || undefined
      })

      return cmdHelper.updateBusinessObject(element, button, value)
    },
    validate: function(element, value, node, idx) {
      // validate id if necessary
      if (modelProperties.indexOf('id') >= 0) {
        var parent = getParent(element, node, bo),
          buttons = getButtonValues(parent),
          button = buttons[idx]

        if (button) {
          // check if id is valid
          var validationError = utils.isIdValid(button, value.id, translate)

          if (validationError) {
            return { id: validationError }
          }
        }
      }
    },
    removeElement: function(element, node, idx) {
      var commands = [],
        parent = getParent(element, node, bo),
        buttons = getButtonsElement(parent),
        buttonValues = getButtonValues(parent),
        currentButton = buttonValues[idx]

      commands.push(cmdHelper.removeElementsFromList(element, buttons, 'values', null, [currentButton]))

      if (buttonValues.length === 1) {
        // remove flowable:buttons if the last existing button has been removed
        if (!isExtensionElements(parent)) {
          commands.push(cmdHelper.updateBusinessObject(element, parent, { buttons: undefined }))
        } else {
          forEach(parent.values, function(value) {
            if (is(value, 'flowable:Buttons')) {
              commands.push(extensionElementsHelper.removeEntry(bo, element, value))
            }
          })
        }
      }

      return commands
    }
  })

  return factory.table(options)
}
