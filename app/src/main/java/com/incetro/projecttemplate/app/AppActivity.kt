/*
 * ProjectTemplate
 *
 * Created by artembirmin on 3/11/2022.
 */

package com.incetro.projecttemplate.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.incetro.projecttemplate.R

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_container)
    }
}