package com.embassylegacy.weatherapp.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.embassylegacy.weatherapp.model.CurrentWeatherResponse

object DatabaseMigrations {
    const val DB_VERSION = 32

    val MIGRATIONS: Array<Migration>
        get() = arrayOf<Migration>(
            migration12()
        )

    private fun migration12(): Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE ${CurrentWeatherResponse.TABLE_NAME} ADD COLUMN body TEXT")

        }
    }
}
