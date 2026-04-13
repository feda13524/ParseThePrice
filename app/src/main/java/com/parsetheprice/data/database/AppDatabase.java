package com.parsetheprice.data.database;

import android.content.Context;
import androidx.room.Room;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.parsetheprice.data.dao.*;
import com.parsetheprice.data.entity.*;
import static com.parsetheprice.utils.Constants.DATABASE_NAME;
import static com.parsetheprice.utils.Constants.DATABASE_VERSION;

@Database(
        entities = {ParseTask.class, PriceTask.class},
        version = DATABASE_VERSION,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase{
    public abstract ParseTaskDao parseTaskDao();
    public abstract PriceTaskDao priceTaskDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
