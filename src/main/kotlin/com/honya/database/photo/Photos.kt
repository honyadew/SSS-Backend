package com.honya.database.photo

import org.jetbrains.exposed.sql.BinaryColumnType
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object Photos : Table("photos") {
    private val photoId = Photos.varchar(name = "photo_id", length = 70)
    private val photoFile = Photos.registerColumn<ByteArray>(
        name = "photo_file",
        type = BinaryColumnType(500000)
    )

    fun insert(pFile: ByteArray) : String{
        val pId = UUID.randomUUID().toString().substring(0, 20)
        transaction {
            Photos.insert {
                it[photoId] = pId
                it[photoFile] = pFile
            }
        }
        return pId
    }

    fun getPhoto(getPhotoId: String) : PhotoDTO? {
        return transaction {
            val result = Photos.select(photoId.eq(getPhotoId)).singleOrNull()

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