/*
 * Copyright (c) 2021, Alibaba Group Holding Limited;
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.gaiax.render.node

import app.visly.stretch.*
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.alibaba.gaiax.context.GXTemplateContext
import com.alibaba.gaiax.render.node.text.GXFitContentUtils
import com.alibaba.gaiax.template.GXFlexBox
import com.alibaba.gaiax.template.GXStyle
import com.alibaba.gaiax.template.utils.GXTemplateUtils

/**
 * @suppress
 */
data class GXStretchNode(val node: Node, var layout: Layout? = null) {

    var finalLayout: Layout? = null

    fun reset() {
        finalLayout = null
    }

    fun initFinal() {
        layout?.let {
            finalLayout = Layout(it.x, it.y, it.width, it.height, it.children, it.id, it.idPath)
        }
    }

    fun updateContainerStyle(context: GXTemplateContext, gxTemplateNode: GXTemplateNode, gxNode: GXNode, templateData: JSONObject): Boolean {

        val containerDataBinding = gxNode.templateNode.dataBinding
        //  对于容器嵌套模板，传递给下一层的数据只能是JSONArray
        val containerTemplateData = (containerDataBinding?.getValueData(templateData) as? JSONArray) ?: JSONArray()

        val style = this.node.getStyle()

        var result = false
        val finalCss = gxNode.templateNode.finalCss
        val finalScrollConfig = gxNode.templateNode.finalScrollConfig
        val finalGridConfig = gxNode.templateNode.finalGridConfig
        val finalFlexBox = finalCss?.flexBox
        val finalCssStyle = finalCss?.style

        var isDirty = false

        val height = finalFlexBox?.size?.height
        val flexGrow = finalFlexBox?.flexGrow

        // 当容器节点不是flexGrow时，且容器节点的高度设置，或者是默认，或者是未定义，需要主动计算高度
        if (gxNode.isScrollType() && finalScrollConfig?.isHorizontal == true && flexGrow == null && (height == null || height == Dimension.Auto || height == Dimension.Undefined)) {
            val containerSize = GXNodeUtils.computeContainerHeightByItemTemplate(context, gxNode, containerTemplateData)
            containerSize?.height?.let {
                finalFlexBox?.size?.height = it
                isDirty = true
            }
        } else if (gxNode.isGridType() && flexGrow == null && (height == null || height == Dimension.Auto || height == Dimension.Undefined)) {
            val containerSize = GXNodeUtils.computeContainerHeightByItemTemplate(context, gxNode, containerTemplateData)
            containerSize?.height?.let {
                finalFlexBox?.size?.height = it
                isDirty = true
            }
        }

        if (finalFlexBox != null) {
            updateStyleByFlexBox(finalFlexBox, style)?.let {
                isDirty = it
            }
        }

        if (finalCssStyle != null) {
            updateStyleByCssStyle(context, finalCssStyle, style, gxTemplateNode, this, templateData)?.let {
                isDirty = it
            }
        }

        if (isDirty) {
            style.free()
            style.init()
            this.node.setStyle(style)
            this.node.markDirty()
            result = true
        }

        return result
    }

    fun updateNormalStyle(context: GXTemplateContext, node: GXTemplateNode, templateData: JSONObject): Boolean {

        val style = this.node.getStyle()

        var result = false
        var isDirty = false

        val finalCss = node.finalCss
        val finalFlexBox = finalCss?.flexBox
        val finalCssStyle = finalCss?.style

        if (finalFlexBox != null) {
            updateStyleByFlexBox(finalFlexBox, style)?.let {
                isDirty = it
            }
        }

        if (finalCssStyle != null) {
            updateStyleByCssStyle(context, finalCssStyle, style, node, this, templateData)?.let {
                isDirty = it
            }
        }

        if (isDirty) {
            style.free()
            style.init()
            this.node.setStyle(style)
            this.node.markDirty()
            result = true
        }

        return result
    }

    private fun updateStyleByFlexBox(flexBox: GXFlexBox, style: Style): Boolean? {
        var isDirty: Boolean? = null
        flexBox.display?.let {
            style.display = it
            isDirty = true
        }

        flexBox.aspectRatio?.let {
            style.aspectRatio = it
            isDirty = true
        }

        flexBox.direction?.let {
            style.direction = it
            isDirty = true
        }

        flexBox.flexDirection?.let {
            style.flexDirection = it
            isDirty = true
        }

        flexBox.flexWrap?.let {
            style.flexWrap = it
            isDirty = true
        }

        flexBox.overflow?.let {
            style.overflow = it
            isDirty = true
        }

        flexBox.alignItems?.let {
            style.alignItems = it
            isDirty = true
        }

        flexBox.alignSelf?.let {
            style.alignSelf = it
            isDirty = true
        }

        flexBox.alignContent?.let {
            style.alignContent = it
            isDirty = true
        }

        flexBox.justifyContent?.let {
            style.justifyContent = it
            isDirty = true
        }

        flexBox.positionType?.let {
            style.positionType = it
            isDirty = true
        }

        flexBox.position?.let {
            GXTemplateUtils.updateDimension(it, style.position)
            isDirty = true
        }

        flexBox.margin?.let {
            GXTemplateUtils.updateDimension(it, style.margin)
            isDirty = true
        }

        flexBox.padding?.let {
            GXTemplateUtils.updateDimension(it, style.padding)
            isDirty = true
        }

        flexBox.border?.let {
            GXTemplateUtils.updateDimension(it, style.border)
            isDirty = true
        }

        flexBox.flexGrow?.let {
            style.flexGrow = it
            isDirty = true
        }

        flexBox.flexShrink?.let {
            style.flexShrink = it
            isDirty = true
        }

        flexBox.size?.let {
            GXTemplateUtils.updateSize(it, style.size)
            isDirty = true
        }

        flexBox.minSize?.let {
            GXTemplateUtils.updateSize(it, style.minSize)
            isDirty = true
        }

        flexBox.maxSize?.let {
            GXTemplateUtils.updateSize(it, style.maxSize)
            isDirty = true
        }

        return isDirty
    }

    private fun updateStyleByCssStyle(templateContext: GXTemplateContext, finalCssStyle: GXStyle, stretchStyle: Style, currentNode: GXTemplateNode, currentStretchNode: GXStretchNode, data: JSONObject): Boolean? {
        if (finalCssStyle.fitContent == true) {
            GXFitContentUtils.fitContent(templateContext, currentNode, currentStretchNode, data)?.let {
                GXTemplateUtils.updateSize(it, stretchStyle.size)
                // 使用FlexGrow结合FitContent计算出来的宽度，在赋值的是偶，需要将flexGrow重置成0，否则在Stretch计算的时候会使用FlexGrow计算出的宽度
                if (stretchStyle.flexGrow != 0F) {
                    stretchStyle.flexGrow = 0F
                }
                return true
            }
        }
        return null
    }

    override fun toString(): String {
        return "GXStretchNode(node=$node, layout=$layout)"
    }

    fun free() {
        layout = null
        finalLayout = null
        node.free()
    }

    companion object {

        fun createNode(currentNode: GXTemplateNode, id: String, idPath: String): GXStretchNode {
            val stretchStyle = createStretchStyle(currentNode)
            val stretchNode = Node(id, idPath, stretchStyle, mutableListOf())
            return GXStretchNode(stretchNode, null)
        }

        private fun createStretchStyle(currentNode: GXTemplateNode): Style {
            val builder = Style.Builder()

            // Set Self FlexBox Property
            val flexBox = currentNode.css.flexBox
            setBuilder(flexBox, builder)

            // Override Property From Parent FlexBox
            val visualTemplateNodeFlexBox = currentNode.visualTemplateNode?.css?.flexBox
            visualTemplateNodeFlexBox?.let { setBuilder(it, builder) }

            return builder.build()
        }

        private fun setBuilder(flexBox: GXFlexBox, builder: Style.Builder) {
            flexBox.display?.let { builder.display = it }

            flexBox.aspectRatio?.let { builder.aspectRatio = it }

            flexBox.direction?.let { builder.direction = it }

            flexBox.flexDirection?.let { builder.flexDirection = it }

            flexBox.flexWrap?.let { builder.flexWrap = it }

            flexBox.overflow?.let { builder.overflow = it }

            flexBox.alignItems?.let { builder.alignItems = it }

            flexBox.alignSelf?.let { builder.alignSelf = it }

            flexBox.alignContent?.let { builder.alignContent = it }

            flexBox.justifyContent?.let { builder.justifyContent = it }

            flexBox.positionType?.let { builder.positionType = it }

            flexBox.position?.let { builder.position = it }

            flexBox.margin?.let { builder.margin = it }

            flexBox.padding?.let { builder.padding = it }

            flexBox.border?.let { builder.border = it }

            flexBox.flexGrow?.let { builder.flexGrow = it }

            flexBox.flexShrink?.let { builder.flexShrink = it }

            flexBox.size?.let { builder.size = Size(it.width, it.height) }

            flexBox.minSize?.let { builder.minSize = Size(it.width, it.height) }

            flexBox.maxSize?.let { builder.maxSize = Size(it.width, it.height) }
        }
    }
}