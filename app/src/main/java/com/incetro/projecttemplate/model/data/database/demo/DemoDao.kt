/*
 * ProjectTemplate
 *
 * Created by artembirmin on 15/11/2023.
 */

package com.incetro.projecttemplate.model.data.database.demo

import androidx.room.Dao
import com.incetro.projecttemplate.model.data.database.BaseDao

@Dao
interface DemoDao : BaseDao<DemoDto> {

}