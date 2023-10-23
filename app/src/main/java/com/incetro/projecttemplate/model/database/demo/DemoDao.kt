/*
 * Ruvpro
 *
 * Created by artembirmin on 21/6/2022.
 */

package com.incetro.projecttemplate.model.database.demo

import androidx.room.Dao
import com.incetro.projecttemplate.model.database.BaseDao

@Dao
interface DemoDao : BaseDao<DemoDto> {

}