package com.b3taisadonna.passwordmanager

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.b3taisadonna.passwordmanager.databinding.ItemPasswordBinding

class PasswordAdapter(private val onItemClick: (PasswordEntity) -> Unit) : 
    ListAdapter<PasswordEntity, PasswordAdapter.PasswordViewHolder>(PasswordDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder {
        val binding = ItemPasswordBinding.inflate(
            LayoutInflater.from(parent.context), 
            parent, 
            false
        )
        return PasswordViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val password = getItem(position)
        holder.bind(password, onItemClick)
    }
    
    class PasswordViewHolder(private val binding: ItemPasswordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(password: PasswordEntity, onItemClick: (PasswordEntity) -> Unit) {
            binding.titleText.text = password.title
            binding.usernameText.text = password.username
            
            binding.root.setOnClickListener { onItemClick(password) }
            
            binding.copyButton.setOnClickListener {
                val clipboard = binding.root.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("password", password.password)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(binding.root.context, R.string.password_copied, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    class PasswordDiffCallback : DiffUtil.ItemCallback<PasswordEntity>() {
        override fun areItemsTheSame(oldItem: PasswordEntity, newItem: PasswordEntity): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: PasswordEntity, newItem: PasswordEntity): Boolean {
            return oldItem == newItem
        }
    }
}
