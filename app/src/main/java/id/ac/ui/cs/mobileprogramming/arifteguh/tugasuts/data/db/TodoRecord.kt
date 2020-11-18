package id.ac.ui.cs.mobileprogramming.arifteguh.tugasuts.data.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * @author Naveen T P
 * @since 08/11/18
 */
@Entity(tableName = "todo")
@Parcelize()
data class TodoRecord(@PrimaryKey(autoGenerate = true) val id: Long?,
                      @ColumnInfo(name = "title") val title: String,
                      @ColumnInfo(name = "dd") val dd: String,
                      @ColumnInfo(name = "mm") val mm: String,
                      @ColumnInfo(name = "yyyy") val yyyy : String,
                      @ColumnInfo(name = "hour") val hour: String,
                      @ColumnInfo(name = "minute") val minute: String,
                      @ColumnInfo(name = "deskripsi") val deskripsi: String) : Parcelable
