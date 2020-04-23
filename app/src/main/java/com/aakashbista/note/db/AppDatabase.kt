package com.aakashbista.note.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aakashbista.note.converter.LocalDateTimeConverter

@Database(
    entities = [Note::class,Reminder::class],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
    abstract fun getReminderDao(): ReminderDao

    companion object {
        const val DATABASE_NAME="myDatabase"
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }


}