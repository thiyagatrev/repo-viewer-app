package com.example.repoviewer.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RepoComment")
data class RepoCommentModel(
    @ColumnInfo(name = "NODEID") var nodeId: String = "",
    @ColumnInfo(name = "COMMENT") var comment: String = "",
    @ColumnInfo(name = "timestamp") var timestamp: Long = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
