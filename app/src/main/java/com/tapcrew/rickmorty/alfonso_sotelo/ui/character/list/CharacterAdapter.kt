package com.tapcrew.rickmorty.alfonso_sotelo.ui.character.list

import android.view.View
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.tapcrew.rickmorty.alfonso_sotelo.R
import com.tapcrew.rickmorty.alfonso_sotelo.base.BaseAdapter
import com.tapcrew.rickmorty.alfonso_sotelo.repository.remote.objects.Character
import kotlinx.android.synthetic.main.row_character.view.*

class CharacterAdapter: BaseAdapter<Character, CharacterAdapter.ViewHolder>(
    R.layout.row_character,
    DefaultDiffCallback()
) {

    override fun onBindVH(holder: ViewHolder, position: Int) {

    }

    override fun instanceViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View): BaseViewHolder(view) {
        override fun bind(element: Character?) {
            itemView.apply {
                element?.let { character ->
                    Glide.with(context)
                        .load(character.image)
                        .placeholder(
                            CircularProgressDrawable(context).apply {
                                strokeWidth = 3f
                                centerRadius = 30f
                                start()
                            }
                        )
                        .into(ivImage)

                    tvName.text = character.name
                    tvSpecies.text = character.species
                    tvStatus.text = character.status
                } ?: run {
                    tvName.text = ""
                    tvSpecies.text = ""
                    tvStatus.text = ""

                    Glide.with(context)
                        .clear(ivImage)
                }
            }
        }
    }
}