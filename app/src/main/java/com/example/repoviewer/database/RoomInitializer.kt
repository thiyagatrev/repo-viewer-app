package com.example.repoviewer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.repoviewer.database.dao.RepoCommentDao
import com.example.repoviewer.database.model.RepoCommentModel

@Database(entities = [RepoCommentModel::class], version = 1, exportSchema = false)
abstract class RoomInitializer : RoomDatabase() {
    abstract fun repoCommentDao(): RepoCommentDao

    init {

    }

    companion object {

        /**
         * This is just for singleton pattern
         */
        private var INSTANCE: RoomInitializer? = null

        internal fun initDatabase(context: Context, name: String): RoomInitializer {
            if (INSTANCE == null) {
                synchronized(RoomInitializer::class.java) {
                    if (INSTANCE == null) {
                        // Get PhraseRoomDatabase database instance
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            RoomInitializer::class.java,
                            name
                        )
                            .allowMainThreadQueries()
                            .addMigrations()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }

        fun getInstance(): RoomInitializer {
            return INSTANCE!!
        }


    }
}
