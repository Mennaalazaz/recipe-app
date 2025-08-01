package com.example.recipeapp.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.recipeapp.FavoriteViewModel
import com.example.recipeapp.R
import com.example.recipeapp.databinding.FragmentRecipeDetailBinding
import com.example.recipeapp.favoritedb.FavoriteRecipe
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment() {

    private var _binding: FragmentRecipeDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels()

    private var recipeId: String? = null
    private var title: String? = null
    private var imageUrl: String? = null
    private var instructions: String? = null
    private var videoUrl: String? = null // 🔹 لينك الفيديو من API

    private var isFavorite = false
    private var isExpanded = false

    private var floatingVideoLayout: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getString("idMeal")
            title = it.getString("strMeal")
            imageUrl = it.getString("strMealThumb")
            instructions = it.getString("strInstructions")
            videoUrl = it.getString("strYoutube") // 🔹 استلام لينك الفيديو من API
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)

        // عرض البيانات
        binding.tvTitle.text = title
        Glide.with(requireContext())
            .load(imageUrl)
            .into(binding.ivRecipe)

        // عرض الوصف المختصر
        binding.tvDetails.text = instructions?.take(100) ?: "No details available"

        val recipe = FavoriteRecipe(
            recipeId = recipeId ?: "",
            title = title ?: "",
            imageUrl = imageUrl ?: "",
            instructions = instructions ?: "", // 🆕 حفظ الوصف
            videoUrl = videoUrl
        )


        lifecycleScope.launch {
            val exists = viewModel.exists(recipe.recipeId)
            isFavorite = exists
            binding.btnFavorite.setImageResource(
                if (exists) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        }

        binding.btnFavorite.setOnClickListener {
            lifecycleScope.launch {
                if (!isFavorite) {
                    viewModel.insert(recipe)
                    Toast.makeText(requireContext(), "Added to Favorites", Toast.LENGTH_SHORT).show()
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorite)
                    isFavorite = true
                } else {
                    viewModel.deleteById(recipe.recipeId)
                    Toast.makeText(requireContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show()
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorite_border)
                    isFavorite = false
                }
            }
        }

        binding.btnExpand.setOnClickListener {
            isExpanded = !isExpanded
            if (isExpanded) {
                binding.tvDetails.text = instructions ?: "No details available"
                binding.btnExpand.text = "Show Less"
            } else {
                binding.tvDetails.text = instructions?.take(100) ?: "No details available"
                binding.btnExpand.text = "Show More"
            }
        }

        // 🎥 زر تشغيل الفيديو
        binding.btnPlayVideo.setOnClickListener {
            videoUrl?.let {
                showFloatingVideo()
            } ?: run {
                Toast.makeText(requireContext(), "No video available", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        return binding.root
    }

    // 🎥 عرض الفيديو داخل WebView
    private fun showFloatingVideo() {
        // لو مفتوح قبل كده نشيله
        if (floatingVideoLayout != null) {
            (requireActivity().findViewById<ViewGroup>(android.R.id.content))
                .removeView(floatingVideoLayout)
        }

        // عمل inflate لملف الفيديو
        floatingVideoLayout = layoutInflater.inflate(R.layout.layout_floating_video, null)
        val webView = floatingVideoLayout!!.findViewById<WebView>(R.id.webViewVideo)
        val btnClose = floatingVideoLayout!!.findViewById<ImageButton>(R.id.btnCloseVideo)

        // إعداد الـ WebView
        webView.settings.javaScriptEnabled = true
        webView.settings.pluginState = WebSettings.PluginState.ON
        webView.webViewClient = WebViewClient()

        // تحويل رابط يوتيوب إلى Embed
        val embedUrl = videoUrl?.replace("watch?v=", "embed/")
        webView.loadUrl(embedUrl ?: "https://www.youtube.com")

        // زر الإغلاق
        btnClose.setOnClickListener {
            (requireActivity().findViewById<ViewGroup>(android.R.id.content))
                .removeView(floatingVideoLayout)
        }

        // إضافة الفيديو على الشاشة
        (requireActivity().findViewById<ViewGroup>(android.R.id.content))
            .addView(floatingVideoLayout, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
