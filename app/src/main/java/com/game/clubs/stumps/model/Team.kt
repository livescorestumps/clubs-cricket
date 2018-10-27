package com.game.clubs.stumps.model

import com.google.firebase.firestore.FieldValue

open class Team {
    var name: String? = null
    var shortName: String? = null
    var admin: String? = null
    var players: ArrayList<String> ? = null
    var joinRequests : List<String> ? = null
    //var createdDate: FieldValue? = null
}