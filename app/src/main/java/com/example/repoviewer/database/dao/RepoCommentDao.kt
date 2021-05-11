package com.example.repoviewer.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.repoviewer.database.model.RepoCommentModel

@Dao
interface RepoCommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repoCommentModel: RepoCommentModel)

    @Insert
    fun insertList(repoCommentModel: List<RepoCommentModel>)

    @Update
    fun update(repoCommentModel: RepoCommentModel)

    @Query("SELECT * FROM RepoComment ORDER BY timeStamp DESC")
    fun getAll(): List<RepoCommentModel>

    @Query("SELECT * FROM RepoComment WHERE NODEID=:nodeId ORDER BY timeStamp DESC")
    fun get(nodeId: String): LiveData<List<RepoCommentModel>?>

    @Query("DELETE FROM RepoComment WHERE id = :id")
    fun delete(id: Int)


}