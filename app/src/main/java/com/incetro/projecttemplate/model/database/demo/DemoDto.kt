/*
 * Ruvpro
 *
 * Created by artembirmin on 21/6/2022.
 */

package com.incetro.projecttemplate.model.database.demo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = DemoDto.TABLE_NAME
)
data class DemoDto(
    @PrimaryKey
    val id: String,
    val name: String,
) {
    companion object {
        const val TABLE_NAME = "demo"
    }
}
