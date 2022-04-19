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

package com.alibaba.gaiax.context

import android.content.Context
import android.view.View
import com.alibaba.gaiax.GXTemplateEngine
import com.alibaba.gaiax.render.node.GXNode
import com.alibaba.gaiax.render.node.GXTemplateNode
import com.alibaba.gaiax.render.view.GXIRootView
import com.alibaba.gaiax.template.GXTemplateInfo
import java.lang.ref.SoftReference

/**
 * @suppress
 */
class GXTemplateContext(val context: Context) {

    /**
     * Is dirty
     */
    var isDirty: Boolean = false

    /**
     * ViewPort size
     */
    lateinit var size: GXTemplateEngine.GXMeasureSize

    /**
     * Template information
     */
    lateinit var templateItem: GXTemplateEngine.GXTemplateItem

    /**
     * Raw data of the template associated with the current view: root template, nested template, and child template
     */
    lateinit var templateInfo: GXTemplateInfo

    /**
     * A soft or weak reference to a view
     */
    var rootView: SoftReference<View>? = null

    /**
     * View Information about the virtual node tree associated with the template
     */
    var rootNode: GXNode? = null


    var templateData: GXTemplateEngine.GXTemplateData? = null

    /**
     * Container-indexed position
     */
    var indexPosition: Int = -1

    /**
     * A virtual node for nested templates
     */
    var visualTemplateNode: GXTemplateNode? = null

    override fun toString(): String {
        return "GXTemplateContext(context=$context, isDirty=$isDirty, size=$size, templateItem='$templateItem' rootView=$rootView)"
    }

    fun release() {
        templateData = null
        rootView?.clear()
        rootView = null
        visualTemplateNode = null
        rootNode?.release()
        rootNode = null
    }

    companion object {

        fun createContext(templateItem: GXTemplateEngine.GXTemplateItem, size: GXTemplateEngine.GXMeasureSize, templateInfo: GXTemplateInfo, visualTemplateNode: GXTemplateNode? = null): GXTemplateContext {
            val context = GXTemplateContext(templateItem.context)
            context.size = size
            context.templateItem = templateItem
            context.templateInfo = templateInfo
            context.visualTemplateNode = visualTemplateNode
            return context
        }

        fun getContext(targetView: View): GXTemplateContext? {
            if (targetView is GXIRootView) {
                return targetView.getTemplateContext()
            }
            return null
        }
    }
}