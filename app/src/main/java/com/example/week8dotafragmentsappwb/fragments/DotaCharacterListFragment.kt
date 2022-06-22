package com.example.week8dotafragmentsappwb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week8dotafragmentsappwb.MainActivity
import com.example.week8dotafragmentsappwb.R
import com.example.week8dotafragmentsappwb.data.CharacterAdapter
import com.example.week8dotafragmentsappwb.data.Utils
import com.example.week8dotafragmentsappwb.databinding.FragmentDotaCharacterListBinding
import com.example.week8dotafragmentsappwb.fragments.contract.HasCustomTitle
import com.example.week8dotafragmentsappwb.model.CharacterItem

class DotaCharacterListFragment : Fragment(), HasCustomTitle {

    private lateinit var binding: FragmentDotaCharacterListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDotaCharacterListBinding.inflate(inflater, container, false)

        val layoutManager = GridLayoutManager(requireContext(), 3)
        val adapter = CharacterAdapter(requireContext() as MainActivity, getCharacterItemList())

        binding.characterRecyclerView.adapter = adapter
        binding.characterRecyclerView.layoutManager = layoutManager

        return binding.root
    }

    override fun getTitleRes(): Int = R.string.character_list_title

    private fun getCharacterItemList(): ArrayList<CharacterItem> =
        requireArguments().getParcelableArrayList(Utils.CHARACTERS_LIST_KEY)!!


    companion object {
        @JvmStatic
        fun newInstance(characterItems: ArrayList<CharacterItem>): DotaCharacterListFragment {
            val args = Bundle().apply {
                putParcelableArrayList(Utils.CHARACTERS_LIST_KEY, characterItems)
            }
            val fragment = DotaCharacterListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}