/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.renteco_v1

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import com.example.renteco_v1.AppContainer
import com.example.renteco_v1.api.Api


class MyApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        val context=this
        Log.d(TAG, "init")
        Api.loadKeyStore(context)
        Api.createRetrofit()
        container = AppContainer(this)
    }
}
