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

package com.alibaba.gaiax.template

import android.graphics.Color
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GXColorTest {


    @Test
    fun parseColor_RGB() {
        Assert.assertEquals(Color.rgb(255, 0, 0), GXColor.parseColor("rgb(255,0,0)"))
        Assert.assertEquals(null, GXColor.parseColor("rbg(255,0,0)"))
        Assert.assertEquals(null, GXColor.parseColor(""))
    }

    @Test
    fun parseColor_RGBA() {
        Assert.assertEquals(Color.argb((255 * 0.3).toInt(), 255, 0, 0), GXColor.parseColor("rgba(255,0,0,0.3)"))
        Assert.assertEquals(null, GXColor.parseColor(""))
    }

    @Test
    fun parseColor_HEX() {
        Assert.assertEquals(Color.parseColor("#00FF00"), GXColor.parseColor("#00FF00"))
        Assert.assertEquals(null, GXColor.parseColor("FF00FF"))
        Assert.assertEquals(null, GXColor.parseColor(""))
        Assert.assertEquals(Color.parseColor("#4C000000"), GXColor.parseColor("#0000004C"))
    }

    @Test
    fun parseColor_SIMPLE() {
        Assert.assertEquals(Color.RED, GXColor.parseColor("RED"))
        Assert.assertEquals(Color.RED, GXColor.parseColor("red"))
    }

}