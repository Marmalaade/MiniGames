package com.example.android.navigation.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentGameBinding
import com.example.android.navigation.references.TableData

class GameFragment : Fragment() {

    private var satietyCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentGameBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game, container, false
        )
        binding.feedButton.setOnClickListener {
            satietyCount++
            if (satietyCount % 15 == 0) {
                binding.cat.startAnimation(AnimationUtils.loadAnimation(context, R.anim.cat_enjoy_animation))
            }
            binding.satietyCountText.text = satietyCount.toString()
        }
        setHasOptionsMenu(true)

        binding.saveButton.setOnClickListener {
            TableData.data.value?.let { list ->
                list.add(Pair(binding.satietyCountText.text.toString(), System.currentTimeMillis().toString()))
                TableData.saveData(it.context)
            }
        }

        return binding.root
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.shared_menu, menu)
        if ((getShareIntent()).resolveActivity(requireActivity().packageManager) == null) {
            menu.findItem(R.id.share).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareSuccess() {
        startActivity(Intent.createChooser(getShareIntent(), getString(R.string.share_success_text, satietyCount)))
    }

    private fun getShareIntent(): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.share_success_text, satietyCount)
            )
        return shareIntent
    }

}
