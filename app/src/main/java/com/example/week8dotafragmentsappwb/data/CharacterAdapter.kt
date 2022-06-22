package com.example.week8dotafragmentsappwb.data

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.week8dotafragmentsappwb.MainActivity
import com.example.week8dotafragmentsappwb.R
import com.example.week8dotafragmentsappwb.fragments.CharacterInfoFragment
import com.example.week8dotafragmentsappwb.model.CharacterItem

class CharacterAdapter(
    private val context: Context,
    private val characterItems: ArrayList<CharacterItem>
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {


    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterIconImageView: ImageView = itemView.findViewById(R.id.characterIconImageView)
        val characterNameTextView: TextView = itemView.findViewById(R.id.characterNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val characterItem: CharacterItem = characterItems[position]
        holder.characterIconImageView.load(characterItem.characterIcon)
        holder.characterNameTextView.text = characterItem.characterName

        holder.itemView.setOnClickListener {
            val characterInfoBundle = Bundle().apply {
                putString(Utils.CHARACTER_ICON_KEY, characterItem.characterIcon)
                putString(Utils.CHARACTER_NAME_KEY, characterItem.characterName)
                putString(Utils.PRIMARY_ATTRIBUTE_KEY, characterItem.primaryAttribute)
                putString(Utils.ATTACK_TYPE_KEY, characterItem.attackType)
                putStringArray(Utils.ROLES_KEY, characterItem.roles)
                putString(Utils.BASE_HEALTH_KEY, characterItem.baseHealth)
                putString(Utils.BASE_HEALTH_REGEN_KEY, characterItem.baseHealthRegen)
                putString(Utils.BASE_MANA_KEY, characterItem.baseMana)
                putString(Utils.BASE_MANA_REGEN_KEY, characterItem.baseManaRegen)
                putString(Utils.BASE_ARMOR_KEY, characterItem.baseArmor)
                putString(Utils.BASE_MANA_RESIST_KEY, characterItem.baseManaResist)
                putString(Utils.BASE_ATTACK_MIN_KEY, characterItem.baseAttackMin)
                putString(Utils.BASE_ATTACK_MAX_KEY, characterItem.baseAttackMax)
                putString(Utils.BASE_STRENGTH_KEY, characterItem.baseStrength)
                putString(Utils.BASE_AGILITY_KEY, characterItem.baseAgility)
                putString(Utils.BASE_INTELLECT_KEY, characterItem.baseIntellect)
            }
            val characterInfoFragment = CharacterInfoFragment.newInstance(
                characterInfoBundle = characterInfoBundle
            )
            val activity = it.context as MainActivity
            activity.supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, characterInfoFragment)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return characterItems.size
    }
}