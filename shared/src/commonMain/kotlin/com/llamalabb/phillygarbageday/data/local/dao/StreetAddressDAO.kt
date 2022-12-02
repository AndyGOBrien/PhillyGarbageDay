package com.llamalabb.phillygarbageday.data.local.dao

import io.realm.kotlin.types.RealmObject


open class StreetAddressDAO : RealmObject {
    var streetAddress: String = ""
}