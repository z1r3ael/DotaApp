package com.example.week8dotafragmentsappwb.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CharacterItem(

    @Json(name = "img")
    var characterIcon: String,

    @Json(name = "localized_name")
    val characterName: String,

    @Json(name = "primary_attr")
    val primaryAttribute: String,

    @Json(name = "attack_type")
    val attackType: String,

    @Json(name = "roles")
    val roles: Array<String>,

    @Json(name = "base_health")
    val baseHealth: String?,

    @Json(name = "base_health_regen")
    val baseHealthRegen: String,

    @Json(name = "base_mana")
    val baseMana: String,

    @Json(name = "base_mana_regen")
    val baseManaRegen: String,

    @Json(name = "base_armor")
    val baseArmor: String,

    @Json(name = "base_mr")
    val baseManaResist: String,

    @Json(name = "base_attack_min")
    val baseAttackMin: String,

    @Json(name = "base_attack_max")
    val baseAttackMax: String,

    @Json(name = "base_str")
    val baseStrength: String,

    @Json(name = "base_agi")
    val baseAgility: String,

    @Json(name = "base_int")
    val baseIntellect: String,
) : Parcelable {
    fun setCharacterIcon(baseUrl: String, currentVal: String) {
        characterIcon = baseUrl + currentVal
    }
}