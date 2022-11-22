package com.llamalabb.phillygarbageday.data.local.dao

import com.llamalabb.phillygarbageday.domain.domain_model.HeadlineDomainModel
import io.realm.kotlin.types.RealmObject


open class HeadlineDAO(

) : RealmObject {


    var author: String = ""

    var content: String = ""

    var description: String = ""

    var publishedAt: String = ""

    var source: String = ""

    var title: String = ""

    var url: String = ""

    var urlToImage: String = ""



}


fun HeadlineDAO.asDomainModel() = HeadlineDomainModel(
    author, content, description, publishedAt, source, title, url, urlToImage
)

fun List<HeadlineDAO>.asDomainModel() = map {
    it.asDomainModel()
}
