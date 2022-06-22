package com.example.week8dotafragmentsappwb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.week8dotafragmentsappwb.R
import com.example.week8dotafragmentsappwb.data.Utils
import com.example.week8dotafragmentsappwb.databinding.FragmentCharacterInfoBinding
import com.example.week8dotafragmentsappwb.fragments.contract.HasCustomTitle
import com.example.week8dotafragmentsappwb.model.CharacterItem
import java.util.*

class CharacterInfoFragment : Fragment(), HasCustomTitle {

    private lateinit var binding: FragmentCharacterInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterInfoBinding.inflate(inflater, container, false)

        setCharactersInfo()

        return binding.root
    }

    override fun getTitleRes(): Int = R.string.character_info_title

    private fun getCharacterInfoBundle(): Bundle? =
        requireArguments().getBundle(Utils.CHARACTERS_INFO_KEY)

    private fun setCharactersInfo() {
        val bundle = getCharacterInfoBundle()
        if (bundle != null) {
            binding.characterIconImageView.load(bundle.getString(Utils.CHARACTER_ICON_KEY))
            binding.characterNameTextView.text = bundle.getString(Utils.CHARACTER_NAME_KEY)
            binding.setStrengthTextView.text = bundle.getString(Utils.BASE_STRENGTH_KEY)
            binding.setAgilityTextView.text = bundle.getString(Utils.BASE_AGILITY_KEY)
            binding.setIntellectTextView.text = bundle.getString(Utils.BASE_INTELLECT_KEY)
            binding.setPrimaryAttribute.text = bundle.getString(Utils.PRIMARY_ATTRIBUTE_KEY)
            binding.setAttackType.text = bundle.getString(Utils.ATTACK_TYPE_KEY)
            binding.setRoles.text = Arrays.toString(bundle.getStringArray(Utils.ROLES_KEY))
            binding.setBaseHealth.text = bundle.getString(Utils.BASE_HEALTH_KEY)
            binding.setBaseHealthRegen.text = bundle.getString(Utils.BASE_HEALTH_REGEN_KEY)
            binding.setBaseMana.text = bundle.getString(Utils.BASE_MANA_KEY)
            binding.setBaseManaRegen.text = bundle.getString(Utils.BASE_MANA_REGEN_KEY)
            binding.setBaseArmor.text = bundle.getString(Utils.BASE_ARMOR_KEY)
            binding.setBaseManaResist.text = bundle.getString(Utils.BASE_MANA_RESIST_KEY)
            binding.setBaseAttackMin.text = bundle.getString(Utils.BASE_ATTACK_MIN_KEY)
            binding.setBaseAttackMax.text = bundle.getString(Utils.BASE_ATTACK_MAX_KEY)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(characterInfoBundle: Bundle): CharacterInfoFragment {
            val args = Bundle().apply {
                putBundle(Utils.CHARACTERS_INFO_KEY, characterInfoBundle)
            }
            val fragment = CharacterInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}