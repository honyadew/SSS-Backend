package com.honya.database.photo

import org.jetbrains.exposed.sql.BinaryColumnType
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Photos : Table("photos") {
    private val photoId = Photos.varchar(name = "photo_id", length = 70)
    private val photoFile = Photos.registerColumn<ByteArray>(
        name = "photo_file",
        type = BinaryColumnType(500000)
    )

    fun insert(photoDTO: PhotoDTO){
        transaction {
            Photos.insert {
                it[photoId] = photoDTO.photoId
                it[photoFile] = photoDTO.photoFIle
            }
        }
    }

    fun getPhoto(photId: String) : PhotoDTO? {
        return transaction {
            val result = Photos.select(photoId.eq(photId)).singleOrNull()

            val photoDTO = result?.let {
                PhotoDTO(
                    photoId = it[photoId],
                    photoFIle = it[photoFile]
                )
            }

            photoDTO
        }
    }
}