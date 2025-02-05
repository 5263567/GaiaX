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

package com.alibaba.gaiax.template.utils

import app.visly.stretch.Dimension
import app.visly.stretch.Rect
import app.visly.stretch.Size
import com.alibaba.gaiax.template.GXSize

/**
 * @suppress
 */
object GXTemplateUtils {

    fun updateDimension(it: Rect<Dimension>, targetDimension: Rect<Dimension>) {
        if (it.start !is Dimension.Undefined) {
            targetDimension.start = it.start
        }
        if (it.end !is Dimension.Undefined) {
            targetDimension.end = it.end
        }
        if (it.top !is Dimension.Undefined) {
            targetDimension.top = it.top
        }
        if (it.bottom !is Dimension.Undefined) {
            targetDimension.bottom = it.bottom
        }
    }

    fun updateSize(it: Size<Dimension>, targetDimension: Size<Dimension>) {
        if (it.width !is Dimension.Undefined) {
            targetDimension.width = it.width
        }
        if (it.height !is Dimension.Undefined) {
            targetDimension.height = it.height
        }
    }

    fun createRectGXSizeByPriority(heightPriority: Rect<GXSize>?, lowPriority: Rect<GXSize>?): Rect<GXSize>? {
        return if (lowPriority != null && heightPriority != null) {
            Rect(
                start = if (heightPriority.start !is GXSize.Undefined && heightPriority.start !is GXSize.Auto) heightPriority.start else lowPriority.start,
                end = if (heightPriority.end !is GXSize.Undefined && heightPriority.end !is GXSize.Auto) heightPriority.end else lowPriority.end,
                top = if (heightPriority.top !is GXSize.Undefined && heightPriority.top !is GXSize.Auto) heightPriority.top else lowPriority.top,
                bottom = if (heightPriority.bottom !is GXSize.Undefined && heightPriority.bottom !is GXSize.Auto) heightPriority.bottom else lowPriority.bottom,
            )
        } else if (heightPriority == null) {
            lowPriority
        } else if (lowPriority == null) {
            heightPriority
        } else {
            null
        }
    }

    fun createRectDimensionByPriority(heightPriority: Rect<Dimension>?, lowPriority: Rect<Dimension>?): Rect<Dimension>? {
        return if (lowPriority != null && heightPriority != null) {
            Rect(
                start = if (heightPriority.start !is Dimension.Undefined && heightPriority.start !is Dimension.Auto) heightPriority.start else lowPriority.start,
                end = if (heightPriority.end !is Dimension.Undefined && heightPriority.end !is Dimension.Auto) heightPriority.end else lowPriority.end,
                top = if (heightPriority.top !is Dimension.Undefined && heightPriority.top !is Dimension.Auto) heightPriority.top else lowPriority.top,
                bottom = if (heightPriority.bottom !is Dimension.Undefined && heightPriority.bottom !is Dimension.Auto) heightPriority.bottom else lowPriority.bottom,
            )
        } else if (heightPriority == null) {
            lowPriority
        } else if (lowPriority == null) {
            heightPriority
        } else {
            null
        }
    }

    fun createSizeDimensionByPriority(heightPriority: Size<Dimension>?, lowPriority: Size<Dimension>?): Size<Dimension>? {
        return if (lowPriority != null && heightPriority != null) {
            Size(
                width = if (heightPriority.width !is Dimension.Undefined && heightPriority.width !is Dimension.Auto) heightPriority.width else lowPriority.width,
                height = if (heightPriority.height !is Dimension.Undefined && heightPriority.height !is Dimension.Auto) heightPriority.height else lowPriority.height,
            )
        } else if (heightPriority == null) {
            lowPriority
        } else if (lowPriority == null) {
            heightPriority
        } else {
            null
        }
    }
}