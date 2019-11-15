package com.arildojr.data.songs.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class Song(
    @PrimaryKey
    var id: Int? = null,
    var artistId: Int? = null,
    var artistName: String? = null,
    var trackName: String? = null,
    var collectionName: String? = null,
    var artworkUrl: String? = null,
    var wrapperType: String? = null,
    var trackExplicitness: String? = null,
    var trackCensoredName: String? = null,
    var collectionId: Int? = null,
    var country: String? = null,
    var primaryGenreName: String? = null,
    var releaseDate: String? = null,
    var trackTimeMillis: Int? = null
)