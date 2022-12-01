package com.llamalabb.phillygarbageday.data.local.service

import com.llamalabb.phillygarbageday.data.local.dao.HeadlineDAO
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class AddressRealmService(private val realm: Realm) : IAddressRealmService {
    override suspend fun addToReadLater(headlineDAO: HeadlineDAO) {
        realm.query<HeadlineDAO>("title = $0", headlineDAO.title).find().ifEmpty {
            realm.writeBlocking {
                this.copyToRealm(headlineDAO)
            }
        }
    }

    override suspend fun getReadLater() =realm.query<HeadlineDAO>().find()

}