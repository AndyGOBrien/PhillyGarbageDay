package com.llamalabb.phillygarbageday.data.local.service

import com.llamalabb.phillygarbageday.data.local.dao.StreetAddressDAO
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class AddressRealmService(private val realm: Realm) : IAddressRealmService {
    override suspend fun saveStreetAddress(address: String) {
        realm.query<StreetAddressDAO>().first().find()?.let {
            realm.write {
                findLatest(it)?.streetAddress = address
            }
        } ?: run {
            realm.write { copyToRealm(StreetAddressDAO().apply { streetAddress = address }) }
        }
    }

    override suspend fun getStreetAddress(): String? =
        realm.query<StreetAddressDAO>().first().find()?.streetAddress

}